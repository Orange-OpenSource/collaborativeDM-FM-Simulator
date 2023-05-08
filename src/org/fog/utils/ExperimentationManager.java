package org.fog.utils;

import org.cloudbus.cloudsim.distributions.UniformDistr;
import java.util.Arrays;

public class ExperimentationManager {
    public double [] generateFailureDates(int n)
    {
        double [] dates=new double[n];
        UniformDistr distr=new UniformDistr(100, Config.MAX_SIMULATION_TIME, 1234);
        for (int i=0;i<n; i++) { dates[i]=Math.round(distr.sample());}
        Arrays.sort(dates);
        for (int i=0;i<n; i++) { System.out.println(dates[i]);}
        return dates;
    }
}
