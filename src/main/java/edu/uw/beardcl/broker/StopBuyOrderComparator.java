package edu.uw.beardcl.broker;

import java.util.Comparator;

import edu.uw.ext.framework.order.StopBuyOrder;

// TODO: Auto-generated Javadoc
/**
 * The Class StopBuyOrderComparator.
 */
public class StopBuyOrderComparator extends Object implements
		Comparator<StopBuyOrder> {

	// Perfoms
	// @param order1 first of two orders to be compared
	// @param order2 first of two orders to be compared
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(final StopBuyOrder order, final StopBuyOrder order2) {
		if (order == order2) {
			return 0;
		}
		int diff = Integer.compare(order.getPrice(), order2.getPrice());
		if (diff == 0) {
			diff = order.compareTo(order2);
		}
		return diff;
	}
}