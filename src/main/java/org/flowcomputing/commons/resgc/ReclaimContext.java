package org.flowcomputing.commons.resgc;

/**
 * the context interface for reclaim operation.
 *
 * @param <MRES>
 *            resource type to be holden.
 */
public interface ReclaimContext<MRES> {

    MRES getRes();

    void setRes(MRES res);

}
