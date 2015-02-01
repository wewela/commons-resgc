package org.flowcomputing.commons.resgc;

/**
 * A interface that defines a holder. it is used to hold any kind of resource
 * and provide way to access its holden resource. In addition, it should allow
 * its resource to be destroyed manually ahead of GC.
 * 
 * @author wg
 *
 * @param <T> a resource type for its holder 
 */
public interface Holder<T> {

	/**
	 * Register a destroyer to destroy its holden resource.
	 * 
	 * @param rd
	 *            a destroyer
	 */
	public void registerDestroyer(
			ResCollector<? extends Holder<T>, T>.ResDestroy rd);

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
	 */
	public void set(T mres);

	/**
	 * destroy its holden resource and unregister from its collector.
	 * 
	 */
	public void destroy();
}
