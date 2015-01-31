package org.flowcomputing.commons.resgc;

import java.lang.ref.Reference;

abstract class ResHolder<T> {

  protected Reference<? extends ResHolder<T>> m_refer;
  protected T m_mres;
  
  public ResHolder(T mres) {
    m_mres = mres;
  }
  
  public void initIdReferring(Reference<? extends ResHolder<T>> ref) {
    m_refer = ref;
  }
  
  public Reference<? extends ResHolder<T>> getIdReferring() {
    return m_refer;
  }
  
  public T get() {
    return m_mres;
  }
  
}
