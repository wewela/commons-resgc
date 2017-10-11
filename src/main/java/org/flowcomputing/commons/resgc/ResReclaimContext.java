package org.flowcomputing.commons.resgc;

public class ResReclaimContext implements ReclaimContext {

    protected int m_modeid;

    public ResReclaimContext() {
        m_modeid = 0;
    }

    public ResReclaimContext(int modeid) {
        m_modeid = modeid;
    }

    @Override
    public ResReclaimContext clone() {
        return new ResReclaimContext(m_modeid);
    }

    public int getModeId() {
        return m_modeid;
    }

    public void setModeId(int modeid) {
        this.m_modeid = modeid;
    }

}

