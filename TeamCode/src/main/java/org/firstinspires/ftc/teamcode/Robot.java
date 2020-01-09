package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class Robot extends OpMode{
    // Declare parts of the robot members.
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor middleDrive = null;

    public void init() {
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        middleDrive = hardwareMap.get(DcMotor.class, "middle_drive");

    }

    public void loop(){}

    public void stopAll(){
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        middleDrive.setPower(0);
    }
}
