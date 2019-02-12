package com.magic.usecase.datastream.disruptor.eventfactory;

import com.magic.usecase.datastream.model.DataStream;

import com.lmax.disruptor.EventFactory;

public class DataStreamEvent implements EventFactory<DataStream> {

	@Override
	public DataStream newInstance() {
		return new DataStream();
	}
	
}
