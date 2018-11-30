package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;

public class ColorSensor {

    HardwareMap hwMap = null;
    public ModernRoboticsI2cColorSensor frontColorSensor = null;

    public ColorSensor(){

    }

    public void init(HardwareMap hwMap){
        frontColorSensor = hwMap.get(ModernRoboticsI2cColorSensor.class, "sensor_color");
    }

}