package org.fog.FaultManagement;

import org.cloudbus.cloudsim.core.SimEvent;
import org.fog.application.Application;
import org.fog.entities.Actuator;
import org.fog.utils.FogEvents;
import org.fog.utils.GeoLocation;
import org.fog.utils.Logger;


public class MyActuator extends Actuator {




	public MyActuator(String name, int userId, String appId, String actuatorType) {
		super(name, userId,appId,actuatorType);
	}

	@Override
	public void startEntity() {
		sendNow(super.getGatewayDeviceId(), FogEvents.ACTUATOR_JOINED, getLatency());
	}

	@Override
	public void processEvent(SimEvent ev) {
		switch(ev.getTag()){
		case FogEvents.TUPLE_ARRIVAL:
			super.processTupleArrival(ev);
			break;
			case FailureType.OUTLIER:
				Logger.debug(getName(),"OUTLIER");
				break;
			case FailureType.HIGHVARIANCE:
				Logger.debug(getName(),"HighVariance");
				break;
		}		
	}



}
