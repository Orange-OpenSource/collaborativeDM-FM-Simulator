package org.fog.test.perfeval;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.cloudbus.cloudsim.sdn.overbooking.BwProvisionerOverbooking;
import org.cloudbus.cloudsim.sdn.overbooking.PeProvisionerOverbooking;
import org.fog.FaultManagement.*;
import org.fog.application.AppEdge;
import org.fog.application.AppLoop;
import org.fog.application.Application;
import org.fog.application.selectivity.FractionalSelectivity;
import org.fog.entities.*;
import org.fog.placement.ModuleMapping;
import org.fog.placement.ModulePlacementEdgewards;
import org.fog.policy.AppModuleAllocationPolicy;
import org.fog.scheduler.StreamOperatorScheduler;
import org.fog.utils.ExperimentationManager;
import org.fog.utils.FogLinearPowerModel;
import org.fog.utils.FogUtils;
import org.fog.utils.TimeKeeper;
import org.fog.utils.distribution.DeterministicDistribution;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 *
 *
 * @author Harshit Gupta
 *
 */
public class LightManagementApp {

    static List<FogDevice> fogDevices = new ArrayList<FogDevice>();
    static List<Sensor> sensors = new ArrayList<Sensor>();
    static List<Actuator> actuators = new ArrayList<Actuator>();
    static  List<FaultInjector>failures=new ArrayList<FaultInjector>();
    static List<FaultRecovery> recoveryActions=new ArrayList<FaultRecovery>();

    public static void main(String[] args) {

        Log.printLine("Starting ...");
        try {
            Log.disable();
            int num_user = 1; // number of cloud users
            Calendar calendar = Calendar.getInstance();
            boolean trace_flag = true; // mean trace events
            CloudSim.init(num_user, calendar, trace_flag);
            String appId = "LightManagement"; // identifier of the application
            FogBroker broker = new FogBroker("broker");
            Application application = createApplication(appId, broker.getId());
            application.setUserId(broker.getId());
            createFogDevices(broker.getId(), appId);
            MyController controller = null;
            /*inject failure*/
           /* FaultInjector fault=new FaultInjector("HighVariance",60, sensors.get(0).getId(), FailureType.HIGHVARIANCE);
            failures.add(fault);
            FaultRecovery recovery= new FaultRecovery("recovery",100, sensors.get(0).getId());
            recoveryActions.add(recovery);
            fault=new FaultInjector("stuckAt",180,sensors.get(0).getId(),FailureType.STUCKAT);
            failures.add(fault);
            recovery= new FaultRecovery("recovery",220, sensors.get(0).getId());
            recoveryActions.add(recovery);
            fault=new FaultInjector("stuckAt",300,sensors.get(0).getId(),FailureType.STUCKAT);
            failures.add(fault);
            recovery= new FaultRecovery("recovery",340, sensors.get(0).getId());
            recoveryActions.add(recovery);*/

            /**Performance Measures**/
          /* FaultInjector fault=new FaultInjector("HighVariance",600500, sensors.get(0).getId(), FailureType.HIGHVARIANCE);
            failures.add(fault);
            FaultRecovery recovery= new FaultRecovery("recovery",601000, sensors.get(0).getId());
            recoveryActions.add(recovery);
            fault=new FaultInjector("stuckAt",601780,sensors.get(0).getId(),FailureType.STUCKAT);
            failures.add(fault);
            recovery= new FaultRecovery("recovery",601900, sensors.get(0).getId());
            recoveryActions.add(recovery);
            fault=new FaultInjector("stuckAt",602200,sensors.get(0).getId(),FailureType.STUCKAT);
            failures.add(fault);
            recovery= new FaultRecovery("recovery",603300, sensors.get(0).getId());
            recoveryActions.add(recovery);
            fault=new FaultInjector("HighVariance",610500,sensors.get(0).getId(),FailureType.HIGHVARIANCE);
            failures.add(fault);
            recovery= new FaultRecovery("recovery",611000, sensors.get(0).getId());
            recoveryActions.add(recovery);
            fault=new FaultInjector("stuckAt",611780,sensors.get(0).getId(),FailureType.STUCKAT);
            failures.add(fault);
            recovery= new FaultRecovery("recovery",611900, sensors.get(0).getId());
            recoveryActions.add(recovery);
            fault=new FaultInjector("stuckAt",612200,sensors.get(0).getId(),FailureType.STUCKAT);
            failures.add(fault);
            recovery= new FaultRecovery("recovery",613300, sensors.get(0).getId());
            recoveryActions.add(recovery);*/
            /**test protocol exp**/
            ExperimentationManager exp=new ExperimentationManager();
            //human intervention=10800000
            double []dates= exp.generateFailureDates(7); //generate failures according to the uniform distribution.
            for(int i=0;i<3;i++) {
                FaultInjector fault = new FaultInjector("HighVariance", (int)dates[i], sensors.get(0).getId(), FailureType.HIGHVARIANCE);
                failures.add(fault);
                FaultRecovery recovery = new FaultRecovery("recovery", (int) dates[i] + 621 , sensors.get(0).getId());
                recoveryActions.add(recovery);
            }
            for(int i=3;i<7;i++) {
                FaultInjector fault = new FaultInjector("StuckAt",(int)dates[i], sensors.get(0).getId(), FailureType.STUCKAT);
                failures.add(fault);
                FaultRecovery recovery = new FaultRecovery("recovery", (int)dates[i] + 621, sensors.get(0).getId());
                recoveryActions.add(recovery);
            }
             /******/
             ModuleMapping moduleMapping = ModuleMapping.createModuleMapping(); // initializing a module mapping
             moduleMapping.addModuleToDevice("light_management", "cloud");  // fixing 1 instance of the Motion Detector module to each Smart Camera
             controller = new MyController("master-controller", fogDevices, sensors, actuators,failures,recoveryActions);
             controller.submitApplication(application, new ModulePlacementEdgewards(fogDevices, sensors, actuators, application, moduleMapping));
             TimeKeeper.getInstance().setSimulationStartTime(Calendar.getInstance().getTimeInMillis());
             CloudSim.startSimulation();
             CloudSim.stopSimulation();
            }
        catch (Exception e) {
            e.printStackTrace();
            Log.printLine("Unwanted errors happen");
        }
    }

