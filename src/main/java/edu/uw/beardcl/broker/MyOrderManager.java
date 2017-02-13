package edu.uw.beardcl.broker;


import edu.uw.ext.framework.broker.OrderManager;
import edu.uw.ext.framework.broker.OrderProcessor;
import edu.uw.ext.framework.order.StopBuyOrder;
import edu.uw.ext.framework.order.StopSellOrder;

// TODO: Auto-generated Javadoc
/**
 * The Class MyOrderManager.
 */
public class MyOrderManager extends Object implements OrderManager {
	
	/** The stock ticker symbol. */
	String stockTickerSymbol = null;
	
	/** The price. */
	int price = 0;
	
	/** The order process. */
	OrderProcessor orderProcess = null;
	
	/** The buy filter. */
	StopBuyOrderDispatchFilter buyFilter = null;
	
	/** The sell filter. */
	StopSellOrderDispatchFilter sellFilter = null;
	
	/** The buy queue. */
	MyOrderQueue<StopBuyOrder> buyQueue = null;
	
	/** The sell queue. */
	MyOrderQueue<StopSellOrder> sellQueue = null;
	

	/**
	 * Instantiates a new my order manager.
	 *
	 * @param stockTickerSymbol the stock ticker symbol
	 * @param price the price
	 */
	public MyOrderManager(String stockTickerSymbol, int price) {
			this.stockTickerSymbol = stockTickerSymbol;
			this.price = price;
			 buyFilter = new StopBuyOrderDispatchFilter(this.price);
			 sellFilter = new StopSellOrderDispatchFilter(this.price);
			 buyQueue = new MyOrderQueue<StopBuyOrder>(new StopBuyOrderComparator(), buyFilter);
			 sellQueue = new MyOrderQueue<StopSellOrder>(new StopSellOrderComparator(), sellFilter);
			
	}
	

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.broker.OrderManager#adjustPrice(int)
	 */
	@Override
	public void adjustPrice(int price) {
		this.price = price;
		buyFilter.setThreshold(price);
		sellFilter.setThreshold(price);
		buyQueue.dispatchOrders();
		sellQueue.dispatchOrders();
		
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.broker.OrderManager#getSymbol()
	 */
	@Override
	public String getSymbol() {
			
		return this.stockTickerSymbol;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.broker.OrderManager#queueOrder(edu.uw.ext.framework.order.StopBuyOrder)
	 */
	@Override
	public void queueOrder(StopBuyOrder order) {
			buyQueue.enqueue(order);
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.broker.OrderManager#queueOrder(edu.uw.ext.framework.order.StopSellOrder)
	 */
	@Override
	public void queueOrder(StopSellOrder order) {
		sellQueue.enqueue(order);
		
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.broker.OrderManager#setOrderProcessor(edu.uw.ext.framework.broker.OrderProcessor)
	 */
	@Override
	public void setOrderProcessor(OrderProcessor orderProcess) {
			this.orderProcess = orderProcess;
			buyQueue.setOrderProcessor(orderProcess);
			sellQueue.setOrderProcessor(orderProcess);
	}




}

