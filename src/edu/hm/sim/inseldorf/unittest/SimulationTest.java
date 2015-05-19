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
	private double simlationEnd = 1;
	
	private double avgSpawnTimes = 0;
	private double avgProcessTimes = 0;
	private double avgQueueLenght = 0;
	private double avgTimeInShop = 0;
	private double avgServerUtility =0;
	
	
	
	private long seed1 =2l;
	private long seed2 =5l;
	
	int doSomeThing = 0;
	
	private int lambdaSpawnTest =1000;
	private int lambdaProcess = 100;
	double testExpS = Util.expNumber(lambdaSpawnTest,seed1);
	double testExpP = Util.expNumber(lambdaProcess,seed2);
	
	Simulation testSim1 = new Simulation(0,lambdaSpawnTest,lambdaProcess,seed1,seed2);
	
	
	/**
	 * Test case alle Kunden kommen mit einer höheren mittlern Ankunfsrate
	 * an als sie bedient werden mit festgelegtem random seed 
	 * Hier jeder ankommende kunde wird sofort bedient keiner bleibt in 
	 * der Schlange
	 * 
	 */
	
	@Test
	public void Simaltionstest(){
		

		
		testSim1.addListener(this);
		testSim1.start();
		
		System.out.println("process with seed 2l \t" + testExpP + "\n Spawn  with seed 5l \t" + testExpS);
		while(time < simlationEnd){
			
			System.out.println("Simulation endet in \t" + simlationEnd +"\t"+ "current Time \t"+ time);
		
			
			
		}
		System.out.println("Simulation endet \t"+ time);
		testSim1.interrupt();
		
		
		
		assertEquals((int)testExpS,(int)avgSpawnTimes);
		assertEquals((int)testExpP,(int)avgProcessTimes);
		assertEquals(0,(int)avgQueueLenght);
		
		//TODO in diesem test fall muss die zeit im laden der 
		//verarbeitungsezeit entsprechen
		System.out.println(avgTimeInShop);
		assertTrue(avgTimeInShop < testExpP);
		assertTrue(avgServerUtility<10);
		
		
		
	}
	

	@Override
	public void notify(DataCollector col, Event ev) {
		
		time = col.time()/3600;
		avgProcessTimes = col.averageClientProcessTime();
		avgSpawnTimes = col.averageClientSpawnTime();
		avgQueueLenght = col.averageQueueSize();
		avgTimeInShop = col.averageClientInShopTime();
		avgServerUtility = col.averageServerLoad();
		
		
		
	
		
	}
	
	
	
}