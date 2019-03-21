

package com.magic.lucene;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.lucene.search.BooleanQuery;

import java.util.function.Function;
import java.util.function.Supplier;


public class LuceneQueryParser {

    private static Function<String, ANTLRInputStream> createAntlrInputStream = ANTLRInputStream::new;
    private static Function<ANTLRInputStream, LuceneSqlLexer> createLexer = LuceneSqlLexer::new;
    private static Function<LuceneSqlLexer, CommonTokenStream> createTokenStream = CommonTokenStream::new;
    private static Function<CommonTokenStream, LuceneSqlParser> createLuceneSqlParser = LuceneSqlParser::new;
    private static Function<LuceneSqlParser, LuceneSqlParser.QueryContext> createParseTree = LuceneSqlParser::query;
    private static Supplier<ParseTreeWalker> parseTreeWalkerSupplier = ParseTreeWalker::new;
    private static Supplier<LuceneQueryListener> luceneQueryListenerSupplier = LuceneQueryListener::new;

    private static Function<String, LuceneSqlParser> createParser = createAntlrInputStream
                                                                    .andThen(createLexer)
                                                                    .andThen(createTokenStream)
                                                                    .andThen(createLuceneSqlParser);

    private static Function<LuceneSqlParser, QueryParseResults> parse = parser -> {
        LuceneQueryListener listener = luceneQueryListenerSupplier.get();
        ParseTree parseTree = createParseTree.apply(parser);
        ParseTreeWalker walker = parseTreeWalkerSupplier.get();
        walker.walk(listener, parseTree);
        return listener.getParseResults();
    };


    public static QueryParseResults parseQuery(String query) {
        return parse.apply(createParser.apply(query));
    }

    public static BooleanQuery  parseToBooleanQuery(String query) {
        return parseQuery(query).getBooleanQuery();
    }

}
