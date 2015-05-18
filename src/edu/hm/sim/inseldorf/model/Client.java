package edu.hm.sim.inseldorf.model;

import edu.hm.sim.inseldorf.controller.Simulation;
import edu.hm.sim.inseldorf.util.Pool.PoolFactory;
import edu.hm.sim.inseldorf.util.Pool.Poolable;
import edu.hm.sim.inseldorf.util.Util;

public class Client extends Poolable {
	public static class ClientFactory implements PoolFactory<Client> {
		@Override
		public Client create() {
			return new Client();
		}
	}
	
	public int id;
	public double atSpawn;
	public double atServer;
	public double atFinish;
	public double deltaSpawn;
	public double deltaQueue;
	public double deltaProcess;

	public void _init(Simulation sim) {
		id = sim.currentID++;

		deltaSpawn = Util.expNumber(sim.lambdaSpawn);
		atSpawn = (sim.currentSpawnTime += deltaSpawn);
		sim.addEvent(Simulation.EventPool.alloc(this, Event.AT_SPAWN, atSpawn));

		deltaQueue = Util.cutNegative(sim.serverBusyTill - atSpawn);
		atServer = atSpawn + deltaQueue;
		sim.addEvent(Simulation.EventPool.alloc(this, Event.AT_SERVER, atServer));

		deltaProcess = Util.expNumber(sim.lambdaProcess);
		atFinish = (sim.serverBusyTill = atServer + deltaProcess);
		sim.addEvent(Simulation.EventPool.alloc(this, Event.AT_FINISH, atFinish));
	}

	public String toString() {
		return "Client #" + id + " (spawn: " + atSpawn + " - " + atServer +
				" - " + atFinish + " | wartezeit: " + deltaQueue + 
				" | verarbeitungszeit " + deltaProcess + "";
	}

	@Override
	public void init(Object... args) {
		_init((Simulation) args[0]);
	}

	@Override
	public void destroy() {
		atSpawn = atServer = atFinish = deltaSpawn = deltaQueue = deltaProcess = id = 0;
	}
}
