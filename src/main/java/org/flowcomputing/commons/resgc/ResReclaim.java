package org.flowcomputing.commons.resgc;

/**
 * Reclaim any kind of resource. it is customized by client code that is
 * specific to how to destroy a kind of specified resource.
 * 
 * @author wg
 *
 * @param <MRES> the type of a resource
 */
public interface ResReclaim<MRES> {

    /**
     * reclaim a specified resource.
     *
     * @param cw
     *            a resource context wrapper to be used for resource release
     */
    public void reclaim(ContextWrapper<MRES> cw);
}
