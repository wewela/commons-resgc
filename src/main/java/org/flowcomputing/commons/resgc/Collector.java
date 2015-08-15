package org.flowcomputing.commons.resgc;

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
public interface Collector<HOLDER extends Holder<MRES>, MRES> {

	/**
	 * Register a holder and its bound resource.
	 * 
	 * @param holder
	 *            a holder to be registered
	 */
	public void register(HOLDER holder);

	/**
	 * Unregister a bound resource.
	 * 
	 * @param holder
	 *            a holder to be registered
	 */
	public void unregister(HOLDER holder);

}
