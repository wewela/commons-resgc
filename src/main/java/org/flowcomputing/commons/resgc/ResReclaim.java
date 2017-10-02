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
     * @param rctx
     *            a resource context to be used for release
     */
    public void reclaim(ReclaimContext<MRES> rctx);
}
