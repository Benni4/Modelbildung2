package edu.hm.sim.inseldorf.view;

import edu.hm.sim.inseldorf.controller.Simulation;
import edu.hm.sim.inseldorf.model.Event;
import edu.hm.sim.inseldorf.util.DataCollector;
import edu.hm.sim.inseldorf.util.EventListener;

public class ConsoleMain implements EventListener {
	public static void main(String...args) {
<<<<<<< HEAD
<<<<<<< HEAD
		Simulation sim = new Simulation(10, 1000, 1000);
=======
		Simulation sim = new Simulation(100, 1000, 100);
>>>>>>> origin/master
=======
		Simulation sim = new Simulation(100, 1000, 100);
>>>>>>> origin/master
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
		//System.out.println("Vergangene Zeit: " + (int)(col.time()/24) + " Tage "+(int)col.time()%24 + " Stunden");
		System.out.println("Vergangene Zeit: " + (col.time()) + " in Stunden");
<<<<<<< HEAD

		System.out.println("Little 0=lambda*d-Q: 0 ≐ " + (1/col.averageClientSpawnTime() * 
=======
		System.out.println("Little1 0=lambda*d-Q: 0 ≐ " + (1/col.averageClientSpawnTime() * 
>>>>>>> origin/master
				col.averageClientWaitTime() - col.averageQueueSize()) + " = 1/" + col.averageClientSpawnTime() +
				" * " + col.averageClientWaitTime() + " - " + col.averageQueueSize() );
		System.out.println("Little2 0=lambda*w-L: 0 ≐ " + (1/col.averageClientSpawnTime() * 
				col.averageClientInShopTime() - col.averageNumberOfClients()) + "= 1/" + col.averageClientSpawnTime() +
				" * " + col.averageClientInShopTime() + " - " + col.averageNumberOfClients());
		System.out.println("Pools: C " + Simulation.ClientPool.size() + " - E " + Simulation.EventPool.size());
	}
}
