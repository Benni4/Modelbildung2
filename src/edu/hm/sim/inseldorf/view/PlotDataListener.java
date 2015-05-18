package edu.hm.sim.inseldorf.view;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.hm.sim.inseldorf.model.Event;
import edu.hm.sim.inseldorf.util.DataCollector;
import edu.hm.sim.inseldorf.util.EventListener;

public class PlotDataListener implements EventListener {

	private XYSeries mdlQueueLenghtToTime;
	private XYSeries mdlAmountOfCountersToTime;
	private XYSeries mdlWaitingTimeToTime;
	private XYSeries mdlTimeInShopToTime;
	private XYSeries mdlServerUtilizationToTime;
	private double currentServerUtilization = 0;
	private int currentNbrOfClientsInQueue = 0;

	PlotDataListener() {

		mdlQueueLenghtToTime = new XYSeries("Mittlere Schlangenlänge zur Zeit");
		mdlAmountOfCountersToTime = new XYSeries(
				"Mittlere Anzahl von Kunden zur Zeit");
		mdlWaitingTimeToTime = new XYSeries("Mittlere Wartezeiten zur Zeit");
		mdlTimeInShopToTime = new XYSeries(
				"Mittlere Verweildauer im Laden zur Zeit");
		mdlServerUtilizationToTime = new XYSeries(
				"Mittlere Serverauslastung zur Zeit");
	}

	public double getCurrentServerUtilization() {
		return currentServerUtilization;
	}

	public XYSeriesCollection getMdlTimeInShopToTime() {
		XYSeriesCollection plotData = new XYSeriesCollection();
		plotData.addSeries(mdlTimeInShopToTime);
		return plotData;
	}

	public XYSeriesCollection getMdlServerUtilizationToTime() {

		XYSeriesCollection plotData = new XYSeriesCollection();
		plotData.addSeries(mdlServerUtilizationToTime);
		return plotData;

	}

	public XYSeriesCollection getMdlAmountOfCountersToTime() {

		XYSeriesCollection plotData = new XYSeriesCollection();
		plotData.addSeries(mdlAmountOfCountersToTime);
		return plotData;
	}

	public XYSeriesCollection getQueueLenghtToTime() {

		XYSeriesCollection plotData = new XYSeriesCollection();
		plotData.addSeries(mdlQueueLenghtToTime);
		return plotData;
	}

	public XYSeriesCollection getWaitingTimeToTime() {

		XYSeriesCollection plotData = new XYSeriesCollection();
		plotData.addSeries(mdlWaitingTimeToTime);
		return plotData;
	}

	@Override
	public void notify(DataCollector col, Event ev) {
		//Hier noch mal!!!
		currentServerUtilization = col.getcServerSize();
		currentNbrOfClientsInQueue = col.getCQueueSize();

		double time = col.time();
		mdlQueueLenghtToTime.add(time, col.averageQueueSize());
		mdlAmountOfCountersToTime.add(time, col.averageNumberOfClients());
		mdlWaitingTimeToTime.add(time, col.averageClientWaitTime());
		mdlTimeInShopToTime.add(time, col.averageClientInShopTime());
		mdlServerUtilizationToTime.add(time, col.averageServerLoad()*100);

	}

	public int getCurrentNbrOfClientsInQueue() {
		return currentNbrOfClientsInQueue;
	}

}
