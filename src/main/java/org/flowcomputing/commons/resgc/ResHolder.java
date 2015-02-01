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
class ResHolder<T> implements Holder<T> {

	protected Reference<? extends Holder<T>> m_refer;
	protected ResCollector<? extends Holder<T>, T>.ResDestroy m_resdestroyer;
	protected T m_mres;

	/**
	 * constructor to accept its resource.
	 * 
	 * @param mres
	 */
	public ResHolder(T mres) {
		m_mres = mres;
	}

	/* (non-Javadoc)
	 * @see org.flowcomputing.commons.resgc.Holder#registerDestroyer(org.flowcomputing.commons.resgc.ResCollector.ResDestroy)
	 */
	@Override
	public void registerDestroyer(
			ResCollector<? extends Holder<T>, T>.ResDestroy rd) {
		m_resdestroyer = rd;
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
	 * @see org.flowcomputing.commons.resgc.Holder#destroy()
	 */
	@Override
	public void destroy() {
		m_resdestroyer.destroy();
		m_mres = null;
	}

}
