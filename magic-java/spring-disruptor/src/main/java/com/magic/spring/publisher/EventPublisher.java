package com.magic.spring.publisher;


import com.magic.spring.DisruptorConfig;

/**
 * Ring buffer publisher should use this interface.
 *
 * @author Anoop Nair
 *
 * @param <T>
 */
public interface EventPublisher<T> {

    /**
     * Publish to the ring buffer. Use a Event Translator.
     *
     * @param t
     */
    void publish(T t);

    /**
     * Set the DisruptorConfig spring bean.
     *
     * @param disruptorConfig
     */
    void setDisruptorConfig(DisruptorConfig<T> disruptorConfig);
}

