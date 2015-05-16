package edu.hm.sim.inseldorf.util;

import java.util.ArrayList;

import edu.hm.sim.inseldorf.util.Pool.Poolable;

public class Pool<E extends Poolable> {
	public static abstract class Poolable {
		public abstract void init(Object...args);
		public abstract void destroy();
	}
	
	public interface PoolFactory<E> {
		public E create();
	}

	public ArrayList<E> all;
	public ArrayList<E> pool;
	public PoolFactory<E> factory;

	public Pool(PoolFactory<E> f) {
		factory = f;
		pool = new ArrayList<>();
		all = new ArrayList<>();
	}
	
	public int size() {
		return all.size();
	}

	public E alloc(Object...args) {
		E e;
		if(pool.isEmpty()) {
			e = factory.create();
			all.add(e);
		} else {
			e = pool.remove(0);
		}
		e.init(args);
		return e;
	}

	public void free(E obj) {
		pool.add(obj);
	}
}
