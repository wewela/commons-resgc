package org.flowcomputing.commons.resgc;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.util.Map;
import java.util.HashMap;

public class ResCollector<HOLDER extends ResHolder<MRES>, MRES> {

    private final ReferenceQueue<HOLDER> refque = new ReferenceQueue<HOLDER>();
    private final Map<Reference<? extends ResHolder<MRES>>, MRES> refmap = new HashMap<Reference<? extends ResHolder<MRES>>, MRES>();
    
    private ResReclaim<MRES> m_reclaimer;
    private Thread m_collector;
  
    public ResCollector(ResReclaim<MRES> reclaimer) {
        m_reclaimer = reclaimer;
		m_collector = new Thread() {
			public void run() {
				while (true) {
					try {
						Reference<? extends HOLDER> ref = refque.remove();
                        MRES mres = refmap.remove(ref);
                        if (null != mres) {
                            m_reclaimer.reclaim(mres);
                        }
					} catch (InterruptedException ex) {
						break;
					}
				}
			}
		};
        m_collector.setDaemon(true);
        m_collector.start();
    }
  
    public void register(HOLDER holder, MRES mres) {
        holder.initIdReferring(new PhantomReference<HOLDER>(holder, refque));
        refmap.put(holder.getIdReferring(), mres);
    }
    
    public void destroy(HOLDER holder) {
        refmap.remove(holder.getIdReferring());
        if (null != holder.get()) {
            m_reclaimer.reclaim(holder.get());
        }
    }
  
}