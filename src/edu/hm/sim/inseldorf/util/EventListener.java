package edu.hm.sim.inseldorf.util;

import edu.hm.sim.inseldorf.model.Event;

public interface EventListener {
	public void notify(DataCollector col, Event ev);
}
