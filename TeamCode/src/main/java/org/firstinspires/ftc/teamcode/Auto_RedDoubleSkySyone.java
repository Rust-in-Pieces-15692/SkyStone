package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Red Double SkyStone", group="Red")
public class Auto_RedDoubleSkyStone extends LinearOpMode{
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor middleDrive = null;
    private Servo clawServo = null;
    private double tickConstant = 12.566/1440;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        middleDrive = hardwareMap.get(DcMotor.class, "middle_drive");
        clawServo = hardwareMap.get(Servo.class,"claw_servo");
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        middleDrive.setDirection(DcMotor.Direction.FORWARD);
        int servoLocation = 0;
        int mode = 0;
        waitForStart();
        while (opModeIsActive()) {
          switch (mode) {
            case 0:
              drive(true,1,50);
              mode = 1;
              break;
            case 1:
              //grab block
              mode = 2;
              break;
            case 2:
              strafe(true,1,50);
              mode = 3;
              break;
            case 3:
              turn(true,1,180);
              mode = 4;
              break;
              
            //do the rest
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