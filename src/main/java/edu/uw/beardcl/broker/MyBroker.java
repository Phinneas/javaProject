package edu.uw.beardcl.broker;

import java.util.HashMap;

//import org.slf4j.LoggerFactory;


import org.slf4j.LoggerFactory;

import edu.uw.ext.framework.account.Account;
import edu.uw.ext.framework.account.AccountException;
import edu.uw.ext.framework.account.AccountManager;
import edu.uw.ext.framework.broker.Broker;
import edu.uw.ext.framework.broker.BrokerException;
import edu.uw.ext.framework.broker.OrderManager;
import edu.uw.ext.framework.exchange.ExchangeEvent;
import edu.uw.ext.framework.exchange.ExchangeListener;
import edu.uw.ext.framework.exchange.StockExchange;
import edu.uw.ext.framework.exchange.StockQuote;
import edu.uw.ext.framework.order.MarketBuyOrder;
import edu.uw.ext.framework.order.MarketSellOrder;
import edu.uw.ext.framework.order.Order;
import edu.uw.ext.framework.order.StopBuyOrder;
import edu.uw.ext.framework.order.StopSellOrder;

public class MyBroker extends Object implements
		Broker,
		ExchangeListener {
	String name = null;
	AccountManager acctMgr = null;
	StockExchange exchg = null;
	MarketDispatchFilter marketDispatchFilter = new MarketDispatchFilter(false);
	HashMap<String, OrderManager> managerMap = new HashMap<String, OrderManager>();
	MyOrderQueue<Order> marketOrder = new MyOrderQueue<Order>(marketDispatchFilter);
	private static org.slf4j.Logger logger =
			LoggerFactory.getLogger(MyBroker.class);
	
	
	MyBroker(String brokerName,
			AccountManager acctMgr,
			StockExchange exchg) {
		logger.debug("LOTR");
		this.name = brokerName;
		this.acctMgr = acctMgr;
		this.exchg = exchg;
		MoveToMarketQueueProcessor processor = new MoveToMarketQueueProcessor(marketOrder);
		for(String ticker: this.exchg.getTickers()){
			int price = exchg.getQuote(ticker).getPrice();
			OrderManager manager = new MyOrderManager(ticker, price);	//creating OrderManager for each ticker symbol, matching price to ticker;
			manager.setOrderProcessor(processor);
			managerMap.put(ticker, manager);
		}
	}

	@Override
	public void exchangeClosed(ExchangeEvent arg0) {
			marketDispatchFilter.setMarketState(false);
	}

	@Override
	public void exchangeOpened(ExchangeEvent arg0) {
			marketDispatchFilter.setMarketState(true);
			this.marketOrder.dispatchOrders();
	}

	@Override
	public void priceChanged(ExchangeEvent event) {
			this.managerMap.get(event.getTicker()).adjustPrice(event.getPrice());
	}

	@Override
	public void close() throws BrokerException {
				this.exchg.removeExchangeListener(this);
				try {
					this.acctMgr.close();
				} catch (AccountException e) {
					logger.debug("Error in MyBroker on close() method", e);
					e.printStackTrace();

				}
	}

	@Override
	public Account createAccount(String username, String password, int balance)
			throws BrokerException {
		try {
			return this.acctMgr.createAccount(username, password, balance);
		} catch (AccountException e) {
			logger.debug("Error in MyBroker at createAccount", e);		
			e.printStackTrace();

			throw new BrokerException("Could not create account.", e);
		}
	}

	@Override
	public void deleteAccount(String username) throws BrokerException {
			try {
				this.acctMgr.deleteAccount(username);
			} catch (AccountException e) {
				logger.debug("Error in MyBroker at deleteAccount", e);
				e.printStackTrace();

				throw new BrokerException("Cannot delete account.", e);
			}
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.broker.Broker#getAccount(java.lang.String, java.lang.String)
	 */
	@Override
	public Account getAccount(String username, String password) throws BrokerException {

		try {
			if( this.acctMgr.validateLogin(username, password)){
				return this.acctMgr.getAccount(username);
			}
		} catch (AccountException e) {
			logger.error("Error in MyBroker at getAccount() method", e);
			e.printStackTrace();

			throw new BrokerException("Cannot get account", e);
		}
		return null;
	}

	@Override
	public String getName() {
			
		return this.name;
	}

	@Override
	public void placeOrder(MarketBuyOrder order) throws BrokerException {
		marketOrder.enqueue(order);
	}

	@Override
	public void placeOrder(MarketSellOrder order) throws BrokerException {
		marketOrder.enqueue(order);

	}

	@Override
	public void placeOrder(StopBuyOrder order) throws BrokerException {
			this.managerMap.get(order.getStockTicker()).queueOrder(order);;
			
	}

	@Override
	public void placeOrder(StopSellOrder order) throws BrokerException {
					this.managerMap.get(order.getStockTicker()).queueOrder(order);
	}

	@Override
	public StockQuote requestQuote(String ticker) throws BrokerException {

		return this.exchg.getQuote(ticker);
	}
	
	
	

}
