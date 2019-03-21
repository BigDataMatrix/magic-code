


package com.magic.lucene;


import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.magic.util.Collectors;
import com.magic.util.ThrowingBiFunction;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.BooleanFilter;
import org.apache.lucene.queries.FilterClause;
import org.apache.lucene.search.*;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;


public interface LuceneQueryFunctions {


    BiFunction<String, String, Term> createTerm = Term::new;

    BiFunction<String, String, Function<QueryType, Function<BooleanClause.Occur, BooleanClause>>> toBooleanClause = (f, t) -> qt -> bco -> new BooleanClause(qt.query(f, t), bco);

    ThrowingBiFunction<Term, Class<? extends Query>, Query> toQuery = (term, clazz) -> clazz.getConstructor(Term.class).newInstance(term);

    Function<BooleanClause.Occur,Function<Query,BooleanClause>> fromOccurToClause = occur -> query -> new BooleanClause(query,occur);

    Function<BooleanQuery,BooleanClause> extractBooleanClause = bq -> bq.getClauses()[0];

    Function<Query,BooleanClause> toOrBooleanClause = fromOccurToClause.apply(BooleanClause.Occur.SHOULD);

    Function<Query,BooleanClause> toMustBooleanClause = fromOccurToClause.apply(BooleanClause.Occur.MUST);

    Function<Query,BooleanClause> toNotBooleanClause = fromOccurToClause.apply(BooleanClause.Occur.MUST_NOT);

    Function<Term,Query> toTermQuery = t -> toQuery.apply(t, TermQuery.class);

    Function<String, Function<String, Term>> termFunction = field -> text -> createTerm.apply(field, text);

    Function<String, Stream<String>> toWordStream = s -> Lists.newArrayList(Splitter.on(" ").split(s)).stream();

    BiFunction<String, String, PhraseQuery> toPhraseQuery = (field, text) ->
            toWordStream.apply(text).map(termFunction.apply(field)).collect(Collectors.nonParallelCollect(PhraseQuery::new, PhraseQuery::add));

    Function<List<BooleanClause>, BooleanQuery> toBooleanQuery = l -> l.stream().collect(Collectors.nonParallelCollect(BooleanQuery::new, BooleanQuery::add));

    Function<List<FilterClause>,BooleanFilter> toBooleanFilter = filters -> filters.stream().collect(Collectors.nonParallelCollect(BooleanFilter::new, BooleanFilter::add));

}
