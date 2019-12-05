package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Red Double SkySyone", group="Red")
public class Auto_RedDoubleSkySyone extends LinearOpMode{
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor middleDrive = null;
    private Servo clawServo = null;
    private double tickConstant = (12.566/1440)*1.5;
    private int stonesCollected = 0;
    private int movedLeft = 0;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        middleDrive = hardwareMap.get(DcMotor.class, "middle_drive");
        clawServo = hardwareMap.get(Servo.class, "claw_servo");
        colorSensor = hardwareMap.get(ColorSensor.class, "color_sensor");
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        middleDrive.setDirection(DcMotor.Direction.FORWARD);
        int servoLocation = 0;
        int mode = 0;
        waitForStart();
        while (opModeIsActive()) {
          switch (mode) {
            case -1:
              drive(true,0.7,20);
              mode = 0;
            case 0:
              if (colorSensor.red() > colorSensor.blue() && colorSensor.green() > colorSensor.blue()) {
                //Dude, that's a SkyStone!
                //go get it
                mode = 1;
              } else {
                //check the next one
                strafe(left,0.7,1);
              }
              break;
            case 1:
              strafe(false,0.7,1);
              movedLeft++;
              if (colorSensor.red() < 20 && colorSensor.green() < 20 && colorSensor.blue() < 20) {
                //This is the one!
                movedLeft -= 9;
                strafe(true,0.7,9);
                drive(true,0.7,8);
                //grab it
              }
              mode = 2;
              break;
            case 2:
              drive(false, 0.7, 28);
              turn(true,0.7,90);
              drive(true,0.7,44+driveLeft);
              //drop it 
              drive(false,0.7,(44+(driveLeft*2)));
          }
        }
    }
    private void strafe(boolean right, double speed, float distance){
        //12.566/1440 * in to give desired ticks
        useEncoders();
        int tickValue = (int) Math.floor(tickConstant*distance);
        middleDrive.setPower(speed);
        if (right){
            middleDrive.setTargetPosition(tickValue);
        } else {
            middleDrive.setTargetPosition(-tickValue);
        }
        while (middleDrive.isBusy()) {
            continue;
        }
        middleDrive.setPower(0);
        resetEncoders();
    }
    private void drive(boolean forward, double speed, float distance){
        //12.566/1440 * in to give desired ticks
        useEncoders();
        int tickValue = (int) Math.floor(tickConstant*distance);
        if (!forward){
          tickValue *= -1;
        }
        leftDrive.setPower(speed);
        rightDrive.setPower(speed);
        leftDrive.setTargetPosition(tickValue);
        rightDrive.setTargetPosition(tickValue);
        while (leftDrive.isBusy() && rightDrive.isBusy()){
            continue;
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
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        middleDrive.setPower(0);
    }
    private void useEncoders(){
        resetEncoders();
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
