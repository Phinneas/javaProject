package edu.uw.beardcl.broker;

import org.slf4j.LoggerFactory;

import edu.uw.ext.framework.account.Account;
import edu.uw.ext.framework.account.AccountException;
import edu.uw.ext.framework.account.AccountManager;
import edu.uw.ext.framework.exchange.StockExchange;
import edu.uw.ext.framework.order.Order;

// TODO: Auto-generated Javadoc
/**
 * The Class StockTraderOrderProcessor.
 */
public class StockTraderOrderProcessor extends Object implements edu.uw.ext.framework.broker.OrderProcessor
{
	
	/** The acct mgr. */
	private AccountManager acctMgr;
	
	/** The exchange. */
	private StockExchange exchange;
	
	/**
	 * Instantiates a new stock trader order processor.
	 *
	 * @param acctMgr the acct mgr
	 * @param exchange the exchange
	 */
	public StockTraderOrderProcessor(AccountManager acctMgr,
            StockExchange exchange){
		
	}
	
	/** The logger. */
	static org.slf4j.Logger logger = LoggerFactory
			.getLogger(StockTraderOrderProcessor.class);
	
	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.broker.OrderProcessor#process(edu.uw.ext.framework.order.Order)
	 */
	@Override
	public void process(Order order) {
		final int sharePrice = exchange.executeTrade(order);
		try{
			final Account acct = acctMgr.getAccount(order.getAccountId());
			acct.reflectOrder(order, sharePrice);
		}catch (final AccountException ex){
		logger.error("Cannot update account, %s", order.getAccountId(), ex);
		}
	}

}
