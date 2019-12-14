package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Disabled
@Autonomous(name="Red Double SkyStone", group="Red")
public class Auto_RedDoubleSkyStone extends LinearOpMode{
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor middleDrive = null;
    private DcMotor clawDrive = null;
    private Servo clawServo = null;
    private ColorSensor colorSensor = null;
    private ElapsedTime runtime = new ElapsedTime();
    private double tickConstant = 76;
    private double strafeConstant = 229;
    private int stonesCollected = 0;
    private int movedLeft = 0;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        middleDrive = hardwareMap.get(DcMotor.class, "middle_drive");
        clawDrive = hardwareMap.get(DcMotor.class, "claw_drive");
        clawServo = hardwareMap.get(Servo.class, "claw_servo");
        colorSensor = hardwareMap.get(ColorSensor.class, "color_sensor");
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        middleDrive.setDirection(DcMotor.Direction.FORWARD);
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setTargetPosition(0);
        rightDrive.setTargetPosition(0);
        middleDrive.setTargetPosition(0);
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        int servoLocation = 0;
        int mode = 0;
        waitForStart();
        while (opModeIsActive()) {
            switch (mode) {
                case 0:
                    runtime.reset();
                    clawServo.setPosition(0);
                    while (runtime.seconds() < 3) {
                        continue;
                    }
                    drive(true,0.7,20);
                    mode = 1;
                    break;
                case 1:
                    if ((colorSensor.red() > colorSensor.blue() && colorSensor.green() > colorSensor.blue()) || (colorSensor.red() < 100 && colorSensor.green() < 100 && colorSensor.blue() < 100)) {
                        //Dude, that's a SkyStone!
                        //go get it
                        mode = 2;
                    } else {
                        //check the next one
                        strafe(false,0.7,((float)0.5));
                    }
                    break;
                case 2:
                    strafe(false,0.7,1);
                    movedLeft++;
                    if (colorSensor.red() < 100 && colorSensor.green() < 100 && colorSensor.blue() < 100) {
                        //This is the one!
                        movedLeft -= 9;
                        strafe(true, 0.7, 9);
                        drive(true, 0.7, 8);
                        //grab it
                        clawMove(true,0.7,6);
                        runtime.reset();
                        clawServo.setPosition(180);
                        while (runtime.seconds() < 3) {
                            continue;
                        }
                        clawMove(false,0.7,6);
                        drive(false,0.7,3);
                        mode = 3;
                    } else if(colorSensor.red() > colorSensor.blue() && colorSensor.green() > colorSensor.blue()) {
                        //Alright, not it
                        mode = 1;
                    } else {
                        //Bruh, what the heck is this then?
                        mode = 1;
                    }
                    break;
                case 3:
                    drive(false, 0.7, 28);
                    //turn(true,0.7,90);
                    strafe(false,0.7,44+movedLeft);
                    //drop it
                    runtime.reset();
                    clawServo.setPosition(0);
                    while (runtime.seconds() < 3) {
                        continue;
                    }
                    strafe(true,0.7,(44+(movedLeft*2))); //Go back and get the other!
                    drive(true,0.7,3);
                    //turn(false,0.7,90);
                    drive(true,0.7,28);
                    //grab it
                    runtime.reset();
                    clawServo.setPosition(180);
                    while (runtime.seconds() < 3) {
                        continue;
                    }
                    drive(false,0.7,3);
                    drive(false, 0.7, 28);
                    //turn(true,0.7,90);
                    strafe(false,0.7,44+movedLeft);
                    //drop it
                    runtime.reset();
                    clawServo.setPosition(0);
                    while (runtime.seconds() < 3) {
                        continue;
                    }
                    mode = 4;
                    break;
                case 4:
                    //go back to center (6' from bottom)
                    strafe(true,0.7,(float)33.5);
                    //we're parked now.
                    mode = 5;
                    break;
                case 5:
                    break;
            }
        }
    }
    private void strafe(boolean left, double speed, float distance){
        //12.566/1440 * in to give desired ticks
        int tickValue = (int)(strafeConstant*distance);
        if (left){
            middleDrive.setTargetPosition(tickValue);
        } else {
            middleDrive.setTargetPosition(-tickValue);
        }
        middleDrive.setPower(speed);
        while (middleDrive.isBusy()) {
            telemetry.addData("Tick Counter", tickValue);
            telemetry.addData("Current Position", middleDrive.getCurrentPosition());
            telemetry.update();
        }
        middleDrive.setPower(0);
        resetEncoders();
    }
    private void clawMove(boolean up, double speed, float ticks){
        //12.566/1440 * in to give desired ticks
        int tickValue = (int)(ticks);
        if (up){
            clawDrive.setTargetPosition(tickValue);
        } else {
            clawDrive.setTargetPosition(-tickValue);
        }
        clawDrive.setPower(speed);
        while (clawDrive.isBusy()) {
            telemetry.addData("Tick Counter", tickValue);
            telemetry.addData("Current Position", clawDrive.getCurrentPosition());
            telemetry.update();
        }
        clawDrive.setPower(0);
        resetEncoders();
    }
    private void drive(boolean forward, double speed, float distance){
        //12.566/1440 * in to give desired ticks
        int tickValue = (int)(tickConstant*distance);
        if (forward){
            leftDrive.setTargetPosition(tickValue);
            rightDrive.setTargetPosition(tickValue);
        } else {
            leftDrive.setTargetPosition(-tickValue);
            rightDrive.setTargetPosition(-tickValue);
        }
        leftDrive.setPower(speed);
        rightDrive.setPower(speed);
        while (leftDrive.isBusy() && rightDrive.isBusy()){
            telemetry.addData("L Tick Counter", tickValue);
            telemetry.addData("L Current Position", leftDrive.getCurrentPosition());
            telemetry.addData("R Tick Counter", tickValue);
            telemetry.addData("R Current Position", rightDrive.getCurrentPosition());
            telemetry.update();
        }
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        resetEncoders();
    }
    private void turn(boolean right, double speed, int degrees){
        //Will add when access to field
    }
    private void resetEncoders(){
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        clawDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        clawDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
