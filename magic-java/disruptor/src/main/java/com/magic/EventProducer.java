package com.magic;

import com.lmax.disruptor.RingBuffer;

public interface EventProducer {

    /**
     * Start the producer that would start producing the values.
     * @param ringBuffer
     * @param count
     */
    public void startProducing(final RingBuffer<ValueEvent> ringBuffer, final int count);

}
