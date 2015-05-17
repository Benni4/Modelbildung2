package edu.hm.sim.inseldorf.util;

import java.util.ArrayList;
import java.util.Iterator;

import edu.hm.sim.inseldorf.controller.Simulation;
import edu.hm.sim.inseldorf.model.Client;
import edu.hm.sim.inseldorf.model.Event;

public class DataCollector {
	private ArrayList<Double> spawnTimes = new ArrayList<>();
	private ArrayList<Double> processTimes = new ArrayList<>();
	private ArrayList<Double> queueTimes = new ArrayList<>();
	private ArrayList<EventListener> listeners = new ArrayList<>();
	private Simulation simulation;

	private double lastEvent = 0;

	private int cNumberOfClients;
	private int cQueueSize;
	private int cServerSize;

	private int cClientSpawnTime = 0;
	private int cClientWaitTime = 0;
	private int cClientProcessTime = 0;

	private double avgQueueSize = 0;
	private double avgServerLoad = 0;
	private double avgClientSpawnTime = 0;
	private double avgClientWaitTime = 0;
	private double avgClientProcessTime = 0;
	private double avgNumberOfClients = 0;

	public DataCollector(Simulation sim) {
		simulation = sim;
	}

	public int getClientCount() {
		return simulation.currentID;
	}

	public ArrayList<Double> getSpawnTimes() {
		return spawnTimes;
	}

	public ArrayList<Double> getProcessTimes() {
		return processTimes;
	}

	public ArrayList<Double> getQueueTimes() {
		return queueTimes;
	}

	public double time() {
		return lastEvent/3600;
	}

	public double averageQueueSize() {
		return avgQueueSize / lastEvent;
	}

	public double averageNumberOfClients() {
		return avgNumberOfClients / lastEvent;
	}

	public double averageServerLoad() {
		return avgServerLoad / lastEvent;
	}

	public double averageClientSpawnTime() {
		return avgClientSpawnTime / cClientSpawnTime;
	}

	public double averageClientWaitTime() {
		return avgClientWaitTime / cClientWaitTime;
	}

	public double averageClientProcessTime() {
		return avgClientProcessTime / cClientProcessTime;
	}

	public double averageClientInShopTime() {
		return (avgClientWaitTime + avgClientProcessTime) / getClientCount();
	}

	public void addListener(EventListener ev) {
		listeners.add(ev);
	}

	public void process(Event event) {
		double delta = event.time - lastEvent;
		lastEvent = event.time;

		cQueueSize = 0;
		cNumberOfClients = 0;
		cServerSize = 0;

		Iterator<Client> it = simulation.clients.iterator();
		while(it.hasNext()) {
			Client client = it.next();
			if(client.atFinish <= event.time) {
				Simulation.ClientPool.free(client);
				it.remove(); // remove finished clients from the system
			} else {
				if(client.atSpawn <= event.time && client.atServer > event.time) { // in queue
					cNumberOfClients++;
					cQueueSize++;
				} else if(client.atServer <= event.time && client.atFinish > event.time){ // at server
					cNumberOfClients++;
					cServerSize++; // should be one or zero
				}
			}
		}
		if(cServerSize > 1) throw new RuntimeException("Server can not process more than 1 client at a time");
		avgQueueSize += cQueueSize * delta;
		avgServerLoad += cServerSize * delta;
		avgNumberOfClients += cNumberOfClients * delta;

		switch(event.type) {
			case Event.AT_SPAWN:
				avgClientSpawnTime += event.client.deltaSpawn;
				spawnTimes.add(event.client.deltaSpawn);
				cClientSpawnTime++;
				break;
			case Event.AT_SERVER:
				avgClientWaitTime += event.client.deltaQueue;				
				queueTimes.add(event.client.deltaQueue);
				cClientWaitTime++;
				break;
			default: // AT_FINISH
				avgClientProcessTime += event.client.deltaProcess;				
				processTimes.add(event.client.deltaProcess);
				cClientProcessTime++;
				break;
		}

		for(EventListener el : listeners) {
			el.notify(this, event);
		}
		
		Simulation.EventPool.free(event);
	}
}
