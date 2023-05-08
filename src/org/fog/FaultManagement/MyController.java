package org.fog.FaultManagement;

import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.core.SimEntity;
import org.cloudbus.cloudsim.core.SimEvent;
import org.fog.application.AppEdge;
import org.fog.application.AppLoop;
import org.fog.application.AppModule;
import org.fog.application.Application;
import org.fog.entities.Actuator;
import org.fog.FaultManagement.FaultInjector;
import org.fog.entities.FogDevice;
import org.fog.entities.Sensor;
import org.fog.placement.Controller;
import org.fog.placement.ModulePlacement;
import org.fog.utils.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyController  extends Controller {

        public static boolean ONLY_CLOUD = false;

        private List<FogDevice> fogDevices;
        private List<Sensor> sensors;
        private List<Actuator> actuators;
        private List<FaultInjector> failures;
        private List<FaultRecovery> recoveryActions;

        private Map<String, Application> applications;
        private Map<String, Integer> appLaunchDelays;
        private Map<String, ModulePlacement> appModulePlacementPolicy;

        public MyController(String name, List<FogDevice> fogDevices, List<Sensor> sensors, List<Actuator> actuators, List<FaultInjector> fault, List<FaultRecovery> recovery) {
            super(name,fogDevices,sensors,actuators);
            this.recoveryActions=recovery;
            this.failures=fault;

        }


}
