package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Autonomous_Left", group = "Autonomous")
//@Disabled
public class Autonomous_2 extends LinearOpMode {

    double speed = 1;

    ColorSensor robot = new ColorSensor() {};
    Servo redServo;
    double getRedServo;

    float hsvValues[] = {0F,0F,0F};

    final float values[] = hsvValues;

    int colorNumber = 0;
    int found = 0;

    /**
     * Motor
     */

    DcMotor mfr;
    DcMotor mfl;
    DcMotor mbl;
    DcMotor mbr;

    @Override
    public void runOpMode(){

        /**
         * Motor
         */

        mfr = hardwareMap.dcMotor.get("mfr");
        mfl = hardwareMap.dcMotor.get("mfl");
        mbl = hardwareMap.dcMotor.get("mbl");
        mbr = hardwareMap.dcMotor.get("mbr");

        mfr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mfl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mbl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        mbr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        /**
         * Init Color Sensor
         */

        robot.init(hardwareMap);

        /**
         * Servo HWMAP "2 Servos"
         */

        redServo = hardwareMap.servo.get("redServo");

        int mfr_encode = 0;
        int mfl_encode = 0;
        int mbl_encode = 0;
        int mbr_encode = 0;

        int firstRun = 0;

        robot.frontColorSensor.enableLed(false);

        waitForStart();

        while (opModeIsActive()){

            checkColor();

            telemetry.addData("Encode_mfr:", mfr_encode);
            telemetry.addData("Encode_mfr:", mfl_encode);
            telemetry.addData("Encode_mfr:", mbl_encode);
            telemetry.addData("Encode_mfr:", mbr_encode);

            telemetry.addData("Color Number:", colorNumber);
            telemetry.addData("RedServoPos:", getRedServo);

            telemetry.update();

            if (firstRun == 0)
                redServo.setPosition(0);
            sleep(500);

            mfr_encode = mfr.getCurrentPosition();
            mfl_encode = mfl.getCurrentPosition();
            mbl_encode = mbl.getCurrentPosition();
            mbr_encode = mbr.getCurrentPosition();

            getRedServo = redServo.getPosition();
            ;
            robot.frontColorSensor.enableLed(true);

            /**
             * Gold Block == Color Numbers 7, 8, 9, and 10;
             */

            telemetry.update();

            if (firstRun == 0 && found == 0) {
                DriveForward(0.5, 2350);
                Stop(100);
                sleep(250);
                if (isColorTrue(100)){
                    found = 1;
                    DriveForward(1, 1000);
                }
            }
            if (firstRun == 0 && found == 0) {
                StrafeRight(0.5, 1800);
                Stop(100);
                sleep(250);
                if (isColorTrue(100)){
                    found = 1;
                    DriveForward(1, 1000);
                }
            }
            if (firstRun == 0 && found == 0) {
                StrafeLeft(0.5, 3700);
                Stop(100);
                sleep(250);
                found = 1;
                DriveForward(1, 1000);
            }

            else{
                mfr.setPower(0);
                mfl.setPower(0);
                mbl.setPower(0);
                mbr.setPower(0);

                redServo.setPosition(1);
            }


            firstRun = 1;

            telemetry.update();

        }

    }

    public void checkColor(){

        colorNumber = robot.frontColorSensor.readUnsignedByte(ModernRoboticsI2cColorSensor.Register.COLOR_NUMBER);

        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

        Color.RGBToHSV(robot.frontColorSensor.red() * 8, robot.frontColorSensor.green() * 8, robot.frontColorSensor.blue() * 8, hsvValues);

        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });
    }

    public boolean isColorTrue(long time){

        checkColor();

        if (colorNumber == 7) {
            return true;
        }

        if (colorNumber == 8) {
            return true;
        }

        if (colorNumber == 9) {
            return true;
        }

        if (colorNumber == 10) {
            return true;
        }

        sleep(time);

        return false;

    }

    public void DriveForward(double speed, long time){

        mfr.setPower(-speed);
        mfl.setPower(speed);
        mbl.setPower(speed);
        mbr.setPower(-speed);

        sleep(time);

    }

    public void DriveBackward(double speed, long time){

        mfr.setPower(speed);
        mfl.setPower(-speed);
        mbl.setPower(-speed);
        mbr.setPower(speed);

        sleep(time);

    }

    public void StrafeLeft(double speed, long time){

        mfr.setPower(-speed);
        mfl.setPower(-speed);
        mbl.setPower(speed);
        mbr.setPower(speed);

        sleep(time);

    }

    public void StrafeRight(double speed, long time){

        mfr.setPower(speed);
        mfl.setPower(speed);
        mbl.setPower(-speed);
        mbr.setPower(-speed);

        sleep(time);

    }

    public void RotateRight(double speed, long time){

        mfr.setPower(-speed);
        mfl.setPower(-speed);
        mbl.setPower(-speed);
        mbr.setPower(-speed);

        sleep(time);

    }

    public void Stop(long time){

        mfr.setPower(0);
        mfl.setPower(0);
        mbl.setPower(0);
        mbr.setPower(0);

        sleep(time);

    }

}