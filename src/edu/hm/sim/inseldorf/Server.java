package edu.hm.sim.inseldorf;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Server extends Thread {
	private ConcurrentLinkedQueue<Client> queue;
	private Simulation simulation;
	
	public Server(Simulation sim, ConcurrentLinkedQueue<Client> queue) {
		this.queue = queue;
		simulation = sim;
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				// wait until clients arrive
				while(queue.isEmpty());
				Client c = queue.poll();
				c.update(simulation, Client.PROCESSING);
				
				// work
				Thread.sleep(simulation.processClient());
				c.update(simulation, Client.FINISHED);
			}
		} catch(InterruptedException e) {
			
		}
	}
}
