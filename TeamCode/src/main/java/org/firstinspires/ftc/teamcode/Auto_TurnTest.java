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

@Disabled
@Autonomous(name="Turn Test", group="Dev")
public class Auto_TurnTest extends LinearOpMode {
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor middleDrive = null;
    private DcMotor xClawDrive = null;
    private Servo clawServo = null;
    private BNO055IMU imu;
    private double tickConstant = 172; //1440/12.556*1.5
    private double strafeConstant = ((1440 * (2 / 3)) / 12.566);
    private float rackandpinRadius = 25;
    Orientation angles;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        middleDrive = hardwareMap.get(DcMotor.class, "middle_drive");
        xClawDrive = hardwareMap.get(DcMotor.class, "xclaw_drive");
        clawServo = hardwareMap.get(Servo.class, "claw_servo");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        middleDrive.setDirection(DcMotor.Direction.FORWARD);
        xClawDrive.setDirection(DcMotor.Direction.FORWARD);
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        xClawDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setTargetPosition(0);
        rightDrive.setTargetPosition(0);
        middleDrive.setTargetPosition(0);
        xClawDrive.setTargetPosition(0);
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        xClawDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        int servoLocation = 0;
        int mode = 0;
        waitForStart();
        while (opModeIsActive()) {
            if (mode == 0) {
                turn(true, 0.5, 90);
                mode = 1;
            }
        }
    }

    private void strafe(boolean left, double speed, float distance) {
        //12.566/1440 * in to give desired ticks
        int tickValue = (int) (strafeConstant * distance);
        if (left) {
            middleDrive.setTargetPosition(tickValue);
        } else {
            middleDrive.setTargetPosition(-tickValue);
        }
        middleDrive.setPower(speed);
        while (middleDrive.isBusy()) {
            telemetry.addData("Tick Counter", tickValue);
            telemetry.addData("Current Position", middleDrive.getCurrentPosition());
            telemetry.update();
        }
        middleDrive.setPower(0);
        resetEncoders();
    }

    private void drive(boolean forward, double speed, float distance) {
        //12.566/1440 * in to give desired ticks
        int tickValue = (int) (tickConstant * distance);
        if (forward) {
            leftDrive.setTargetPosition(tickValue);
            rightDrive.setTargetPosition(tickValue);
        } else {
            leftDrive.setTargetPosition(-tickValue);
            rightDrive.setTargetPosition(-tickValue);
        }
        leftDrive.setPower(speed);
        rightDrive.setPower(speed);
        while (leftDrive.isBusy() && rightDrive.isBusy()) {
            telemetry.addData("L Tick Counter", tickValue);
            telemetry.addData("L Current Position", leftDrive.getCurrentPosition());
            telemetry.addData("R Tick Counter", tickValue);
            telemetry.addData("R Current Position", rightDrive.getCurrentPosition());
            telemetry.update();
        }
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        resetEncoders();
    }

    private void turn(boolean right, double speed, int degrees) {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        float currentAngle = angles.firstAngle;
        float goalAngle;
        if (right) {
            leftDrive.setPower(speed);
            rightDrive.setPower(-speed);
            goalAngle = angles.firstAngle - degrees;
            if (goalAngle < -180) {
                goalAngle = goalAngle + 360;
            }
        } else {
            leftDrive.setPower(-speed);
            rightDrive.setPower(speed);
            goalAngle = angles.firstAngle + degrees;
            if (goalAngle > 180) {
                goalAngle = goalAngle - 360;
            }
        }
            while (angles.firstAngle != goalAngle) {
                angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
                telemetry.addData("Goal Angle", goalAngle);
                telemetry.addData("Current Angle", angles.firstAngle);
                telemetry.update();
            }
            leftDrive.setPower(0);
            rightDrive.setPower(0);
            resetEncoders();
        }
        private void resetEncoders(){
            leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            middleDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            xClawDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            middleDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            xClawDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }
