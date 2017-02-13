package edu.uw.beardcl.broker;

import java.util.Comparator;
import java.util.TreeSet;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;

import edu.uw.ext.framework.broker.OrderDispatchFilter;
import edu.uw.ext.framework.broker.OrderProcessor;
import edu.uw.ext.framework.broker.OrderQueue;
import edu.uw.ext.framework.order.Order;

// TODO: Auto-generated Javadoc
/**
 * The Class MyOrderQueue.
 * 
 * @param <E>
 *            the element type
 */
public class MyOrderQueue<E extends Order> // limits orderQueue to just orders
		extends Object implements OrderQueue<E> {
	protected class OrderWorkItem implements Runnable{
		private Order order;
		private OrderProcessor orderProcessor;
		private AtomicInteger count;
		public OrderWorkItem(Order order, OrderProcessor orderProcessor, AtomicInteger count){
			this.order = order;
			this.orderProcessor = orderProcessor;
			this.count = count;
		}
		@Override
		public void run() {
			this.orderProcessor.process(this.order);
			count.decrementAndGet();
		}
		
	}
private static Executor executor = new ThreadPoolExecutor(1, 5, 100, TimeUnit.MILLISECONDS, 
		new LinkedBlockingQueue<Runnable>());

	private Lock lock = new ReentrantLock();
	/** The queue. */
	private TreeSet<E> queue;

	/** The filter. */
	private OrderDispatchFilter<?, E> filter; // use wildCard b/c that part is
												// never used
	/** The order processor. */
	private OrderProcessor orderProcessor;

	/*
	 * void method2() { lock.lock(); try { condition.signal(); --price; }
	 * finally { lock.unlock(); } }
	 * 
	 * void method1() { lock.lock(); try { ++price; condition.await(); } catch
	 * (InterruptedException ex) { // means something called interrupt() }
	 * finally { --price; lock.unlock(); } }
	 */
	/**
	 * Instantiates a new my order queue.
	 * 
	 * @param filter
	 *            the filter
	 */
	public MyOrderQueue(OrderDispatchFilter<?, E> filter) {
		this(null, filter);

	}

	/**
	 * Instantiates a new my order queue.
	 * 
	 * @param cmp
	 *            the cmp
	 */
	public MyOrderQueue(final Comparator<E> cmp) {
		this(cmp, null);
	}

	/**
	 * Instantiates a new my order queue.
	 * 
	 * @param cmp
	 *            the cmp
	 * @param filter
	 *            the filter
	 */
	public MyOrderQueue(Comparator<E> cmp, OrderDispatchFilter<?, E> filter) {
		queue = new TreeSet<E>(cmp);
		this.filter = filter;
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uw.ext.framework.broker.OrderQueue#dequeue()
	 */
	@Override
	public E dequeue() { // this is the crux of the problem, right here
		lock.lock();

		E order = null; // checks top queue memeber against current queue
		try {
			if (!queue.isEmpty()) {
				order = queue.first(); // look at first memeber only
				if (filter.check(order)) {
					queue.remove(order);

				} else {
					order = null;
				}
			}
		} finally {
			lock.unlock();
		}
		return order;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.uw.ext.framework.broker.OrderQueue#dispatchOrders()
	 */
	@Override
	public void dispatchOrders() {
		lock.lock();
		try {
			AtomicInteger count = new AtomicInteger();
			E order = dequeue();
			while (order != null) {
				if (orderProcessor != null) {
					count.incrementAndGet();
					executor.execute(new OrderWorkItem(order, orderProcessor, count));
				//	orderProcessor.process(order);
				}
				order = dequeue();
			}
			while(count.get() != 0);
		} finally {
			lock.unlock();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uw.ext.framework.broker.OrderQueue#enqueue(edu.uw.ext.framework.order
	 * .Order)
	 */
	@Override
	public void enqueue(E order) {
		lock.lock();
		try {
			queue.add(order);
		} finally {
			lock.unlock();
		}
		dispatchOrders();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.uw.ext.framework.broker.OrderQueue#setOrderProcessor(edu.uw.ext.framework
	 * .broker.OrderProcessor)
	 */
	@Override
	public void setOrderProcessor(OrderProcessor processor) {
		lock.lock();
		try {
			orderProcessor = processor; // if lock is used above in Runnable, it
										// is needed here as well
		} finally {
			lock.unlock();
		}
	}

}
