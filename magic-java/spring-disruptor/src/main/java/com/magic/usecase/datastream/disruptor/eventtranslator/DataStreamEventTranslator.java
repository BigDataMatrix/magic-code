package com.magic.usecase.datastream.disruptor.eventtranslator;

import com.magic.usecase.datastream.model.DataStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventTranslator;

public class DataStreamEventTranslator implements EventTranslator<DataStream>{

	private static final Logger LOG = LoggerFactory.getLogger(DataStreamEventTranslator.class);
	private DataStream dataStream;

	public DataStreamEventTranslator(DataStream dataStream) {
		this.dataStream = dataStream;
	}

	@Override
	public void translateTo(DataStream dataStream, long sequence) {
		dataStream.setDataStreamId(this.dataStream.getDataStreamId());
		dataStream.setDataStreamType(this.dataStream.getDataStreamType());
		dataStream.setDataStream(this.dataStream.getDataStream());
		dataStream.setDataSource(this.dataStream.getDataSource());
		dataStream.setDataTarget(this.dataStream.getDataTarget());
		
		if(sequence%10==0){
			LOG.info("Published {} to sequence: {}",dataStream.toString(), sequence);
		}
	}
	
}
