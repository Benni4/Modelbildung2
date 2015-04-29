package edu.hm.sim.inseldorf;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Simulation extends Thread {
	private double currentTime;
	public int id;
	private Scheduler scheduler;
	private Server server;
	private double secondsPerMillisecond;
	
	public Simulation(double secondsPerMillisecond) {
		currentTime = 0.0;
		id = 0;
		ConcurrentLinkedQueue<Client> queue = new ConcurrentLinkedQueue<Client>();
		scheduler = new Scheduler(this, queue);
		server = new Server(this, queue);
		this.secondsPerMillisecond = secondsPerMillisecond;
	}
	
	public void reset(double secondsPerMillisecond) {
		this.secondsPerMillisecond = secondsPerMillisecond;
	}
	
	public void pause() {
		interrupt();
	}
	
	public double time() {
		return currentTime;
	}
	
	public long nextClient() {
		return (long) (5.0/secondsPerMillisecond); // TODO distribute
	}

	public long processClient() {
		return (long) (5.0/secondsPerMillisecond); // TODO distribute
	}
	
	@Override
	public void run() {
		server.start();
		scheduler.start();
		try {
			while(true) {
				currentTime += secondsPerMillisecond;
				Thread.sleep(1);
			}
		} catch(InterruptedException e) {
			// paused
			server.interrupt();
			scheduler.interrupt();
		}
	}
}
