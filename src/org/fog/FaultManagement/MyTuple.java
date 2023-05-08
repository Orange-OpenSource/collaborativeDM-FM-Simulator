package org.fog.FaultManagement;

import org.cloudbus.cloudsim.UtilizationModel;
import org.fog.entities.Tuple;

public class MyTuple extends Tuple {
    private double data;
    public MyTuple(String appId, int cloudletId, int direction, long cloudletLength, int pesNumber, long cloudletFileSize, long cloudletOutputSize, UtilizationModel utilizationModelCpu, UtilizationModel utilizationModelRam, UtilizationModel utilizationModelBw, double data) {
        super(appId, cloudletId, direction, cloudletLength, pesNumber, cloudletFileSize, cloudletOutputSize, utilizationModelCpu, utilizationModelRam, utilizationModelBw);
        this.data=data;
    }
   @Override
    public double getData() {
        return data;
    }
}
