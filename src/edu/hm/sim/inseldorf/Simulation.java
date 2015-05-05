package edu.hm.sim.inseldorf;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Simulation extends Thread {
	public static final int ZERO = 0;
	
	public int id;
	public boolean debug;
	private double currentTime;
	private ConcurrentLinkedQueue<Client> queue;
	private Scheduler scheduler;
	private Server server;
	private DataCollector collector;
	private double secondsPerMillisecond;
	private double lambdaSpawnTime;
	private double lambdaProcessTime;
	
	public Simulation(double spm, double lambdaSpawn, double lambdaProcess) {
		this(spm, lambdaSpawn, lambdaProcess, false);
	}
	
	public Simulation(double spm, double lambdaSpawn, double lambdaProcess, boolean d) {
		currentTime = ZERO;
		id = ZERO;
		queue = new ConcurrentLinkedQueue<Client>();
		scheduler = new Scheduler(this);
		server = new Server(this);
		collector = new DataCollector(this);
		secondsPerMillisecond = spm;
	    lambdaSpawnTime = lambdaSpawn;
		lambdaProcessTime = lambdaProcess;
		debug = d;
	}
	
	public void reset() {
		pause();
		currentTime = ZERO;
		id = ZERO;
		queue = new ConcurrentLinkedQueue<Client>();
		scheduler = new Scheduler(this);
		server = new Server(this);
		collector = new DataCollector(this);
	}
	
	public void pause() {
		if(!interrupted())
			interrupt();
	}
	
	public double time() {
		return currentTime;
	}
	
	public void setSecondsPerMillisecond(double s) {
		secondsPerMillisecond = s;
	}
	
	public void setLambdaSpawnTime(int lambda) {
		lambdaSpawnTime = lambda;
	}
	
	public void setLambdaProcessTime(int lambda) {
		lambdaProcessTime = lambda;
	}
	
	public Scheduler getScheduler() {
		return scheduler;
	}
	
	public Server getServer() {
		return server;
	}
	
	public DataCollector getCollector() {
		return collector;
	}
	
	public ConcurrentLinkedQueue<Client> getQueue() {
		return queue;
	}
	
	public long nextClient() {
		double distribution = ExponentialNumber.expNumber(lambdaSpawnTime);
		collector.collect(DataCollector.Type.SPAWN, distribution);
		return (long) (distribution/secondsPerMillisecond);
	}

	public long processClient() {
		double distribution = ExponentialNumber.expNumber(lambdaProcessTime);
		collector.collect(DataCollector.Type.PROCESS, distribution);
		return (long) (distribution/secondsPerMillisecond);
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
