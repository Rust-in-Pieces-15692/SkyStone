//https://github.com/Rust-in-Pieces-15692/SkyStone/blob/master/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples/BasicOpMode_Linear.java

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp", group="")
public class EthanOofControllerControl extends LinearOpMode {
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor middleDrive = null;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        middleDrive = hardwareMap.get(DcMotor.class, "middle_drive");
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        middleDrive.setDirection(DcMotor.Direction.FORWARD);
        double padOneLeftStickX;
        double padOneLeftStickY;
        waitForStart();
        while (opModeIsActive()) {
            padOneLeftStickX = this.gamepad1.left_stick_x;
            padOneLeftStickY = this.gamepad1.left_stick_y;

            leftDrive.setPower(padOneLeftStickY);
            rightDrive.setPower(padOneLeftStickY);

            if(padOneLeftStickX > 0) {
                middleDrive.setPower(-padOneLeftStickX);
                //not sure if its negative or positive because I dont know where the motor is on the robot
            }else if(padOneLeftStickX < 0) {
                middleDrive.setPower(padOneLeftStickX);
            }else {
                middleDrive.setPower(0);
            }



        }
    }
}
}
