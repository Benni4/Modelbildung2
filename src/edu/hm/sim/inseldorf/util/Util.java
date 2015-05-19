package edu.hm.sim.inseldorf.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import edu.hm.sim.inseldorf.util.DataCollector.DataCollectorEntry;
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
	public static void write (String s, ArrayList<DataCollectorEntry> list) throws IOException{
		BufferedWriter outputWriter = null;
		outputWriter = new BufferedWriter(new FileWriter(s));
		outputWriter.write("{0,0}");
		for (int i = 1; i < list.size(); i++) {
		    outputWriter.write(list.get(i).toString());
		}
		outputWriter.flush();  
		outputWriter.close();  
	}
	
	public static double cutNegative(double x) {
		return x > 0 ? x : 0;
	}
}	
	