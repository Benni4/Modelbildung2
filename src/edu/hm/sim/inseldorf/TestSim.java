package edu.hm.sim.inseldorf;

public class TestSim {
	public static void main(String...args) {
		Simulation sim = new Simulation(100, 1000, 100);
		sim.start();
	}
}
