//Red side, move foundation and park
package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;


@Autonomous(name="Six Stone Auto", group="Dev")
public class Auto_6Stones extends LinearOpMode {
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor middleDrive = null;
    private Servo clawServoOne = null;
    private Servo clawServoTwo = null;

    //Position coords are in inches, same orientation as GM2 Figure 1.3-2
    // Blue building zone is top left
    // Blue block depot is bottom right
    static int HomeXCoord = 0;
    static int HomeYCoord = 0;
    int currentXCoord = 0;
    int currentYCoord = 0;

    @Override
    public void runOpMode() {

        waitForStart();
        while (opModeIsActive()) {

        }
    }

    private void goHome() {
        goToX(HomeXCoord);
        goToY(HomeYCoord);
    }

    private void goToX(int xLocation){

    }

    private void goToY(int yLocation){

    }

    private void intake() {

    }
    private void
}
