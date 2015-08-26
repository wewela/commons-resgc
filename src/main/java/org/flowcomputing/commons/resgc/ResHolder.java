package org.flowcomputing.commons.resgc;

import java.lang.ref.Reference;

/**
 * hold any kind of resource and manages its life cycle.
 * 
 * @author wg
 *
 * @param <T>
 *            a resource type for its holder
 */
public class ResHolder<T, H extends ResHolder<T, H>> implements Holder<T, H> {

	protected Collector<H, T> m_collector;
	protected T m_mres;
	protected Reference<H> m_refid;

	/**
	 * constructor to accept its resource.
	 * 
	 * @param mres
	 *          a resource to be managed
	 */
	public ResHolder(T mres) {
		m_mres = mres;
	}
	
	/* (non-Javadoc)
	 * @see org.flowcomputing.commons.resgc.Holder#get()
	 */
	@Override
	public T get() {
		return m_mres;
	}

	/* (non-Javadoc)
	 * @see org.flowcomputing.commons.resgc.Holder#set(java.lang.Object)
	 */
	@Override
	public void set(T mres) {
		m_mres = mres;
	}
	
	
	/* (non-Javadoc)
	 * @see org.flowcomputing.commons.resgc.Holder#setCollector(java.lang.Object)
	 */
	@Override
	public void setCollector(Collector<H, T> collector) {
		m_collector = collector;
	}
	
	/* (non-Javadoc)
	 * @see org.flowcomputing.commons.resgc.Holder#setRefId(java.lang.Object)
	 */
	@Override
	public void setRefId(Reference<H> rid) {
		m_refid = rid;
	}
	
	/* (non-Javadoc)
	 * @see org.flowcomputing.commons.resgc.Holder#getRefId()
	 */
	@Override
	public Reference<H> getRefId() {
		return m_refid;
	}
	
	/* (non-Javadoc)
	 * @see org.flowcomputing.commons.resgc.Holder#cancelReclaim()
	 */
	@Override
	public void cancelReclaim() {
		if (null != m_refid && null != m_collector) {
			m_collector.unregister(m_refid);
		}
		m_collector = null;
		m_refid = null;
	}

	/* (non-Javadoc)
	 * @see org.flowcomputing.commons.resgc.Holder#destroy()
	 */
	@Override
	public void destroy() {
		if (null != m_refid && null != m_collector) {
			if (null != m_mres) {
				m_collector.destroyRes(m_refid);
			} else {
				m_collector.unregister(m_refid);
			}
		}
		m_collector = null;
		m_mres = null;
		m_refid = null;
	}
	
}
