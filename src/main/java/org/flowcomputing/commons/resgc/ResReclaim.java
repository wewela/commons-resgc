package org.flowcomputing.commons.resgc;

/**
 * Reclaim any kind of resource. it is customized by client code that is
 * specific to how to destroy a kind of specified resource.
 * 
 * @author wg
 *
 * @param <MRES>
 */
public interface ResReclaim<MRES> {

	/**
	 * reclaim a specified resource.
	 * 
	 * @param mres
	 *            a resource to be ready for release
	 */
	public void reclaim(MRES mres);
}
