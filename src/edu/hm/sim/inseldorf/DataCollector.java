package edu.hm.sim.inseldorf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataCollector {
	public static class Type {
		public static int SPAWN = 0x0;
		public static int PROCESS = 0x1;
		public static int CLIENT = 0x2;
	}

	private Simulation simulation;
	private ArrayList<Client> clients;
	private ArrayList<Double> spawnTimes;
	private ArrayList<Double> processTimes;
	private ArrayList<Integer> queueSizes;

	//Calculator usage only
	public static double sumOfPersonsInQueue = 0;
	public static int personCounter = 0;
	public static double queueTime = 0;
	
	private double qStartTime = 0;
	private double qLifeTime = 0;
	

	public DataCollector(Simulation sim) {
		simulation = sim;
		clients = new ArrayList<>();
		spawnTimes = new ArrayList<>();
		processTimes = new ArrayList<>();
		queueSizes = new ArrayList<>();
	}

	public synchronized void collect(int type, Object data) {
		if (type == Type.PROCESS) {
			processTimes.add((double) data);
		} else if (type == Type.SPAWN) {
			spawnTimes.add((double) data);
		} else { // CLIENT
			qLifeTime = qStartTime-simulation.getCurrentTime();
			qStartTime = simulation.getCurrentTime();
			clients.add((Client) data);
			queueSizes.add(simulation.getQueue().size());
			sumOfPersonsInQueue += simulation.getQueue().size()*qLifeTime;
			personCounter++;

			if (simulation.debug) {
				System.out.println("Client " + data.toString() + " in Queue: "
						+ simulation.getQueue().size());
				System.out.println(" A.WaitTime: "+ Calculator.averageClientWaitTime() + " A.ProcessTime: "+Calculator.averageClientProcessTime()
						+ " - 0=" + ((simulation.getLambdaSpawnTime() * Calculator.averageClientWaitTime()) - Calculator.averageQueueSize()));
				System.out.println(" Time: " + (simulation.time()/3600));
			}
		}
	}

	public synchronized ArrayList<Object> get(int type) {
		return null;
	}

	public synchronized void print(String path) throws IOException {
		// files
		File clientFile = new File(path.replace("/", File.separator)
				+ File.separator + "clients.txt");
		File spawnFile = new File(path.replace("/", File.separator)
				+ File.separator + "spawn.txt");
		File processFile = new File(path.replace("/", File.separator)
				+ File.separator + "process.txt");
		File queueFile = new File(path.replace("/", File.separator)
				+ File.separator + "queue.txt");

		// client output
		clientFile.createNewFile();
		FileWriter writer = new FileWriter(clientFile);
		for (Client c : clients) {
			writer.write(c.toString() + "\n");
		}
		writer.flush();
		writer.close();

		// spawn output
		spawnFile.createNewFile();
		writer = new FileWriter(spawnFile);
		for (double s : spawnTimes) {
			writer.write(s + "\n");
		}
		writer.flush();
		writer.close();

		// process output
		processFile.createNewFile();
		writer = new FileWriter(processFile);
		for (double p : processTimes) {
			writer.write(p + "\n");
		}
		writer.flush();
		writer.close();

		// queue output
		queueFile.createNewFile();
		writer = new FileWriter(queueFile);
		for (int q : queueSizes) {
			writer.write(q + "\n");
		}
		writer.flush();
		writer.close();
	}
}
