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
	
	
	private double time = 0;
	private double simlationEnd = 0;
	
	private double avgSpawnTimes = 0;
	private double avgProcessTimes = 0;
	
	
	private double avgQueueLenght = 0;
	private double avgClientWaitTime = 0;
	private double avgTimeInShop = 0;
	private double avgClientStayingInShopTime = 0; 
	private double avgServerUtility =0;
	
	private long seed1 = 0l;
	private long seed2 = 0l;
	
	private int lambdaSpawnTest =0;
	private int lambdaProcess = 0;
	double testExpS =0;
	double testExpP =0;
	
	Simulation testSim1;
	
	
	/**
	 * Test case alle Kunden kommen mit einer höheren mittlern Ankunfsrate
	 * an als sie bedient werden mit festgelegtem random seed 
	 * Hier jeder ankommende kunde wird sofort bedient keiner bleibt in 
	 * der Schlange
	 * 
	 */
	
	@Test
	public void Simaltionstest(){
		
		int lambdaSpawnTest =1000;
		int lambdaProcess = 100;

		seed1 = 2l;
		seed2 = 5l;
		
		
		testSim1 = new Simulation(0,lambdaSpawnTest,lambdaProcess,seed1,seed2);
		
		testExpS = Util.expNumber(lambdaSpawnTest,seed1);
		testExpP = Util.expNumber(lambdaProcess,seed2);
		
		simlationEnd = testExpS + testExpP + testExpS + testExpP + testExpS;
		
		double testValueServerUtility = (testExpP + testExpP)/simlationEnd;
		double testValueTimeInShop = (testExpP + testExpP)/simlationEnd + testValueServerUtility;
		double testMdlQueueLength = 0;
		double testMdlCustomerWaitingTime = testExpP;
		double testMdlCustomerStayInShopTime = testExpP;
		

		testSim1.addListener(this);
		testSim1.start();
		
		
		System.out.println("process with seed "+seed1+" \t" + testExpP + "\n Spawn  with seed "+seed2+" \t" + testExpS);
		while(time <= simlationEnd){
			
			System.out.println("Simulation1 endet in \t" + simlationEnd +"\t"+ "current Time \t"+ time);
		
		}
		System.out.println("Simulation endet \t"+ time);
		testSim1.interrupt();

		System.out.println(testExpS + "\t" + avgSpawnTimes);
		assertTrue(testExpS == avgSpawnTimes);
		assertTrue(testExpP == avgProcessTimes);
		
		
		
		
		
		//TODO in diesem test fall muss die zeit im laden der 
		//verarbeitungsezeit entsprechen
		
		System.out.println(avgTimeInShop);
		
		
		assertTrue(avgQueueLenght ==testMdlQueueLength );
		assertTrue(avgTimeInShop == testValueTimeInShop);
		assertTrue(avgClientWaitTime == testMdlCustomerWaitingTime);
		assertTrue(avgClientStayingInShopTime == testMdlCustomerStayInShopTime);
		assertTrue(avgServerUtility == testValueServerUtility);
		
	}
	
	/**
	 * 
	 * Methode den Fall testet in dem die Ankunftsrate gleich der Bedienrate ist.
	 * 
	 */
	@Test
	public void SimulationsTest(){
		
		
		int lambdaSpawnTest =100;
		int lambdaProcess = 100;
		seed1 = 2l;
		seed2 = 2l;
		testSim1 = new Simulation(0,lambdaSpawnTest,lambdaProcess,seed1,seed2);
		
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

		System.out.println(testExpS + "\t" + avgSpawnTimes);
		
		assertTrue(testExpS == avgSpawnTimes);
		assertTrue(testExpP == avgProcessTimes);
		
		//TODO in diesem test fall muss die zeit im laden der 
		//verarbeitungsezeit entsprechen
		
		System.out.println(avgTimeInShop);
		
		
		assertTrue(avgQueueLenght ==testMdlQueueLength );
		assertTrue(avgTimeInShop == testValueTimeInShop);
		assertTrue(avgClientWaitTime == testMdlCustomerWaitingTime);
		assertTrue(avgClientStayingInShopTime == testMdlCustomerStayInShopTime);
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
		
		
		avgQueueLenght = col.averageQueueSize();
		avgTimeInShop = col.averageClientInShopTime();
		avgClientWaitTime = col.averageClientWaitTime();
		avgClientStayingInShopTime = col.averageClientWaitTime()+col.averageClientProcessTime();
		avgServerUtility = col.averageServerLoad();
		
	}
	
	
	
}