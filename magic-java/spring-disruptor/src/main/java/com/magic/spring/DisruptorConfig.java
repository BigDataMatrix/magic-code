package com.magic.spring;

import com.lmax.disruptor.EventTranslator;

public interface DisruptorConfig<T> {

    /**
     * Publish an event to the ring buffer using event translator.
     *
     * @param translator
     */
    void publish(EventTranslator<T> eventTranslator);

    /**
     * Design a Event Processor/Consumer definition.
     */
    void disruptorEventHandler();

    /**
     * Handle Disruptor exceptions
     */
    void disruptorExceptionHandler();
}

