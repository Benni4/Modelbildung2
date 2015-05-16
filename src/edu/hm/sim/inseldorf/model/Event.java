package edu.hm.sim.inseldorf.model;

public class Event implements Comparable<Event> {
	public final static int AT_SPAWN = 0x01;
	public final static int AT_SERVER = 0x02;
	public final static int AT_FINISH = 0x03;
	
	public Client client;
	public int type;
	public double time;
	
	public Event(Client client, int type, double time) {
		this.client = client;
		this.type = type;
		this.time = time;
	}

	@Override
	public int compareTo(Event o) {
		double diff = (this.time - o.time);
		return diff == 0 ? 0 : (diff > 0 ? (int) (diff + 1) : (int) (diff - 1));
	}
}
