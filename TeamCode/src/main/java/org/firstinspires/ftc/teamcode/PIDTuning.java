package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.PIDController;

@TeleOp
public class PIDTuning extends LinearOpMode {

    private Robot robot = new Robot();

    private PIDController PID = new PIDController();

    private int goalLocation = 1896; //24 inches

    double pConstant = 0.1;
    double iConstant = 0.1;
    double dConstant = 0.1;

    double power = 0;

    boolean aPressed = false;
    boolean bPressed = false;
    boolean dUp = false;
    boolean dDown = false;
    boolean dLeft = false;
    boolean dRight = false;

    public void runOpMode(){
        robot.initRobot(hardwareMap);
        waitForStart();
        while (opModeIsActive()){
            telemetry.addData("P term control", "A +, B -");
            telemetry.addData("I term control", "Right +, Left -");
            telemetry.addData("D term control", "Up +, Down -");
            telemetry.addData("Run Test", "Hold both Bumpers");
            telemetry.addData("Reset Encoders", "Left or Right Stick Button");
            telemetry.addLine(" ");
            telemetry.addData("Current Encoder Average", (robot.leftDrive.getCurrentPosition() + robot.rightDrive.getCurrentPosition()) / 2);
            telemetry.addData("Goal Encoder Location", goalLocation);
            telemetry.addLine(" ");
            telemetry.addData("P term", pConstant);
            telemetry.addData("I term", iConstant);
            telemetry.addData("D term", dConstant);
            telemetry.addLine(" ");
        if (gamepad1.left_bumper && gamepad1.right_bumper) {
                PID.setCustomConstants(pConstant, dConstant, iConstant);
                PID.setPID((robot.leftDrive.getCurrentPosition() + robot.rightDrive.getCurrentPosition()) / 2, goalLocation);
                power = PID.computePID();
                telemetry.addData("[PID] Power", power);
                robot.leftDrive.setPower(power);
                robot.rightDrive.setPower(power);
            }
            if (!aPressed && gamepad1.a){
                aPressed = true;
                pConstant += 0.01;
            } else if (aPressed && !gamepad1.a){
                aPressed = false;
            }
            if (!bPressed && gamepad1.b){
                bPressed = true;
                pConstant -= 0.01;
            } else if (bPressed && !gamepad1.b){
                bPressed = false;
            }
            if (!dUp && gamepad1.dpad_up){
                dUp = true;
                dConstant += 0.01;
            } else if (dUp && !gamepad1.dpad_up){
                dUp = false;
            }
            if (!dDown && gamepad1.dpad_down){
                dDown = true;
                dConstant -= 0.01;
            } else if (dDown && !gamepad1.dpad_down){
                dDown = false;
            }
            if (!dRight && gamepad1.dpad_right){
                dRight = true;
                iConstant += 0.01;
            } else if (dRight && !gamepad1.dpad_right){
                dRight = false;
            }
            if (!dLeft && gamepad1.dpad_left){
                dLeft = true;
                dConstant -= 0.01;
            } else if (dLeft && !gamepad1.dpad_left){
                dLeft = false;
            }
            if (gamepad1.left_stick_button || gamepad1.right_stick_button){
                robot.resetEncoders();
            }
            telemetry.update();
        }
    }
}
