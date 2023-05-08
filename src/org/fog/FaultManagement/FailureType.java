package org.fog.FaultManagement;

public class FailureType {
    private static final int BASE = 100;
    public static final int OUTLIER = BASE + 1;
    public static final int SPIKE = BASE + 2;
    public static final int HIGHVARIANCE = BASE + 3;
    public static final int STUCKAT = BASE + 4;
    public static final int CALIBRATION  = BASE + 5;
    public static final int FAILSTOP  = BASE + 6;
    public static final int RECOVER  = BASE + 7;
    public static final int STOP  = BASE + 8;


}
