package org.fog.utils.distribution;

public class HighVariance extends Distribution{
    private double value;
    public HighVariance(){
        super();
    }
    @Override
    public double getNextValue()
    {
        if (value==0) {value=1;return 1;}
        else {if(value==1)
        { value=0; return 0;}}
        return 0;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int getDistributionType() {
        return Distribution.HVariance;
    }

    @Override
    public double getMeanInterTransmitTime() {
        return 4;
    }

}
