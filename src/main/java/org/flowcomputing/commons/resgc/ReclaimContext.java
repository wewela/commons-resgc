package org.flowcomputing.commons.resgc;

/**
 * the context interface for reclaim operation.
 *
 * @param <MRES>
 *            resource type to be holden.
 */
public interface ReclaimContext<MRES> extends Cloneable {

    MRES getRes();

    void setRes(MRES res);

    ReclaimContext<MRES> clone();

    ReclaimContext copyTo(ReclaimContext rctx);

}

