package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot{
    // Declare parts of the robot members.
    public DcMotor leftDrive;
    public DcMotor rightDrive;
    public DcMotor middleDrive;

    HardwareMap hardwareMap;

    public void initRobot(HardwareMap ahwmap) {
        this.hardwareMap = ahwmap;
        this.leftDrive = this.hardwareMap.get(DcMotor.class, "left_drive");
        this.rightDrive = this.hardwareMap.get(DcMotor.class, "right_drive");
        this.middleDrive = this.hardwareMap.get(DcMotor.class, "middle_drive");

        this.leftDrive.setDirection(DcMotor.Direction.FORWARD);
        this.rightDrive.setDirection(DcMotor.Direction.REVERSE);
        this.middleDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    public void loop(){}

    public void stopAll(){
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        middleDrive.setPower(0);
    }

    public void resetEncoders(){
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        middleDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}
