package edu.hm.sim.inseldorf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestSim {
	public static void main(String...args) throws IOException, InterruptedException {
		Simulation sim = new Simulation(10, 10, 1, true);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String r = null;

		while(true) {
			Thread.sleep(1);
			System.out.print(" > ");
			r = reader.readLine();
			
			switch(r) {
			case "start":
				sim.start();
				break;
			case "pause":
				sim.pause();
				break;
			case "exit":
				sim.getCollector().print("resources");
				sim.reset();
				break;
			case "quit":
				return;
			}
		}
	
	}
}
