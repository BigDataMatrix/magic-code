

package com.magic.lucene;

import org.apache.lucene.index.Term;
import org.apache.lucene.queries.TermsFilter;
import org.apache.lucene.search.Filter;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public enum FilterType implements LuceneQueryFunctions,LuceneAnalyzingFunctions {

    TERMS_FILTER {
        @Override
        Filter filter(String field, Collection<String> filterValues) {
            List<Term> filterTerms = filterValues.stream().map(createFilterTerm.apply(field)).collect(Collectors.toList());
            return new TermsFilter(filterTerms);
        }
    },

    TERM_RANGE_FILTER{
        @Override
        Filter filter(String field, Collection<String> filterValues) {
            throw new RuntimeException("Not Implemented");
        }
    },

    NUMERIC_RANGE_FILTER{
        @Override
        Filter filter(String field, Collection<String> filterValues) {
            throw new RuntimeException("Not Implemented");
        }
    },

    QUERY_FILTER{
        @Override
        Filter filter(String field, Collection<String> filterValues) {
            throw new RuntimeException("Not Implemented");
        }
    };

   abstract Filter filter(String field,Collection<String> filterValues);

    Function<String,Function<String,Term>>  createFilterTerm = f -> lettersNumbersTrimLowerCase.andThen(termFunction.apply(f));

}
