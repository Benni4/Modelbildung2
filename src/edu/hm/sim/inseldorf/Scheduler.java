package edu.hm.sim.inseldorf;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Scheduler extends Thread {
	private ConcurrentLinkedQueue<Client> queue;
	private Simulation simulation;
	
	public Scheduler(Simulation sim, ConcurrentLinkedQueue<Client> queue) {
		this.queue = queue;
		simulation = sim;
	}
	
	@Override
	public void run() {
		try {
			for(; true; simulation.id++) {
				// wait for next client
				Thread.sleep(simulation.nextClient());
				
				// generate client
				Client c = new Client(simulation);
				
				// push client to queue
				queue.offer(c);

				// send information to data collector
			}
		} catch(InterruptedException e) {
			// interrupted!
		}
	}
}
