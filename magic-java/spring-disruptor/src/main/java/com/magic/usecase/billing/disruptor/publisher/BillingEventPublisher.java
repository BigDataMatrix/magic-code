package com.magic.usecase.billing.disruptor.publisher;


import com.magic.spring.DisruptorConfig;
import com.magic.spring.publisher.EventPublisher;
import com.magic.usecase.billing.disruptor.eventtranslator.BillingEventTranslator;
import com.magic.usecase.billing.model.BillingRecord;

public class BillingEventPublisher implements EventPublisher<BillingRecord> {

	private DisruptorConfig<BillingRecord> disruptorConfig;
	
	@Override
	public void publish(BillingRecord billingRecord){
		disruptorConfig.publish(new BillingEventTranslator(billingRecord));
	}

	@Override
	public void setDisruptorConfig(DisruptorConfig<BillingRecord> disruptorConfig) {
		this.disruptorConfig = disruptorConfig;
	}
	
}
