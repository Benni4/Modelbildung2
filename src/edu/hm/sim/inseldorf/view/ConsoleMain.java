package edu.hm.sim.inseldorf.view;

import edu.hm.sim.inseldorf.controller.Simulation;
import edu.hm.sim.inseldorf.model.Event;
import edu.hm.sim.inseldorf.util.DataCollector;
import edu.hm.sim.inseldorf.util.EventListener;

public class ConsoleMain implements EventListener {
	public static void main(String...args) {
		Simulation sim = new Simulation(10, 1000, 100);
		sim.addListener(new ConsoleMain());
		sim.start();
	}

	@Override
	public void notify(DataCollector col, Event ev) {
		System.out.println("=======================================================");
		System.out.println("Verarbeitete Clients: " + col.getClientCount());
		System.out.println("mittlere Warteschlangenlänge: " + col.averageQueueSize());
		System.out.println("mittlere Anzahl der Kunden im Shop: " + col.averageNumberOfClients());
		System.out.println("mittlere Anstehzeit: " + col.averageClientWaitTime());
		System.out.println("mittlere Verweildauer im Shop: " + col.averageClientInShopTime());
		System.out.println("mittlere Serverauslastung: " + col.averageServerLoad());
		System.out.println("Vergangene Zeit: " + (col.time()) + " in Stunden");
		System.out.println("Little 0=lambda*d-Q: 0 ≐ " + (1/col.averageClientSpawnTime() * 
				col.averageClientWaitTime() - col.averageQueueSize()) + " = 1/" + col.averageClientSpawnTime() +
				" * " + col.averageClientWaitTime() + " - " + col.averageQueueSize() );
	}
}