    /**
     * Creates the fog devices in the physical topology of the simulation.
     * @param userId
     * @param appId
     */

    private static void createFogDevices(int userId, String appId) {
        FogDevice cloud = createFogDevice("cloud", 44800, 40000, 100, 10000, 0, 0.01, 16*103, 16*83.25);
        cloud.setParentId(-1);
        fogDevices.add(cloud);
        FogDevice hub = createFogDevice("smartThingsHub", 500, 1000, 10000, 10000, 3, 0, 87.53, 82.44);
        hub.setParentId(cloud.getId());
        hub.setUplinkLatency(100);
        fogDevices.add(hub);
        Sensor sensor = new MySensor("MotionSensor", "MOTION", userId, appId, new DeterministicDistribution(5)); // inter-transmission time of camera (sensor) follows a deterministic distribution
        sensors.add(sensor);
        Actuator actuator = new MyActuator("LightBulb", userId, appId, "LIGHT_CONTROL");
        actuators.add(actuator);
        sensor.setGatewayDeviceId(hub.getId());
        sensor.setLatency(1.0);  // latency of connection between camera (sensor) and the parent Smart Camera is 1 ms
        actuator.setGatewayDeviceId(hub.getId());
        actuator.setLatency(1.0);  // latency of connection between PTZ Control and the parent Smart Camera is 1 ms
    }

