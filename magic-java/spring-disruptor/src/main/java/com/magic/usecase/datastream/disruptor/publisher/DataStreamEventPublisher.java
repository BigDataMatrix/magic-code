package com.magic.usecase.datastream.disruptor.publisher;


import com.magic.spring.DisruptorConfig;
import com.magic.spring.publisher.EventPublisher;
import com.magic.usecase.datastream.disruptor.eventtranslator.DataStreamEventTranslator;
import com.magic.usecase.datastream.model.DataStream;

public class DataStreamEventPublisher implements EventPublisher<DataStream> {

	private DisruptorConfig<DataStream> disruptorConfig;
	
	@Override
	public void publish(DataStream dataStream){
		disruptorConfig.publish(new DataStreamEventTranslator(dataStream));
	}

	@Override
	public void setDisruptorConfig(DisruptorConfig<DataStream> disruptorConfig) {
		this.disruptorConfig = disruptorConfig;
	}
	
}
