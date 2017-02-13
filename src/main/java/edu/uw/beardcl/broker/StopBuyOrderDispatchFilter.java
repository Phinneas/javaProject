package edu.uw.beardcl.broker;

import edu.uw.ext.framework.broker.OrderDispatchFilter;
import edu.uw.ext.framework.order.StopBuyOrder;

// TODO: Auto-generated Javadoc
/**
 * The Class StopBuyOrderDispatchFilter.
 */
public class StopBuyOrderDispatchFilter extends OrderDispatchFilter<Integer, StopBuyOrder> {


	/**
	 * Instantiates a new stop buy order dispatch filter.
	 *
	 * @param initPrice the init price
	 */
	public StopBuyOrderDispatchFilter(final int initPrice){
		setThreshold(initPrice);
	}
	
	/* (non-Javadoc)
	 * @see edu.uw.ext.framework.broker.OrderDispatchFilter#check(edu.uw.ext.framework.order.Order)
	 */
	@Override
	public boolean check(StopBuyOrder order) {
	  int currentPrice = getThreshold();
			  int buyAtOrAbove = order.getPrice(); 
			  boolean dispatch =   currentPrice >= buyAtOrAbove;
			  
		return dispatch;
	}


}
