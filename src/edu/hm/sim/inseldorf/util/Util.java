package edu.hm.sim.inseldorf.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
/**
 * Die Klasse erzeugt exponentialverteilte Zufallszahl.
 * @author Klas, Kabiolskij, Buecherl, Ronzcka.
 *
 */
public class Util {
	/**
	 * 
	 * @param lambda
	 * @return exponential verteilte Werte.
	 */
	public static double expNumber (double lambda){
		Random random = new Random();
		double a = (random.nextDouble());
		double y = (-lambda)*Math.log(1 - a);	
		return y;
	}
	
	
	public static double expNumber(double lambda,long seed){
		Random random = new Random(seed);
		double a = (random.nextDouble());
		double y = (-lambda)*Math.log(1 - a);	
		return y;
		
	}

	/**
	 * Methode um die Funktion mittels Mathematica zu testen.
	 * @param s = filename.
	 * @param data, array aus dem die daten gelesen werden.
	 * @throws IOException.
	 */
	public static void write (String s, double[] data) throws IOException{
		BufferedWriter outputWriter = null;
		outputWriter = new BufferedWriter(new FileWriter(s));
		for (int i = 0; i < data.length; i++) {
		    outputWriter.write(Double.toString(data[i])+",");
		}
		outputWriter.flush();  
		outputWriter.close();  
	}
	
	public static double cutNegative(double x) {
		return x > 0 ? x : 0;
	}
}	
	