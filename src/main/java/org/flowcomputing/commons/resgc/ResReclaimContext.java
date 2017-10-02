package org.flowcomputing.commons.resgc;

public class ResReclaimContext<MRES> implements ReclaimContext<MRES> {

    protected MRES m_res = null;

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
}
