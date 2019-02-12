package com.magic.usecase.billing.disruptor.eventfactory;


import com.lmax.disruptor.EventFactory;
import com.magic.usecase.billing.model.BillingRecord;

public class BillingEvent implements EventFactory<BillingRecord> {

	@Override
	public BillingRecord newInstance() {
		return new BillingRecord();
	}
	
}
