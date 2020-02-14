package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot{
    // Declare parts of the robot members.
    public DcMotor leftDrive;
    public DcMotor rightDrive;
    public DcMotor middleDrive;
    public DcMotor lift;
    public Servo outtake;
    public Servo capstone;
    public Servo foundationLeft;
    public Servo foundationRight;
    private double tickConstant = 79;
    private double strafeConstant = 57;

    HardwareMap hardwareMap;

    public void initRobot(HardwareMap ahwmap) {
        this.hardwareMap = ahwmap;
        this.leftDrive = this.hardwareMap.get(DcMotor.class, "left_drive");
        this.rightDrive = this.hardwareMap.get(DcMotor.class, "right_drive");
        this.middleDrive = this.hardwareMap.get(DcMotor.class, "middle_drive");
        this.lift = this.hardwareMap.get(DcMotor.class, "lift");
        this.outtake = this.hardwareMap.get(Servo.class, "outtake");
        this.capstone = this.hardwareMap.get(Servo.class, "capstone");
        this.foundationLeft = this.hardwareMap.get(Servo.class, "foundationLeft");
        this.foundationRight = this.hardwareMap.get(Servo.class, "foundationRight");

        this.leftDrive.setDirection(DcMotor.Direction.FORWARD);
        this.rightDrive.setDirection(DcMotor.Direction.REVERSE);
        this.middleDrive.setDirection(DcMotor.Direction.FORWARD);
        this.lift.setDirection(DcMotor.Direction.FORWARD);

        this.lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        resetEncoders();
    }

    public void stopAll(){
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        middleDrive.setPower(0);
        lift.setPower(0);
    }

    public void resetEncoders(){
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setTargetPosition(0);
        rightDrive.setTargetPosition(0);
        middleDrive.setTargetPosition(0);
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void resetLiftEncoders(){
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    public void stoneGrab(int close){

        if (close == 1){
            outtake.setPosition(0);
        } else {
            outtake.setPosition(25);
        }
    }

    public void foundationGrab(int lower){
        //TODO: TUNE THESE VALUES
        if (lower == 1){
            foundationRight.setPosition(0);
            foundationLeft.setPosition(0);
        }
        else {
            foundationLeft.setPosition(180);
            foundationRight.setPosition(180);
        }
    }

    public void turn(int degrees){
        //TODO: TURNING CODE
    }

    public void drive(int inches){
        int tickValue = (int)(tickConstant*inches);
        leftDrive.setTargetPosition(tickValue);
        rightDrive.setTargetPosition(tickValue);
        leftDrive.setPower(0.8);
        rightDrive.setPower(0.8);
        while (leftDrive.isBusy() && rightDrive.isBusy()){}
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        resetEncoders();
    }
    public void strafe(int inches){
        int tickValue = (int)(strafeConstant*inches);
        middleDrive.setTargetPosition(tickValue);
        rightDrive.setPower(.8);
        while (middleDrive.isBusy()){}
        middleDrive.setPower(0);
        resetEncoders();
    }
}
