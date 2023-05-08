package org.fog.FaultManagement;

import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;
import org.fog.utils.FogEvents;
import org.fog.utils.TimeKeeper;
import org.fog.utils.distribution.Distribution;

public class FaultInjector extends SimEntity {
    /**
     * Creates a new entity.
     *
     * @param name the name to be associated with this entity
     */
    private int entityId;
    private int date;

    private int failureType;
    public FaultInjector(String name, int date, int sensorid, int failureType) {
        super(name);
        this.date=date;
        this.entityId=sensorid;
        this.failureType=failureType;
    }


    @Override
    public void startEntity()
    {
        MySensor sensor=(MySensor) CloudSim.getEntity(entityId);
        send(entityId,this.date, FailureType.STOP);
        send(entityId, this.date, this.failureType);
    }

    @Override
    public void processEvent(SimEvent ev)
    {

    }

    @Override
    public void shutdownEntity()
    {

    }
}
