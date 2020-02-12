package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name="Red Foundation Park Inside ", group="Red")
public class RedFoundationParkInside extends LinearOpMode{
    private Robot robot = new Robot();
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();
        int state = 0;
        switch (state) {
            case 0:
                robot.strafe(48);
                state = 1;
                break;
            case 1:
                robot.drive(12);
                state = 2;
                break;
            case 2:
                robot.foundationGrab(1);
                state = 3;
                break;
            case 3:
                robot.drive(-12);
                state = 4;
                break;
            case 4:
                robot.foundationGrab(0);
                state = 5;
                break;
            case 5:
                robot.strafe(-48);
                state = 6;
                break;
            case 6:
                robot.drive(14);
                state = 7;
                break;
            case 7:
                robot.stopAll();
                state = 8;
                break;
            case 8:
                break;


        }
    }
}

