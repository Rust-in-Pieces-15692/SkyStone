package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.rdobotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@Autonomous(name="Park Outside", group="Red")
public class Auto_OutsidePark extends LinearOpMode{
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor middleDrive = null;
    private Servo clawServo = null;
    private double tickConstant = 172; //1440/12.556*1.5
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        middleDrive = hardwareMap.get(DcMotor.class, "middle_drive");
        clawServo = hardwareMap.get(Servo.class,"claw_servo");
        leftDrive.setDirection(DcMotor.Direction.REVERSE);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        middleDrive.setDirection(DcMotor.Direction.FORWARD);
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setTargetPosition(0);
        rightDrive.setTargetPosition(0);
        middleDrive.setTargetPosition(0);
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        int servoLocation = 0;
        int mode = 0;
        waitForStart();
        while (opModeIsActive()) {
            if (mode == 0){
                strafe(true,0.8,60);
                mode = 1;
                telemetry.addData("Mode", "0");
                telemetry.update();
            }
            if (mode == 1){
                drive(true,1,12)
                mode = 2;
                telemetry.addData("Mode", "1");
                telemetry.update();
            }
        }
            

    private void strafe(boolean left, double speed, float distance){
        //12.566/1440 * in to give desired ticks
        int tickValue = (int)(tickConstant*distance);
        if (left){
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
    private void drive(boolean forward, double speed, float distance){
        //12.566/1440 * in to give desired ticks
        int tickValue = (int)(tickConstant*distance);
        if (forward){
            leftDrive.setTargetPosition(tickValue);
            rightDrive.setTargetPosition(tickValue);
        } else {
            leftDrive.setTargetPosition(-tickValue);
            rightDrive.setTargetPosition(-tickValue);
        }
        leftDrive.setPower(speed);
        rightDrive.setPower(speed);
        while (leftDrive.isBusy() && rightDrive.isBusy()){
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
    private void turn(boolean right, double speed, int degrees){
        //Will add when access to field
    }
    private void resetEncoders(){
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        middleDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        middleDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}
