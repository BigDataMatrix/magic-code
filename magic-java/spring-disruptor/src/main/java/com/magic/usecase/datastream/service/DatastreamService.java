package com.magic.usecase.datastream.service;


import com.magic.usecase.datastream.model.DataStream;

public interface DatastreamService {
	static final long delay = 555555L;
	
	void journalDatastream(DataStream dataStream);
	
	void processDatastream_A(DataStream dataStream);
	
	void processDatastream_B(DataStream dataStream);
	
	DataStream formatDatastream(DataStream dataStream);
}
