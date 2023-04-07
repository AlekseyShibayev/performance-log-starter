package com.company.app.exchange_rate;

import com.company.app.ApplicationTest;
import com.company.app.exchange_rate.entity.ExchangeRate;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ExchangeRateFacadeTest extends ApplicationTest {

	@Autowired
	private ExchangeRateFacade exchangeRateFacade;

	@Test
	public void extractCurse() {
		ExchangeRate exchangeRate = exchangeRateFacade.extract();
		Assert.assertNotNull(exchangeRate.getAliexpressExchangeRate());
	}
}