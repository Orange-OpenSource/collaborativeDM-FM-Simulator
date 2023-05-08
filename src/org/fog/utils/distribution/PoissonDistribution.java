package org.fog.utils.distribution;

public class PoissonDistribution extends Distribution{
    @Override
    public double getNextValue() {
        return 0;
    }

    @Override
    public int getDistributionType() {
        return 0;
    }

    @Override
    public double getMeanInterTransmitTime() {
        return 0;
    }
}
