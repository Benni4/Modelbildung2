package edu.hm.sim.inseldorf.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import edu.hm.sim.inseldorf.model.Client;
import edu.hm.sim.inseldorf.model.Event;
import edu.hm.sim.inseldorf.util.DataCollector;
import edu.hm.sim.inseldorf.util.EventListener;

public class Simulation extends Thread {
	public int lambdaSpawn;
	public int lambdaProcess;
	public int currentID = 0;
	
	public double currentSpawnTime = 0;
	public double lastSpawnTime = 0;
	public double serverBusyTill = 0;
	public double secondsPerMillisecond = 1;
	
	private DataCollector collector;
	
	public ArrayList<Event> events = new ArrayList<>();
	public ArrayList<Client> clients = new ArrayList<>();
	
	public Simulation(int spm, int lambdaSpawn, int lambdaProcess) {
		this.lambdaSpawn = lambdaSpawn;
		this.lambdaProcess = lambdaProcess;
		this.secondsPerMillisecond = spm;
		collector = new DataCollector(this);
	}
	
	public void addListener(EventListener ev) {
		collector.addListener(ev);
	}
	
	public void addEvent(Event e) {
		events.add(e); // TODO use event pool?
		Collections.sort(events); // TODO optimize?
	}
	
	public void processEventsUntil(double time) {
		Iterator<Event> it = events.iterator();
		while(it.hasNext()) {
			Event ev = it.next();
			if(ev.time < time) {
				collector.process(ev);
				it.remove();
			}
		}
	}
	
	public Client generateClient() {
		Client c = new Client(this); // TODO use client pool?
		clients.add(c);
		return c;
	}

	@Override
	public void run() {
		try {
			while(true) {
				generateClient();
				processEventsUntil(currentSpawnTime);
				
				if(secondsPerMillisecond > 0) {
					int deltans = (int) ((currentSpawnTime - lastSpawnTime)/
							(secondsPerMillisecond/1000000));
					int deltams = deltans/1000000;
					deltans -= deltams * 1000000;
					lastSpawnTime = currentSpawnTime;
					if(deltams > 0 || deltans > 0) {
						Thread.sleep(deltams, deltans);
					}
				}
			}
		} catch(InterruptedException e) {
			// paused
		}
	}
}