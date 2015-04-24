package edu.hm.sim.inseldorf;

public class Server implements Runnable {
	private Queue queue;
	
	public Server(Queue queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		while(true) {
			// check queue
			
			// work
		}
	}
}
