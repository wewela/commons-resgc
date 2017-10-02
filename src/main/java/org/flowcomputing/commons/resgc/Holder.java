package org.flowcomputing.commons.resgc;

import java.lang.ref.Reference;

/**
 * A interface that defines a holder. it is used to hold any kind of resource
 * and provide way to access its holden resource. In addition, it should allow
 * its resource to be destroyed manually ahead of GC.
 * 
 * @author wg
 *
 * @param <T> a resource type for its holder 
 */
public interface Holder<T, H extends Holder<T, H>> {

    /**
     * get its holden resource. Note that it should not be used for assignment.
     *
     * @return its holden resource
     */
    public T get();

    /**
     * set its holden resource.
     *
     * @param mres
     *            the holder will be set to a specified resource.
     *
     * Note: must unregister this holder before changing its managed
     *       resource.
     */
    public void set(T mres);

    /**
     * clear its managed resource
     */
    public void clear();

    /**
     * return whether it has managed resource
     *
     * @return if hold its managed resource
     */
    public boolean hasResource();

    /**
     * set collector.
     *
     * @param collector
     *            the collector to manage this holder.
     */
    public void setCollector(Collector<H, T> collector);

    /**
     * set reference id.
     *
     * @param rid
     *            the reference id to be managed.
     */
    public void setRefId(Reference<H> rid);

    /**
     * get its managed reference id.
     *
     * @return its managed reference id
     */
    public Reference<H> getRefId();

    /**
     * determine if this holder is enabled to be auto reclaimed.
     *
     * @return true if autoclaim is enabled
     */
    public boolean autoReclaim();

    /**
     * prevent resource from being reclaimed.
     *
     */
    public void cancelAutoReclaim();

    /**
     * destroy its holden resource and unregister from its collector.
     *
     */
    public void destroy();

    public void destroy(ReclaimContext<T> rctx);
}
