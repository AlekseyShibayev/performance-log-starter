package com.company.app.exchangeRate;

import com.company.app.AbstractTest;
import com.company.app.exchangeRate.entity.ExchangeRate;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ExchangeRateFacadeTest extends AbstractTest {

	@Autowired
	private ExchangeRateFacade exchangeRateFacade;

	@Test
	public void extractCurse() {
		ExchangeRate exchangeRate = exchangeRateFacade.extract();
		Assert.assertNotNull(exchangeRate.getAliexpressExchangeRate());
	}
}