package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.Robot;

@Autonomous(name="Red Park Outside ", group="Red")
public class RedOutsidePark extends LinearOpMode{
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
                break;
        }
   }
}
