package org.flowcomputing.commons.resgc;

public class ResContextWrapper<MRES> implements ContextWrapper<MRES> {

    protected MRES m_res = null;
    protected ReclaimContext m_context = null;

    public ResContextWrapper() {
        m_res = null;
        m_context = null;
    }

    public ResContextWrapper(MRES res, ReclaimContext context) {
        m_res = res;
        m_context = context;
    }

    @Override
    public MRES getRes() {
        return m_res;
    }

    @Override
    public void setRes(MRES res) {
        m_res = res;
    }

    @Override
    public ReclaimContext getContext() {
        return m_context;
    }

    @Override
    public void setContext(ReclaimContext rctx) {
        m_context = rctx;
    }

}
