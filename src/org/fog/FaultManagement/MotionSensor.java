package org.fog.FaultManagement;

import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEvent;
import org.cloudbus.cloudsim.distributions.UniformDistr;
import org.fog.application.AppEdge;
import org.fog.entities.Sensor;
import org.fog.entities.Tuple;
import org.fog.utils.FogEvents;
import org.fog.utils.FogUtils;
import org.fog.utils.GeoLocation;
import org.fog.utils.Logger;
import org.fog.utils.distribution.Distribution;
import org.fog.utils.distribution.HighVariance;


public class MotionSensor extends MySensor {

    private boolean state;
    private HighVariance hv;
    public MotionSensor(String name, int userId, String appId, int gatewayDeviceId, double latency, GeoLocation geoLocation, Distribution transmitDistribution, int cpuLength, int nwLength, String tupleType, String destModuleName) {
        super(name, userId, appId, gatewayDeviceId, latency, geoLocation, transmitDistribution, cpuLength, nwLength, tupleType, destModuleName);
    }
    public MotionSensor(String name, String tupleType, int userId, String appId, Distribution transmitDistribution) {
        super(name, tupleType, userId, appId, transmitDistribution);
        state=false;
        hv=new HighVariance();
    }
    @Override
    public void startEntity() {
        send(super.getGatewayDeviceId(), CloudSim.getMinTimeBetweenEvents(), FogEvents.SENSOR_JOINED, super.getGeoLocation());
        send(getId(), getTransmitDistribution().getNextValue() + super.getTransmissionStartDelay(), FogEvents.EMIT_TUPLE);
        //Logger.debug(getName(),"Now we inject a failure");
        //send(getId(), getTransmitDistribution().getNextValue() + super.getTransmissionStartDelay(), FogEvents.FAILURE);
    }
    @Override
    public void processEvent(SimEvent ev)  {
        //For leak sensor delay=43200000
        //For smoke sensor delay=28800000
        //For motion sensor random from 3-10 minutes
        UniformDistr dist=new UniformDistr(120000,600000,1234);
        switch(ev.getTag()){
            case FogEvents.TUPLE_ACK:
                 //transmit(transmitDistribution.getNextValue());
                break;
            case FogEvents.EMIT_TUPLE:
                if(state == false)
                {   transmit();
                    //Math.round(dist.sample()) for motion sensor
                    send(getId(),  Math.round(dist.sample()), FogEvents.EMIT_TUPLE); //delay is the frequency of readings
                }
                break;
            case FailureType.OUTLIER:
                   sendNow(getGatewayDeviceId(), FailureType.OUTLIER);
                   Logger.debug(getName(), "Outlier");
                break;
            case FailureType.HIGHVARIANCE:
                if(state==true)
                {   Logger.debug(getName(), "HighVariance");
                    if(hv.getNextValue()==1) {
                        transmitFailure(1);
                    }
                    send(getId(), 100, FailureType.HIGHVARIANCE);

                }
                break;
            case FailureType.STUCKAT:
                if(state==true)
                {   Logger.debug(getName(), "StuckAt");
                    transmitFailure(1);
                    send(getId(), 100, FailureType.STUCKAT); // 10 is emission time.
                }
                break;
            case FailureType.RECOVER:
                 state=false;
                 sendNow(getId(), FogEvents.EMIT_TUPLE);
                break;
            case FailureType.STOP:
                 state=true;
                break;
        }
    }
    public void transmitFailure(double data)
    {
        AppEdge _edge = null;
        for(AppEdge edge : getApp().getEdges()){
            if(edge.getSource().equals(getTupleType()))
                _edge = edge;
        }
        long cpuLength = (long) _edge.getTupleCpuLength();
        long nwLength = (long) _edge.getTupleNwLength();
        MyTuple tuple = new MyTuple(getAppId(), FogUtils.generateTupleId(), Tuple.UP, cpuLength, 1, nwLength, getOutputSize(),
                new UtilizationModelFull(), new UtilizationModelFull(), new UtilizationModelFull(),data);
        tuple.setUserId(getUserId());
        tuple.setTupleType(getTupleType());
        tuple.setDestModuleName(_edge.getDestination());
        tuple.setSrcModuleName(getSensorName());
        Logger.debug(getName(), " Sending failed tuple with tupleId = "+tuple.getCloudletId());
        tuple.setDestinationDeviceId(getGatewayDeviceId());
        int actualTupleId = updateTimings(getSensorName(), tuple.getDestModuleName());
        tuple.setActualTupleId(actualTupleId);
        send(getGatewayDeviceId(),2, FogEvents.TUPLE_ARRIVAL,tuple);
    }
    public void setState(boolean state) {
        this.state = state;
    }

}
