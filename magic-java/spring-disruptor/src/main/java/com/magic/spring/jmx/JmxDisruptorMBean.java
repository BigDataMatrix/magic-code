package com.magic.spring.jmx;

public interface JmxDisruptorMBean {

    void controlledShutdown();

    void halt();

    void awaitAndShutdown(long time);

    void publishToRingbuffer(long sequence);

    String getDisruptorConfiguration();

    String getEventProcessorGraph();

    String getThreadName();

    int getTotalCapacity();

    String getProducerType();

    String getWaitStrategyType();

    long getCurrentLocation();

    long getRemainingCapacity();

}
