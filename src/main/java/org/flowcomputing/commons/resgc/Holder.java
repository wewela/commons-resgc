package org.flowcomputing.commons.resgc;

import java.lang.ref.Reference;

/**
 * A interface that defines a holder. it is used to hold any kind of resource
 * and provide way to access its holden resource. In addition, it should allow
 * its resource to be destroyed manually ahead of GC.
 * 
 * @author wg
 *
 * @param <T> a resource type for its holder 
 */
public interface Holder<T, H extends Holder<T, H>> {
	
	/**
	 * get its holden resource. Note that it should not be used for assignment.
	 * 
	 * @return its holden resource
	 */
	public T get();

	/**
	 * set its holden resource.
	 * 
	 * @param mres
	 *            the holder will be set to a specified resource.
	 */
	public void set(T mres);
		
	/**
	 * set collector.
	 * 
	 * @param collector
	 *            the collector to manage this holder.
	 */
	public void setCollector(Collector<H, T> collector);
	
	/**
	 * set reference id.
	 * 
	 * @param rid
	 *            the reference id to be managed.
	 */
	public void setRefId(Reference<H> rid);
	
	/**
	 * get its managed reference id.
	 * 
	 * @return its managed reference id
	 */
	public Reference<H> getRefId();

	/**
	 * prevent resource from being reclaimed.
	 * 
	 */
	public void cancelReclaim();
	
	/**
	 * destroy its holden resource and unregister from its collector.
	 * 
	 */
	public void destroy();
		
}
