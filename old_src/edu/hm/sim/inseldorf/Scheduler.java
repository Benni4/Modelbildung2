package edu.hm.sim.inseldorf;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Scheduler extends Thread {
	private ConcurrentLinkedQueue<Client> queue;
	private Simulation simulation;
	
	public Scheduler(Simulation sim) {
		simulation = sim;
		queue = sim.getQueue();
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				// wait for next client
				Thread.sleep(simulation.nextClient());
				
				// generate client
				Client c = new Client(simulation);
				
				// push client to queue
				queue.offer(c);
			}
		} catch(InterruptedException e) {
			// interrupted!
		}
	}
}
