package edu.uw.beardcl.broker;

import edu.uw.ext.framework.broker.OrderDispatchFilter;
import edu.uw.ext.framework.order.Order;

// TODO: Auto-generated Javadoc
/**
 * The Class MarketDispatchFilter.
 */
public final class MarketDispatchFilter extends
		OrderDispatchFilter<Boolean, Order> {

	/** The market state. */
	boolean marketState;

	/**
	 * Instantiates a new market dispatch filter.
	 *
	 * @param initState the init state
	 */
	MarketDispatchFilter(boolean initState) {
		this.marketState = initState;
	}

	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.broker.OrderDispatchFilter#check(edu.uw.ext.framework.order.Order)
	 */
	public boolean check(Order order) {

		// Test if order may be dispatched. All orders dispatched if market is
		// open
		return marketState;

	}

	/**
	 * Sets the market state.
	 *
	 * @param marketState the new market state
	 */
	public void setMarketState(boolean marketState){
		this.marketState = marketState;
	}
}
