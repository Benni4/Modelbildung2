package edu.hm.sim.inseldorf.unittest;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import edu.hm.sim.inseldorf.controller.Simulation;
import edu.hm.sim.inseldorf.model.Event;
import edu.hm.sim.inseldorf.util.DataCollector;
import edu.hm.sim.inseldorf.util.EventListener;
import edu.hm.sim.inseldorf.util.Util;


public class SimulationTest implements EventListener{
	
	
	private static final double TOL = 0.00000001;
	private double time = 0;
	private double simlationEnd = 0;
	
	private double avgSpawnTimes = 0;
	private double avgProcessTimes = 0;
	
	
	private double avgQueueLenght = 0;
	private double avgNumberOfClients = 0;
	private double avgQueueWaitTime = 0; 
	private double avgTimeInShop = 0;
	private double avgServerUtility =0;
	
	private long seed1 = 0l;
	private long seed2 = 0l;
	
	private int lambdaSpawnTest =0;
	private int lambdaProcess = 0;
	double testExpS =0;
	double testExpP =0;
		
	/**
	 * Test case alle Kunden kommen mit einer höheren mittlern Ankunfsrate
	 * an als sie bedient werden mit festgelegtem random seed 
	 * Hier jeder ankommende kunde wird sofort bedient keiner bleibt in 
	 * der Schlange
	 * 
	 */
	
	@Test
	public void simaltionsTest(){
		
		int lambdaSpawnTest =1000;
		int lambdaProcess = 100;

		seed1 = 2l;
		seed2 = 5l;
		
		
		Simulation testSim1 = new Simulation(0,lambdaSpawnTest,lambdaProcess,seed1,seed2);
		
		testExpS = Util.expNumber(lambdaSpawnTest,seed1);
		testExpP = Util.expNumber(lambdaProcess,seed2);
		
		simlationEnd = testExpS + testExpP + testExpS + testExpP + testExpS;
		
		//Mittlere Warteschlangenlänge zu zeitpunkt simulationEnd
		double testMdlQueueLength = 0;
		//Severauslastung zum zeitpunkt simulationEnd
		double testValueServerUtility = (testExpP + testExpP)/simlationEnd;
		double testValueServerUtilityInPercent = testValueServerUtility*100;
		//Durchschnittliche Anzahl an Kunden im Shop zu Zeitpunkt simulationEnd
		double testAvgNumberOfClients = testValueServerUtility;
		//Zeit die jeder kunde durchschnittlich wartet
		double testMdlCustomerWaitingTime = 0;
		double testMdlCustomerStayInShopTime = testExpP;
		

		testSim1.addListener(this);
		testSim1.start();
		
		
		System.out.println("process with seed "+seed1+" \t" + testExpP + "\n Spawn  with seed "+seed2+" \t" + testExpS);
		while(time <= simlationEnd){
			
//			System.out.println("Simulation1 endet in \t" + simlationEnd +"\t"+ "current Time \t"+ time);
		
		}

		testSim1.interrupt();

		assertTrue(Math.abs(testExpS - avgSpawnTimes) < TOL);
		
		assertTrue(Math.abs(testExpP -avgProcessTimes)< TOL);
		
		
		
		
		
		//TODO in diesem test fall muss die zeit im laden der 
		//verarbeitungsezeit entsprechen
		System.out.println("Simulation1 endet in \t" + simlationEnd +"\t"+ "current Time \t"+ time);
		
		assertTrue(Math.abs(avgQueueLenght -testMdlQueueLength) < TOL );
		System.out.println(testAvgNumberOfClients);
		assertTrue(Math.abs(avgNumberOfClients - testAvgNumberOfClients) <TOL);
//		assertTrue(Math.abs(avgQueueWaitTime - testMdlCustomerStayInShopTime) <TOL);
//		assertTrue(Math.abs(avgTimeInShop - testAvgNumberOfClients) < TOL);
//		assertTrue(Math.abs(avgServerUtility - testValueServerUtilityInPercent)<TOL);
//		
	}
	
	/**
	 * 
	 * Methode den Fall testet in dem die Ankunftsrate gleich der Bedienrate ist.
	 * 
	 */
	
	public void simulationsTest2(){
		
		
		int lambdaSpawnTest =100;
		int lambdaProcess = 100;
		seed1 = 2l;
		seed2 = 2l;
		Simulation testSim1 = new Simulation(0,lambdaSpawnTest,lambdaProcess,seed1,seed2);
		
		testExpS = Util.expNumber(lambdaSpawnTest,seed1);
		testExpP = Util.expNumber(lambdaProcess,seed2);
		
		
		simlationEnd = testExpS + testExpP +testExpP;
		
		double testValueServerUtility = (testExpP + testExpP)/simlationEnd;
		double testValueTimeInShop = (testExpP *1 + testExpP *1)/simlationEnd + testValueServerUtility;
		double testMdlQueueLength = (testExpP + testExpP)/simlationEnd;
		double testMdlCustomerWaitingTime = 0;
		double testMdlCustomerStayInShopTime = testExpP;
		
		testSim1.addListener(this);
		testSim1.start();
		
		
		System.out.println("process with seed "+seed1+" \t" + testExpP + "\n Spawn  with seed "+seed2+" \t" + testExpS);
		while(time <= simlationEnd){
			
			System.out.println("Simulation endet in \t" + simlationEnd +"\t"+ "current Time \t"+ time);
		
		}
		System.out.println("Simulation endet \t"+ time);
		testSim1.interrupt();

		System.out.println(testExpS + "\t" + avgSpawnTimes );
		
		assertTrue(testExpS - avgSpawnTimes ==0);
		assertTrue(testExpP - avgProcessTimes ==0);
//		assertEquals(testExpP, avgSpawnTimes);
		
		//TODO in diesem test fall muss die zeit im laden der 
		//verarbeitungsezeit entsprechen
		
		System.out.println(avgTimeInShop);
		
		
		assertTrue(avgQueueLenght ==testMdlQueueLength );
		assertTrue(avgTimeInShop == testValueTimeInShop);
		assertTrue(avgNumberOfClients == testMdlCustomerWaitingTime);
		assertTrue(avgQueueWaitTime == testMdlCustomerStayInShopTime);
		assertTrue(avgServerUtility == testValueServerUtility);
	}
	
	
	
	
	
	
	
	
	
	/**
	 * Methode für das Abfragen der Testdaten
	 * 
	 * @param col
	 * @param ev
	 */
	@Override
	public void notify(DataCollector col, Event ev) {
		
		time = col.time();
		avgProcessTimes = col.averageClientProcessTime();
		avgSpawnTimes = col.averageClientSpawnTime();
		
		System.out.println(time);
		avgQueueLenght = col.averageQueueSize();
		avgNumberOfClients =col.averageNumberOfClients();
		avgQueueWaitTime = col.averageClientWaitTime();
		avgTimeInShop = col.averageClientInShopTime();
		avgServerUtility = col.averageServerLoad();
		
	}
	
	
	
}