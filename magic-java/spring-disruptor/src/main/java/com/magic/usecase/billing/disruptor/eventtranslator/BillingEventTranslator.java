package com.magic.usecase.billing.disruptor.eventtranslator;

import com.magic.usecase.billing.model.BillingRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventTranslator;

public class BillingEventTranslator implements EventTranslator<BillingRecord>{

	private static final Logger LOG = LoggerFactory.getLogger(BillingEventTranslator.class);
	private BillingRecord billingRecord;

	public BillingEventTranslator(BillingRecord billingRecord) {
		this.billingRecord = billingRecord;
	}

	@Override
	public void translateTo(BillingRecord billingRecord, long sequence) {
		billingRecord.setBillingId(this.billingRecord.getBillingId());
		billingRecord.setBillable(this.billingRecord.isBillable());
		billingRecord.setQuantity(this.billingRecord.getQuantity());
		billingRecord.setBillableArtifactName(this.billingRecord.getBillableArtifactName());
		billingRecord.setCustomerName(this.billingRecord.getCustomerName());
		
		if(sequence%10==0){
			LOG.info("Published {} to sequence: {}",billingRecord.toString(), sequence);
		}
	}
	
}
