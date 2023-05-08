package org.fog.FaultManagement;

import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;
import org.fog.entities.Sensor;

public class FaultRecovery extends SimEntity {
    /**
     * Creates a new entity.
     *
     * @param name the name to be associated with this entity
     */
    private int entityId;
    private int date;
    public FaultRecovery(String name, int date, int sensorid) {
        super(name);
        this.date=date;
        this.entityId=sensorid;
    }

    @Override
    public void startEntity() {
        MySensor sensor=(MySensor)CloudSim.getEntity(entityId);
         send(entityId, this.date, FailureType.RECOVER);

    }

    @Override
    public void processEvent(SimEvent ev) {

    }

    @Override
    public void shutdownEntity() {

    }
}
