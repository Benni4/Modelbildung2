package edu.hm.sim.inseldorf;

import java.util.ArrayList;

public class DataCollector {
	public static class Type {
		public static int SPAWN = 0x0;
		public static int PROCESS = 0x1;
		public static final int _LENGTH = 2;
	}
	
	private Simulation simulation;
	private ArrayList<ArrayList<Object>> collection;
	
	public DataCollector(Simulation sim) {
		simulation = sim;
	}
	
	public synchronized void collect(int type, Object data) {
		
	}
	
	public synchronized void notify(Client cli) {
		// currently just print out
		System.out.println("Client " + cli.toString() + " in Queue: " + simulation.getQueue().size());
	}
	
	public synchronized ArrayList<Object> get(int type) {
		return null;
	}
}
