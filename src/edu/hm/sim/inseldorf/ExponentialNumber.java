package edu.hm.sim.inseldorf;

import java.util.Random;
/**
 * Die Klasse erzeugt exponentialverteilte Zufallszahl.
 * @author Klas, Kabiolskij, Buecherl, Ronzcka.
 *
 */
public class ExponentialNumber {
	/**
	 * 
	 * @param lambda
	 * @return exponential verteilte Werte.
	 */
	public static double expNumber (int lambda){
		Random random = new Random();
		double a = (random.nextDouble());
		double y = -1/lambda*Math.log(1 - a);
		return lambda*Math.exp(-1*lambda*y);		
	}
}
	
	