package edu.hm.sim.inseldorf.view;

import edu.hm.sim.inseldorf.controller.Simulation;
import edu.hm.sim.inseldorf.model.Event;
import edu.hm.sim.inseldorf.util.DataCollector;
import edu.hm.sim.inseldorf.util.EventListener;
import edu.hm.sim.inseldorf.util.Util;

public class DebugView implements EventListener{
	
	double time= 0;
	Simulation sim;
	double simulationEnd = 0;
	public static boolean ready= false;
	
	
	public DebugView(double simulationTime,Simulation sim){
		this.simulationEnd = simulationTime;
		this.sim = sim;
	}


	public static void main(String[] args) {
		int lambdaS = 1000;
		int lambdaP = 100;
		
		
		long seed1 = 2l;
		long seed2 = 5l;

		
		double testExpS = Util.expNumber(lambdaS, seed1);
		double testExpP = Util.expNumber(lambdaP,seed2);
		
		double testTime = testExpS *3+testExpP*2;
		
		
		
		Simulation sim = new Simulation(1, lambdaS, lambdaP, seed1, seed2);
		EventListener debug = new DebugView(testTime ,sim);
		sim.addListener(debug);
		sim.start();
		
		while(!ready){
			
			
		}
		
		// TODO Auto-generated method stub

	}

	
	
	
	@Override
	public void notify(DataCollector col, Event ev) {
		// TODO Auto-generated method stub
		
		time = col.time();
		
		System.out.println(ev.client);
		
		
//		System.out.println("aktuelle Zeit" + time + "Simulations Ende" + simulationEnd);
//		if(time != simulationEnd){
//			ready = true;
//			sim.interrupt();
//			
//		}
	
	}

}
