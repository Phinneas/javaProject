package edu.uw.beardcl.broker;

import java.util.Comparator;

import edu.uw.ext.framework.order.StopSellOrder;

// TODO: Auto-generated Javadoc
/**
 * The Class StopSellOrderComparator.
 */
public class StopSellOrderComparator extends Object implements
		Comparator<StopSellOrder> {

	// Perfoms
		// @param order1 first of two orders to be compared
		// @param order2 first of two orders to be compared
		/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
		public int compare(final StopSellOrder order, final StopSellOrder order2) {
			if (order == order2) {
				return 0;
			}
			int diff = Integer.compare(order2.getPrice(), order.getPrice());
			if (diff == 0) {
				diff = order.compareTo(order2);
			}
			return diff;
		}

}
