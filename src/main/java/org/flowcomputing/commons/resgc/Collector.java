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
	 * Remove a Ref that cannot be used alone.
	 * 
	 * @param ref
	 *            a reference object to be removed
	 */
	public void removeRef(Reference<HOLDER> ref);
	
	/**
	 * destroy its managed resource
	 * 
	 * @param ref 
	 * 			  a referred resource to be destroyed
	 */
	public void destroyRes(Reference<? extends HOLDER> ref);
	
	/**
	 * destroy the resource
	 *
	 * @param mres
	 * 			  a resource to be destroyed
	 */
	public void destroyRes(MRES mres);

	/**
	 * close this collector.
	 *
	 * @param recltmout
	 *            specify a timeout for reclaiming
	 *
	 * @param termtmout
	 *            specify a timeout for terminating worker thread
	 *
	 * @return true if closed gracefully otherwise timeout.
	 */
	public boolean close(long recltmout, long termtmout);

}
