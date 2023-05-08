package org.fog.test.perfeval;

import org.cloudbus.cloudsim.distributions.UniformDistr;
import org.fog.utils.ExperimentationManager;

public class TestFaultDistribution {
    public static void main(String[] args) {
        ExperimentationManager exp=new ExperimentationManager();
        exp.generateFailureDates(7);
    }
}
