

package com.magic.lucene;


import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.magic.util.ThrowingFunction;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Searcher {

    private volatile IndexSearcher indexSearcher;
    private static final int DEFAULT_LIMIT = 10000;
    private static final Object lock = new Object();

    private ThrowingFunction<String, Path> createPath = Paths::get;
    private ThrowingFunction<Path, Directory> createDirectory = FSDirectory::open;
    private ThrowingFunction<Directory, DirectoryReader> createDirectoryReader = DirectoryReader::open;
    private ThrowingFunction<DirectoryReader, IndexSearcher> openIndexSearcher = IndexSearcher::new;
    private ThrowingFunction<Directory, IndexSearcher> fromDirectoryToIndexSearcher = createDirectoryReader.andThen(openIndexSearcher);
    private ThrowingFunction<String, IndexSearcher> createIndexSearcherFromStringPath = createPath.andThen(createDirectory).andThen(createDirectoryReader).andThen(openIndexSearcher);
    private ThrowingFunction<Path, IndexSearcher> createIndexSearcherFromPath = createDirectory.andThen(createDirectoryReader).andThen(openIndexSearcher);

    private Function<Set<String>, Function<IndexSearcher, ThrowingFunction<ScoreDoc, Document>>> getSearchDocsWithSelectedFields = fields -> searcher -> scoreDoc -> searcher.doc(scoreDoc.doc, fields);
    private Function<IndexSearcher, ThrowingFunction<ScoreDoc, Document>> getSearchDocsWithAllFields = searcher -> scoreDoc -> searcher.doc(scoreDoc.doc);


    private Function<List<IndexableField>, ImmutableMap<String, Object>> loadFieldIntoHashMap = list -> {
        ImmutableMap.Builder<String,Object> mapBuilder = ImmutableMap.builder();
        list.forEach(field -> {
            Object value = (field.numericValue()!=null) ? field.numericValue() : field.stringValue();
            mapBuilder.put(field.name(), value);
        });
        return mapBuilder.build();
    };


    public Searcher() {
    }

    public Searcher(String indexPath){
        Preconditions.checkArgument(indexPath != null && !indexPath.trim().isEmpty(), "Index Path is can't be null or empty");
        this.indexSearcher = createIndexSearcherFromStringPath.apply(indexPath);
    }

    public Searcher(Path indexPath){
        Preconditions.checkNotNull(indexPath,"Index Path can't be null");
        this.indexSearcher = createIndexSearcherFromPath.apply(indexPath);
    }

    public Searcher(Directory directory) {
        Preconditions.checkNotNull(directory,"Directory can't be null");
        this.indexSearcher = fromDirectoryToIndexSearcher.apply(directory);
    }

    /**
     * <p>
     *     实际查询的方法
     * </p>
     * @param query
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> search(String query) throws IOException {

        QueryParseResults parseResults = LuceneQueryParser.parseQuery(query);

        if ( indexSearcher == null ) {
            synchronized (lock) {
                if( indexSearcher == null ) {
                    indexSearcher = createIndexSearcherFromStringPath.apply(parseResults.getIndexPath());
                }
            }
        }

        int maxResults = parseResults.getLimit() == 0 ? DEFAULT_LIMIT : parseResults.getLimit();

        TopDocs topDocs = indexSearcher.search(parseResults.getBooleanQuery(), maxResults);
        Set<String> fields = parseResults.getSelectFields();
        ThrowingFunction<ScoreDoc, Document> retrieveDocFunction;

        if (fields.isEmpty()) {
            retrieveDocFunction = getSearchDocsWithAllFields.apply(indexSearcher);
        } else {
            retrieveDocFunction = getSearchDocsWithSelectedFields.apply(fields).apply(indexSearcher);

        }


        List<Map<String,Object>> resultsListMutable = Stream.of(topDocs.scoreDocs).map(retrieveDocFunction)
                                                            .map(Document::getFields)
                                                            .map(loadFieldIntoHashMap)
                                                            .collect(Collectors.toList());

        return new ImmutableList.Builder<Map<String,Object>>().addAll(resultsListMutable).build();

    }


}
