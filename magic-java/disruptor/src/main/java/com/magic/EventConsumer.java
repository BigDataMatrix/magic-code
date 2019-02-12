package com.magic;

import com.lmax.disruptor.EventHandler;

public interface EventConsumer {

    public EventHandler<ValueEvent>[] getEventHandler();

}
