package edu.hm.sim.inseldorf;

import java.util.ArrayList;

public class DataCollector {
	public static class Type {
		public static int SPAWN = 0x0;
		public static int PROCESS = 0x1;
		public static int CLIENT = 0x2;
	}
	
	private Simulation simulation;
	private ArrayList<Client> clients;
	private ArrayList<Double> spawnTimes;
	private ArrayList<Double> processTimes;
	
	public DataCollector(Simulation sim) {
		simulation = sim;
		clients = new ArrayList<>();
		spawnTimes = new ArrayList<>();
		processTimes = new ArrayList<>();
	}
	
	public synchronized void collect(int type, Object data) {
		if(type == Type.PROCESS) {
			processTimes.add((double)data);
		} else if(type == Type.SPAWN) {
			spawnTimes.add((double)data);
		} else { // CLIENT
			clients.add((Client) data);
			// currently just print out
			System.out.println("Client " + data.toString() + " in Queue: " + simulation.getQueue().size());
		}		
	}
	
	public synchronized ArrayList<Object> get(int type) {
		return null;
	}
}
