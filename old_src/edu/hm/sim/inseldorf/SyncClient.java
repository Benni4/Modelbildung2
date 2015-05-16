package edu.hm.sim.inseldorf;

public class SyncClient {
	public double atSpawn;
	public double atServer;
	public double atFinish;
	public double deltaSpawn;
	public double deltaQueue;
	public double deltaProcess;
	public int id;
	
	public SyncClient(int id) {
		this.id = id;
	}
	
	public String toString() {
		return "Client #" + id + " (spawn: " + atSpawn + " | wartezeit: " + deltaQueue + " | verarbeitungszeit " + deltaProcess + "";
	}
}
