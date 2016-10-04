package org.flowcomputing.commons.resgc;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Collect resource holders that need to be collected after utilization. It
 * manages to release resource in explicit way and we have change to be notified
 * when its resource is going to be release. In addition, It embedded mechanism
 * to allow resource to be forced release in advance.
 * 
 * @author wg
 *
 * @param <HOLDER>
 *            holder type for any kind of resource.
 * 
 * @param <MRES>
 *            resource type to be holden.
 */
public class ResCollector<HOLDER extends Holder<MRES, HOLDER>, MRES> implements
		Collector<HOLDER, MRES> {

	public final static long WAITRECLAIMTIMEOUT = 800;
	public final static long WAITQUEUETIMEOUT = 300;
        public final static long WAITTERMTIMEOUT = 10000;
	public final static long TERMCHECKTIMEOUT = 20;

	private final ReferenceQueue<HOLDER> refque = new ReferenceQueue<HOLDER>();
	private final Map<Reference<HOLDER>, MRES> refmap = 
			new ConcurrentHashMap<Reference<HOLDER>, MRES>();

	private ResReclaim<MRES> m_reclaimer;
	private Thread m_collector;
	private AtomicLong descnt = new AtomicLong(0L);
	
	private volatile boolean m_stopped = false;

	/**
	 * constructor to accept a resource reclaimer that is used to actual its
	 * resource when needed.
	 * 
	 * @param reclaimer 
	 *          a reclaimer object for managed resource reclaim.
	 */
	public ResCollector(ResReclaim<MRES> reclaimer) {
		m_reclaimer = reclaimer;
		m_collector = new Thread() {
			public void run() {
				while (!m_stopped) {
					try {
						Reference<? extends HOLDER> ref = refque.remove(WAITQUEUETIMEOUT);
                                                if (null != ref) {
							destroyRes(ref);
							descnt.getAndIncrement();
						}
					} catch (InterruptedException ex) {
						break;
					}
				}
			}
		};
		m_collector.setDaemon(true);
		m_collector.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.flowcomputing.commons.resgc.Collector#register(org.flowcomputing.
	 * commons.resgc.Holder, java.lang.Object)
	 */
	@Override
	public void register(HOLDER holder) {
		PhantomReference<HOLDER> pref = new PhantomReference<HOLDER>(holder,
				refque);
		refmap.put(pref, holder.get());
		holder.setCollector(this);
		holder.setRefId(pref);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.flowcomputing.commons.resgc.Collector#unregister(org.flowcomputing.
	 * commons.resgc.Holder)
	 */
	@Override
	public void unregister(HOLDER holder) {
		refmap.remove(holder.getRefId());
		holder.setRefId(null);
		holder.setCollector(null);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.flowcomputing.commons.resgc.Collector#unregister()
	 */
	@Override
	public void unregister(Reference<HOLDER> ref) {
		refmap.remove(ref);
	}

	/**
	 * destroy a specified holden resource.
	 * 
	 * @param ref
	 *            a specified reference that is referring a holder
	 */
	@Override
	public void destroyRes(Reference<? extends HOLDER> ref) {
		MRES mres = refmap.remove(ref);
		m_reclaimer.reclaim(mres);
	}

	@Override
	public void destroyRes(MRES mres) {
		m_reclaimer.reclaim(mres);
	}

	private void forceGC(long timeout) {
		System.gc();
		try {
			Thread.sleep(timeout);
		} catch (Exception ex) {
		}
	}

	/**
	 * wait for reclaim termination.
	 *
	 * @param timeout
	 *            specify a timeout to reclaim
	 */
	public void waitReclaimCoolDown(long timeout) {
		do {
			forceGC(timeout);
		} while (0L != descnt.getAndSet(0L));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.flowcomputing.commons.resgc.Collector#close()
	 */
	@Override
	public boolean close(long recltmout, long termtmout) {
		boolean ret = true;
		waitReclaimCoolDown(recltmout);
		m_stopped = true;
		long et = System.currentTimeMillis() + termtmout;
		while (m_collector.getState() != Thread.State.TERMINATED) {
			try {
				Thread.sleep(TERMCHECKTIMEOUT);
			} catch (Exception ex) {
			}
			if (System.currentTimeMillis() > et) {
				ret = false;
				break;
			}
		}
		refmap.clear();
		return ret;
	}

	public boolean close() {
		return close(WAITRECLAIMTIMEOUT, WAITTERMTIMEOUT);
	}
}
