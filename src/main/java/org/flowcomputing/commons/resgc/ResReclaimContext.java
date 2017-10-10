package org.flowcomputing.commons.resgc;

public class ResReclaimContext<MRES> implements ReclaimContext<MRES> {

    protected MRES m_res = null;

    public ResReclaimContext() {
        m_res = null;
    }

    public ResReclaimContext(MRES res) {
        setRes(res);
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
    public ResReclaimContext<MRES> clone() {
        return new ResReclaimContext<MRES>(m_res);
//        try {
//            return (ResReclaimContext<MRES>) super.clone();
//        } catch (CloneNotSupportedException e) {
//            throw new RuntimeException("Clone is not allowed.");
//        }
    }

    @Override
    public ReclaimContext copyTo(ReclaimContext rctx) {
        return rctx;
    }
}

