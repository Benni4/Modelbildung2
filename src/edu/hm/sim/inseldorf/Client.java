package edu.hm.sim.inseldorf;

public class Client {
	public static final int QUEUEING = 0x0;
	public static final int PROCESSING = 0x1;
	public static final int FINISHED = 0x02;
	
	private long timeAtSpawn;
	private long timeAtServer;
	private long timeAtFinish;
	private int clientNumber;
	private int state;
	
	public Client(Simulation sim) {
		state = -1;
		clientNumber = sim.id++;
		timeAtServer = -1;
		timeAtFinish = -1;
		update(sim, QUEUEING);
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
	
	public void update(Simulation sim, int state) {
		if(state > 3 || state <= this.state) return; // restricted update
		
		this.state = state;
		switch(state) {
			case PROCESSING:
				timeAtServer = sim.time();
				break;
			case FINISHED:
				timeAtFinish = sim.time();
				break;
			default:
				timeAtSpawn = sim.time();
				break;
		}
		sim.getCollector().collect(DataCollector.Type.CLIENT, this);
	}
	@Override
	public String toString() {
		return clientNumber + ";" + state + ";" + timeAtSpawn + ";" + 
				timeAtServer + ";" + timeAtFinish;
	}
}
