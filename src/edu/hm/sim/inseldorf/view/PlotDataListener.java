package edu.hm.sim.inseldorf.view;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import edu.hm.sim.inseldorf.model.Event;
import edu.hm.sim.inseldorf.util.DataCollector;
import edu.hm.sim.inseldorf.util.EventListener;

public class PlotDataListener implements EventListener {
	
	private XYSeries queueLenghtToTime;
	
	private XYSeries waitingTimeToTime;
	
	
	PlotDataListener(){
		
		queueLenghtToTime = new XYSeries("Schlangen länge zur Zeit");
		waitingTimeToTime = new XYSeries("Durchschnittliche Wartezeiten zur Zeit");
		
		
		
		
		
	}

	public XYSeriesCollection getQueueLenghtToTime() {
		
		 XYSeriesCollection plotData = new XYSeriesCollection();
		plotData.removeAllSeries();
		plotData.addSeries(queueLenghtToTime);
		
		
		return plotData;
	}
	public XYSeriesCollection getWaitingTimeToTime() {
		 XYSeriesCollection plotData = new XYSeriesCollection();
		plotData.removeAllSeries();
		plotData.addSeries(waitingTimeToTime);
		return plotData;
	}



	@Override
	public void notify(DataCollector col, Event ev) {
		
		double time  = col.time();
		queueLenghtToTime.add(time,col.averageQueueSize());
		waitingTimeToTime.add(time,col.averageClientWaitTime());
		
	

	}

}
