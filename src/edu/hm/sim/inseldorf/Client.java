package edu.hm.sim.inseldorf;

public class Client {
	public static final int QUEUEING = 0x0;
	public static final int PROCESSING = 0x1;
	public static final int FINISHED = 0x02;
	
	private double timeAtSpawn;
	private double timeAtServer;
	private double timeAtFinish;
	private int clientNumber;
	private int state;
	
	public Client(Simulation sim) {
		timeAtSpawn = sim.time();
		clientNumber = sim.id;
		state = QUEUEING;
		timeAtServer = -1;
		timeAtFinish = -1;
	}
	
	// getters
	public int getID() {
		return clientNumber;
	}
	public double getSpawnTime() {
		return timeAtSpawn;
	}
	public double getServerTime() {
		return timeAtServer;
	}
	public double getFinishTime() {
		return timeAtFinish;
	}
	
	// setters
	public void setServerTime(double t) {
		timeAtServer = t;
		state = PROCESSING;
	}
	public void setFinishTime(double t) {
		timeAtFinish = t;
		state = FINISHED;
	}
	
	@Override
	public String toString() {
		return clientNumber + ";" + state + ";" + timeAtSpawn + ";" + timeAtServer + ";" + timeAtFinish;
	}
}
