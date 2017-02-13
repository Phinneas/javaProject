package edu.uw.beardcl.broker;

import edu.uw.ext.framework.broker.OrderProcessor;
import edu.uw.ext.framework.broker.OrderQueue;
import edu.uw.ext.framework.order.Order;

// TODO: Auto-generated Javadoc
/**
 * The Class MoveToMarketQueueProcessor.
 */
public final class MoveToMarketQueueProcessor extends Object implements
		OrderProcessor {

	/** The market queue. */
	OrderQueue<Order> marketQueue = null;
	
	/**
	 * Instantiates a new move to market queue processor.
	 *
	 * @param marketQueue the market queue
	 */
	public MoveToMarketQueueProcessor(
			OrderQueue<Order> marketQueue) {
			this.marketQueue = marketQueue;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.broker.OrderProcessor#process(edu.uw.ext.framework.order.Order)
	 */
	public void process(Order order) {
			this.marketQueue.enqueue(order);
	
	}

}
