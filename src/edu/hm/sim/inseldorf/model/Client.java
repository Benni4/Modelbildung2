package edu.hm.sim.inseldorf.model;

import edu.hm.sim.inseldorf.controller.Simulation;
import edu.hm.sim.inseldorf.util.Util;

public class Client {
	public int id;
	public double atSpawn;
	public double atServer;
	public double atFinish;
	public double deltaSpawn;
	public double deltaQueue;
	public double deltaProcess;

	public Client(Simulation sim) {
		id = sim.currentID++;

		deltaSpawn = Util.expNumber(sim.lambdaSpawn);
		atSpawn = (sim.currentSpawnTime += deltaSpawn);
		sim.addEvent(new Event(this, Event.AT_SPAWN, atSpawn));

		deltaQueue = Util.cutNegative(sim.serverBusyTill - atSpawn);
		atServer = atSpawn + deltaQueue;
		sim.addEvent(new Event(this, Event.AT_SERVER, atServer));

		deltaProcess = Util.expNumber(sim.lambdaProcess);
		atFinish = (sim.serverBusyTill = atServer + deltaProcess);
		sim.addEvent(new Event(this, Event.AT_FINISH, atFinish));
	}

	public String toString() {
		return "Client #" + id + " (spawn: " + atSpawn + " - " + atServer +
				" - " + atFinish + " | wartezeit: " + deltaQueue + 
				" | verarbeitungszeit " + deltaProcess + "";
	}
}
