package org.flowcomputing.commons.resgc;

/**
 * the context wrapper interface for registering and callback.
 *
 * @param <MRES>
 *            resource type to be holden.
 */
public interface ContextWrapper<MRES> {

    MRES getRes();

    void setRes(MRES res);

    ReclaimContext getContext();

    void setContext(ReclaimContext rctx);
}
