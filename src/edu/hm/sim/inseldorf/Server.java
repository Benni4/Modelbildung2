package edu.hm.sim.inseldorf;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Server extends Thread {
	private ConcurrentLinkedQueue<Client> queue;
	private Simulation simulation;
	private boolean isbusy;
	
	//Calculator usage only
	public static long simulationStartTime;
	public static long workTime = 0;
	public static double sumClientWaitTime = 0;
	public static double sumClientProcessTime = 0;
	public static int clientCounter = 0;
	public static int finishedClientCounter = 0;
	
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
		simulationStartTime = System.currentTimeMillis();
		isbusy = false;
		try {
			while(true) {
				// wait until clients arrive
				while(queue.isEmpty()) Thread.sleep(0,1); // just sleep 1 ns
				Client c = queue.poll();
				c.update(simulation, Client.PROCESSING);
				sumClientWaitTime +=(c.getServerTime()-c.getSpawnTime());
				clientCounter++;
				
				// work
				isbusy = true;
				long startWork = System.currentTimeMillis();
				Thread.sleep(simulation.processClient());
				c.update(simulation, Client.FINISHED);
				sumClientProcessTime+=(c.getFinishTime()-c.getSpawnTime());
				
				finishedClientCounter++;
				workTime += System.currentTimeMillis()-startWork;
				isbusy = false;
			}
		} catch(InterruptedException e) {
			isbusy = false;
		}
	}
}
