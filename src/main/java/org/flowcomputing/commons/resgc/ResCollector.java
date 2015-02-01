package org.flowcomputing.commons.resgc;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.util.Map;
import java.util.HashMap;

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
/**
 * @author wg
 *
 * @param <HOLDER>
 * @param <MRES>
 */
/**
 * @author wg
 *
 * @param <HOLDER>
 * @param <MRES>
 */
/**
 * @author wg
 *
 * @param <HOLDER>
 * @param <MRES>
 */
public class ResCollector<HOLDER extends Holder<MRES>, MRES> implements
		Collector<HOLDER, MRES> {

	/**
	 * Destroy its managed resource that hold by its holder.
	 * 
	 * @author wg
	 *
	 */
	public class ResDestroy {
		private Reference<HOLDER> m_ref;

		/**
		 * constructor to accept a holder reference.
		 * 
		 * @param ref
		 *            a holder reference compliance with is host collector.
		 */
		public ResDestroy(Reference<HOLDER> ref) {
			m_ref = ref;
		}

		/**
		 * destroy its holding resource.
		 */
		public void destroy() {
			destroyHoldRes(m_ref);
		}
	}

	private final ReferenceQueue<HOLDER> refque = new ReferenceQueue<HOLDER>();
	private final Map<Reference<? extends Holder<MRES>>, MRES> refmap = new HashMap<Reference<? extends Holder<MRES>>, MRES>();

	private ResReclaim<MRES> m_reclaimer;
	private Thread m_collector;

	/**
	 * constructor to accept a resource reclaimer that is used to actual its
	 * resource when needed.
	 * 
	 * @param reclaimer
	 */
	public ResCollector(ResReclaim<MRES> reclaimer) {
		m_reclaimer = reclaimer;
		m_collector = new Thread() {
			public void run() {
				while (true) {
					try {
						Reference<? extends HOLDER> ref = refque.remove();
						MRES mres = refmap.remove(ref);
						if (null != mres) {
							m_reclaimer.reclaim(mres);
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
	public void register(HOLDER holder, MRES mres) {
		PhantomReference<HOLDER> pref = new PhantomReference<HOLDER>(holder,
				refque);
		refmap.put(pref, mres);
		holder.registerDestroyer(new ResDestroy(pref));
	}

	/**
	 * destroy a specified holden resource.
	 * 
	 * @param ref
	 *            a specified reference that is referring a holder
	 */
	private void destroyHoldRes(Reference<HOLDER> ref) {
		MRES mres = refmap.remove(ref);
		if (null != mres) {
			m_reclaimer.reclaim(mres);
		}
	}

}