    /**
     * Creates a vanilla fog device
     * @param nodeName name of the device to be used in simulation
     * @param mips MIPS
     * @param ram RAM
     * @param upBw uplink bandwidth
     * @param downBw downlink bandwidth
     * @param level hierarchy level of the device
     * @param ratePerMips cost rate per MIPS used
     * @param busyPower
     * @param idlePower
     * @return
     */
    private static FogDevice createFogDevice(String nodeName, long mips,
                                             int ram, long upBw, long downBw, int level, double ratePerMips, double busyPower, double idlePower) {

        List<Pe> peList = new ArrayList<Pe>();

        // 3. Create PEs and add these into a list.
        peList.add(new Pe(0, new PeProvisionerOverbooking(mips))); // need to store Pe id and MIPS Rating

        int hostId = FogUtils.generateEntityId();
        long storage = 1000000; // host storage
        int bw = 10000;

        PowerHost host = new PowerHost(
                hostId,
                new RamProvisionerSimple(ram),
                new BwProvisionerOverbooking(bw),
                storage,
                peList,
                new StreamOperatorScheduler(peList),
                new FogLinearPowerModel(busyPower, idlePower)
        );

        List<Host> hostList = new ArrayList<Host>();
        hostList.add(host);

        String arch = "x86"; // system architecture
        String os = "Linux"; // operating system
        String vmm = "Xen";
        double time_zone = 10.0; // time zone this resource located
        double cost = 3.0; // the cost of using processing in this resource
        double costPerMem = 0.05; // the cost of using memory in this resource
        double costPerStorage = 0.001; // the cost of using storage in this
        // resource
        double costPerBw = 0.0; // the cost of using bw in this resource
        LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are not adding SAN
        // devices by now

        FogDeviceCharacteristics characteristics = new FogDeviceCharacteristics(
                arch, os, vmm, host, time_zone, cost, costPerMem,
                costPerStorage, costPerBw);

        FogDevice fogdevice = null;
        try {
            fogdevice = new MyFogDevice(nodeName, characteristics,
                    new AppModuleAllocationPolicy(hostList), storageList, 10, upBw, downBw, 0, ratePerMips);
        } catch (Exception e) {
            e.printStackTrace();
        }

        fogdevice.setLevel(level);
        return fogdevice;
    }

    /**
     * Function to create the Intelligent Surveillance application in the DDF model.
     * @param appId unique identifier of the application
     * @param userId identifier of the user of the application
     * @return
     */
    @SuppressWarnings({"serial" })
    private static Application createApplication(String appId, int userId){

        Application application = Application.createApplication(appId, userId);
        /*
         * Adding modules (vertices) to the application model (directed graph)
         */
         application.addAppModule("light_management", 10);

        /*
         * Connecting the application modules (vertices) in the application model (directed graph) with edges
         */
        application.addAppEdge("MOTION", "light_management", 1000, 2000, "MOTION", Tuple.UP, AppEdge.SENSOR); // adding edge from CAMERA (sensor) to Motion Detector module carrying tuples of type CAMERA
        application.addAppEdge("light_management", "LIGHT_CONTROL", 100, 100, "LIGHT_PARAMS", Tuple.DOWN, AppEdge.ACTUATOR); // adding edge from Object Tracker to PTZ CONTROL (actuator) carrying tuples of type PTZ_PARAMS

        /*
         * Defining the input-output relationships (represented by selectivity) of the application modules.
         */
        application.addTupleMapping("light_management", "MOTION", "LIGHT_PARAMS", new FractionalSelectivity(1.0)); // 1.0 tuples of type MOTION_VIDEO_STREAM are emitted by Motion Detector module per incoming tuple of type CAMERA

        /*
         * Defining application loops (maybe incomplete loops) to monitor the latency of.
         * Here, we add two loops for monitoring : Motion Detector -> Object Detector -> Object Tracker and Object Tracker -> PTZ Control
         */
      final AppLoop loop1 = new AppLoop(new ArrayList<String>(){{add("MOTION");add("light_management"); add("LIGHT_CONTROL");}});
         List<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};

        application.setLoops(loops);
        return application;
    }

}