package edu.hm.sim.inseldorf;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Server extends Thread {
	private ConcurrentLinkedQueue<Client> queue;
	private Simulation simulation;
	private boolean isbusy;
	
	public Server(Simulation sim) {
		simulation = sim;
		queue = sim.getQueue();
		isbusy = false;
	}
	
	public synchronized boolean busy() {
		return isbusy;
	}
	
	@Override
	public void run() {
		isbusy = false;
		try {
			while(true) {
				// wait until clients arrive
				while(queue.isEmpty()) Thread.sleep(0);
				Client c = queue.poll();
				c.update(simulation, Client.PROCESSING);
				
				// work
				isbusy = true;
				Thread.sleep(simulation.processClient());
				c.update(simulation, Client.FINISHED);
				isbusy = false;
			}
		} catch(InterruptedException e) {
			isbusy = false;
		}
	}
}
