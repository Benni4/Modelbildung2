package edu.hm.sim.inseldorf;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Testklasse
 * 
 * @author Klas, Kabiolskij, Buecherl, Ronzcka.
 *
 */
public class ExponentialNumbertest {
	public static void main(String[] args) throws IOException {

		final int size = 1000;
		double[] data1 = new double[size];
		double[] data2 = new double[size];
		double[] data3 = new double[size];

		int lambda1 = 100;
		int lambda2 = 1000;
		int lambda3 = 20;
		for (int i = 0; i < size; i++) {
			data1[i] = ExponentialNumber.expNumber(lambda1);
			data2[i] = ExponentialNumber.expNumber(lambda2);
			data3[i] = ExponentialNumber.expNumber(lambda3);
		}

		String name1 = "data1.txt";
		String name2 = "data2.txt";
		String name3 = "data3.txt";
		ExponentialNumber.write(name1, data1);
		ExponentialNumber.write(name2, data2);
		ExponentialNumber.write(name3, data3);
	}

}
