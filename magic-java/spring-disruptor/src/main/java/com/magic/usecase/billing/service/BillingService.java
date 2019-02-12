package com.magic.usecase.billing.service;


import com.magic.usecase.billing.model.BillingRecord;

public interface BillingService {
	static final long delay = 999999L;
	
	void validateBillingRecord(BillingRecord billingRecord);
	
	void formatBillingRecord(BillingRecord billingRecord);
	
	void journalBillingRecord(BillingRecord billingRecord);
	
	void processBillingRecord(BillingRecord billingRecord);
	
	void processCorporateBillingRecord(BillingRecord billingRecord);
	
	void processCustomerSpecificBillingRecord(BillingRecord billingRecord);
	
}
