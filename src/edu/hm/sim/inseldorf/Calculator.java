/**
 * 
 */
package edu.hm.sim.inseldorf;

/**
 * @author Klas, Kabiolskij, Buecherl, Ronzcka.
 *
 */
public class Calculator {
	
	/**
	 * @return Mittlere Länge der Warteschlangen seit Simulationsstart.
	 */
	public static double averageQueueSize(){
		return DataCollector.sumOfPersonsInQueue/DataCollector.personCounter;
	}
	
	/**
	 * @return Mittlere Serverauslastung in %.
	 */
	public static double averageServerLoad(){
		double time = System.currentTimeMillis()-Server.simulationStartTime;
		return Server.workTime/time*100;
	}
	
	/**
	 * @return Mittlere Anstehzeit in der Warteschlange in s.
	 */
	public static double averageClientWaitTime(){
		return Server.sumClientWaitTime/Server.clientCounter;
	}
	/**
	 * @return Mittlere Bedienzeit der Clients seit Simulationsstart in s.
	 */
	public static double averageClientProcessTime(){
		return Server.sumClientProcessTime/Server.finishedClientCounter;
	}
	/**
	 * @return Mittlere Anzahl der Kunden im Shop
	 */
	public static double averageNumberOfClients(){
		//ToDo
		System.out.println("Hier könnte ihre Werbung stehen");
		return 0;
	}
}
