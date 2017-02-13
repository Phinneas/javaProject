package edu.uw.beardcl.broker;

import edu.uw.ext.framework.broker.OrderDispatchFilter;
import edu.uw.ext.framework.order.StopSellOrder;

// TODO: Auto-generated Javadoc
/**
 * The Class StopSellOrderDispatchFilter.
 */
public class StopSellOrderDispatchFilter extends OrderDispatchFilter<Integer, StopSellOrder> {

	/**
	 * Instantiates a new stop sell order dispatch filter.
	 *
	 * @param initPrice the init price
	 */
	public StopSellOrderDispatchFilter(final int initPrice){
		setThreshold(initPrice);
	}
	
	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.broker.OrderDispatchFilter#check(edu.uw.ext.framework.order.Order)
	 */
	@Override
	public boolean check(StopSellOrder order) {
		  int currentPrice = getThreshold();
		  int sellAtOrBelow = order.getPrice(); 
		  boolean dispatch =   currentPrice <= sellAtOrBelow;
		  
	return dispatch;
	}

}
