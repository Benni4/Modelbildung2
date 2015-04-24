package edu.hm.sim.inseldorf;

public class Scheduler {
	private Queue queue;
	
	public Scheduler(Queue queue) {
		this.queue = queue;
	}

	public void clientArrive(Client client) {
		this.queue.push(client);
	}
}
