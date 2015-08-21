package org.flowcomputing.commons.resgc;

import java.lang.ref.Reference;

/**
 * A interface of Collector that is used to collect holders who need to be
 * collected after utilization of its resource.
 * 
 * @author wg
 *
 * @param <HOLDER>
 *            holder type for any kind of resource.
 * @param <MRES>
 *            resource type to be holden.
 */
public interface Collector<HOLDER extends Holder<MRES, HOLDER>, MRES> {

	/**
	 * Register a holder and its resource to be managed.
	 * 
	 * @param holder
	 *            a holder to be registered
	 */
	public void register(HOLDER holder);

	/**
	 * Unregister a managed resource.
	 * 
	 * @param holder
	 *            a holder to be unregistered
	 */
	public void unregister(HOLDER holder);
	
	
	/**
	 * Unregister a managed resource.
	 * 
	 * @param ref
	 *            a referred resource to be unregistered
	 */
	public void unregister(Reference<HOLDER> ref);
	
	/**
	 * destroy its managed resource
	 * 
	 * @param ref 
	 * 			  a referred resource to be destroyed
	 */
	public void destroyRes(Reference<? extends HOLDER> ref);
	
	/**
	 * close this collector.
	 */
	public void close();

}
