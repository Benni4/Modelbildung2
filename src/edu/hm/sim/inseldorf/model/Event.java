package edu.hm.sim.inseldorf.model;

import edu.hm.sim.inseldorf.util.Pool.PoolFactory;
import edu.hm.sim.inseldorf.util.Pool.Poolable;

public class Event extends Poolable implements Comparable<Event> {
	public static class EventFactory implements PoolFactory<Event> {
		@Override
		public Event create() {
			return new Event();
		}
	}
	public final static int UNDEF = 0x00;
	public final static int AT_SPAWN = 0x01;
	public final static int AT_SERVER = 0x02;
	public final static int AT_FINISH = 0x03;
	
	public Client client;
	public int type;
	public double time;
	
	public void _init(Client client, int type, double time) {
		this.client = client;
		this.type = type;
		this.time = time;
	}

	@Override
	public int compareTo(Event o) {
		double diff = (this.time - o.time);
		return diff == 0 ? 0 : (diff > 0 ? (int) (diff + 1) : (int) (diff - 1));
	}

	@Override
	public void init(Object... args) {
		_init((Client) args[0], (int) args[1], (double) args[2]);
	}

	@Override
	public void destroy() {
		client = null;
		time = type = 0;
	}
}
