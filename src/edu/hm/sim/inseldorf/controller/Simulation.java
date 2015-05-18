package edu.hm.sim.inseldorf.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import edu.hm.sim.inseldorf.model.Client;
import edu.hm.sim.inseldorf.model.Client.ClientFactory;
import edu.hm.sim.inseldorf.model.Event;
import edu.hm.sim.inseldorf.model.Event.EventFactory;
import edu.hm.sim.inseldorf.util.DataCollector;
import edu.hm.sim.inseldorf.util.EventListener;
import edu.hm.sim.inseldorf.util.Pool;
import edu.hm.sim.inseldorf.util.Util;

public class Simulation extends Thread {
	public static Pool<Client> ClientPool = new Pool<Client>(new ClientFactory());
	public static Pool<Event> EventPool = new Pool<Event>(new EventFactory());

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

	public Simulation(double spm, int lambdaSpawn, int lambdaProcess) {
		this.lambdaSpawn = lambdaSpawn;
		this.lambdaProcess = lambdaProcess;
		this.secondsPerMillisecond = spm;
		collector = new DataCollector(this);
	}

	public void addListener(EventListener ev) {
		collector.addListener(ev);
	}

	public void addEvent(Event e) {
        int i = 0;
        boolean found = false;
        while (!found && (i < events.size())) {
            found = e.compareTo(events.get(i)) < 0;
            if (!found) {
                i++;
            }
        }
        events.add(i,e);
	}

	public void processEventsUntil(double time) {
		Iterator<Event> it = events.iterator();
		while(it.hasNext()) {
			Event ev = it.next();
			if(ev.time < time) {
				collector.process(ev);
				EventPool.free(ev);
				it.remove();
			}
		}
	}

	public Client generateClient() {
		Client c = ClientPool.alloc(this);
		clients.add(c);
		return c;
	}

	@Override
	public void run() {
		try {
			while(true) {
				generateClient();
				//Mathematica Export
				processEventsUntil(currentSpawnTime);

				if(secondsPerMillisecond > 0) {
					int deltans = (int) ((currentSpawnTime - lastSpawnTime)/
							(secondsPerMillisecond/1000000));
					int deltams = deltans/1000000;
					deltans -= deltams * 1000000;
					lastSpawnTime = currentSpawnTime;
					collector.collect();
					collector.exportData();
					if(deltams > 0 || deltans > 0) {
						Thread.sleep(deltams, deltans);
					}
				}
			}
		} catch(InterruptedException | IOException e) {
			// paused
		}
	}
}
