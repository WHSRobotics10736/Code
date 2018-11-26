package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.I2cAddr;

@Autonomous(name = "Autonomous_1", group = "Autonomous")
@Disabled
public class Autonomous_1 extends LinearOpMode {

    ColorSensor robot = new ColorSensor() {};

    @Override
    public void runOpMode(){

        robot.init(hardwareMap);

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        float hsvValues[] = {0F,0F,0F};

        final float values[] = hsvValues;

        waitForStart();

        while (opModeIsActive()){

            robot.frontColorSensor.enableLed(true);

            Color.RGBToHSV(robot.frontColorSensor.red() * 8, robot.frontColorSensor.green() * 8, robot.frontColorSensor.blue() * 8, hsvValues);

            /**
             * Gold Block == Color Numbers 7, 8, 9, and 10;
             */
            telemetry.addData("Color Number", robot.frontColorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER));
            telemetry.update();

            relativeLayout.post(new Runnable() {
                public void run() {
                    relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
                }
            });
        }

        robot.frontColorSensor.enableLed(false);

    }

}
