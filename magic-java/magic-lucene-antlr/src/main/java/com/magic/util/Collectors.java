

package com.magic.util;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collector;


public class Collectors {
    private static final BinaryOperator noopCombiner = (l,r)->{throw new RuntimeException("Parallel execution not supported");};

    @SuppressWarnings("unchecked")
    public static<T, R> Collector<T,R,R> nonParallelCollect(Supplier<R> container, BiConsumer<R, T> accumulator){
        return Collector.of(container,accumulator, noopCombiner);
    }
}