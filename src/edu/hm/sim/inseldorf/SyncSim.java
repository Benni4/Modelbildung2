package edu.hm.sim.inseldorf;


public class SyncSim {
	private int lambdaSpawn;
	private int lambdaProcess;
	private double lastSpawnTime;
	private int queuesize;
	private double q;
	private int lastid;
	private double serverbusytill;
	
	private double sumwaittime;
	
	public SyncSim(int lambdaSpawn, int lambdaProcess) {
		this.lambdaProcess = lambdaProcess;
		this.lambdaSpawn = lambdaSpawn;
		lastSpawnTime = 0;
		queuesize = 0;
		lastid = 0;
		serverbusytill = -1;
		sumwaittime = 0;
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
	
	public void run() {
		while(true) {
			SyncClient cli = new SyncClient(lastid++);
			cli.deltaSpawn = nextClient();
			cli.deltaProcess = processClient();
			lastSpawnTime += cli.deltaSpawn;
			cli.atSpawn = lastSpawnTime;
			queuesize++;
			cli.deltaQueue = cutNegative(serverbusytill - cli.atSpawn);
			cli.atServer = cli.atSpawn + cli.deltaQueue;
			cli.atFinish = cli.atServer + cli.deltaProcess;
			serverbusytill = cli.atFinish;
			
			sumwaittime += cli.deltaQueue;
			
			System.out.println(cli);
			System.out.println("d = " + (sumwaittime/lastid) + " Q = " + lambdaSpawn*(sumwaittime/lastid));
		}
	}
	
	public static void main(String...args) {
		SyncSim s = new SyncSim(1000,100);
		s.run();
	}
}
