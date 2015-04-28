package edu.hm.sim.inseldorf;

public class Server implements Runnable {
	private Queue queue;
	private boolean busy;
	
	public Queue getQueue() {
		return queue;
	}

	public void setQueue(Queue queue) {
		this.queue = queue;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

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
