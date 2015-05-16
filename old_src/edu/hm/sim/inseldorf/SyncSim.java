package edu.hm.sim.inseldorf;

import java.util.ArrayList;
import java.util.Iterator;

public class SyncSim {
	private int lambdaSpawn;
	private int lambdaProcess;
	private double lastSpawnTime;
	private double q;
	private int qcount;
	private int lastid;
	private double serverbusytill;
	
	private double sumwaittime;
	private ArrayList<SyncClient> clients;
	
	public SyncSim(int lambdaSpawn, int lambdaProcess) {
		this.lambdaProcess = lambdaProcess;
		this.lambdaSpawn = lambdaSpawn;
		lastSpawnTime = 0;
		lastid = 0;
		serverbusytill = -1;
		sumwaittime = 0;
		q = 0;
		qcount = 0;
		clients = new ArrayList<>();
	}
	
	public double nextClient() {
		return ExponentialNumber.expNumber(lambdaSpawn);
	}

	public double processClient() {
		return ExponentialNumber.expNumber(lambdaProcess);
	}
	
	private double cutNegative(double x) {
		return x < 0 ? 0 : x;
	}
	
	public void checkAt(double time, boolean inqueue) {
		int size = clients.size() - 1;
		double lasttime = 0;
		if(size >= 0)
			lasttime = clients.get(size).atSpawn;
			
		Iterator<SyncClient> it = clients.iterator();
		while(it.hasNext()) {
			SyncClient cli = it.next();
			if(cli.atServer < time) {

			} else {
				if(it.hasNext()) {
					if(cli.atServer <= time && cli.atServer > lasttime) {
						lasttime = cli.atServer;
					}
				}
					if(cli.atSpawn <= time && cli.atSpawn > lasttime) {
						lasttime = cli.atSpawn;
					}
			}
		}
		if((time - lasttime) > 0) {
			qcount++;
			q += (time - lasttime) * size;
			System.out.println("(time " + time + " - lasttime " + lasttime+ ") * size " + size);
		}
	}
	
	public void run() {
		while(lastid < 100) {
			SyncClient cli = new SyncClient(lastid++);
			clients.add(cli);
			cli.deltaSpawn = nextClient();
			cli.deltaProcess = processClient();
			lastSpawnTime += cli.deltaSpawn;
			cli.atSpawn = lastSpawnTime;
			cli.deltaQueue = cutNegative(serverbusytill - cli.atSpawn);
			cli.atServer = cli.atSpawn + cli.deltaQueue;
			if(cli.deltaQueue != 0) {
				checkAt(cli.atSpawn, true);
				checkAt(cli.atServer, false);
			}
			cli.atFinish = cli.atServer + cli.deltaProcess;
			serverbusytill = cli.atFinish;
			sumwaittime += cli.deltaQueue;
			
			System.out.println(cli);
			System.out.println("d = " + (sumwaittime/lastid) + " 0 = " + (lambdaSpawn*(sumwaittime/(lastid-1)) - q/qcount));
		}
	}
	
	public static void main(String...args) {
		SyncSim s = new SyncSim(1000,100);
		s.run();
	}
}
