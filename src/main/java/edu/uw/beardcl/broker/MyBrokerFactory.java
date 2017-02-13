package edu.uw.beardcl.broker;

import edu.uw.ext.framework.account.AccountManager;
import edu.uw.ext.framework.broker.Broker;
import edu.uw.ext.framework.broker.BrokerFactory;
import edu.uw.ext.framework.exchange.StockExchange;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating MyBroker objects.
 */
public class MyBrokerFactory extends Object implements BrokerFactory
{

	
	

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.broker.BrokerFactory#newBroker(java.lang.String, edu.uw.ext.framework.account.AccountManager, edu.uw.ext.framework.exchange.StockExchange)
	 */
	@Override
	public Broker newBroker(String name, AccountManager accntManager, StockExchange exch) {
			
		return new MyBroker(name, accntManager, exch);
	}

	


}
