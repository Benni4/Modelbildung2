package edu.hm.sim.inseldorf;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Simulation {
	public static final int ZERO = 0;
	
	public int id;
	public boolean debug;
	private ConcurrentLinkedQueue<Client> queue;
	private Scheduler scheduler;
	private Server server;
	private DataCollector collector;
	private double secondsPerMillisecond;
	
	public static  double scale; 

	private int lambdaSpawnTime;
	private int lambdaProcessTime;
	

	public Simulation(double spm, int lambdaSpawn, int lambdaProcess) {
		this(spm, lambdaSpawn, lambdaProcess, false);
	}
	
	public Simulation(double spm, int lambdaSpawn, int lambdaProcess, boolean d) {
		scale = spm;
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
		id = ZERO;
		queue = new ConcurrentLinkedQueue<Client>();
		scheduler = new Scheduler(this);
		server = new Server(this);
		collector = new DataCollector(this);
	}
	
	public void start() {
		server.start();
		scheduler.start();
	}
	
	public void pause() {
		if(!server.isInterrupted())
			server.interrupt();
		if(!scheduler.isInterrupted())
			scheduler.interrupt();
	}
	
	public long time() {
		return System.nanoTime();
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
}
