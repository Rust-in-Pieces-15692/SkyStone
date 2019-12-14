//Red side, move foundation and park
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Blue Foundation/Park Inside", group="Blue")
public class Auto_BlueFoundationParkInside extends LinearOpMode{
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor middleDrive = null;
    private Servo foundationServo = null;
    private Servo clawServo = null;
    private ElapsedTime runtime = new ElapsedTime();
    private double tickConstant = 79;
    private double strafeConstant = 185;
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        middleDrive = hardwareMap.get(DcMotor.class, "middle_drive");
        foundationServo = hardwareMap.get(Servo.class, "foundation_servo" );
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
                foundationServo.setPosition(180);
                strafe(false,0.8,18);
                drive(false, 0.6, 23);
                drive(false, 0.3, 7);
                rackandpin(false);
                drive(true,0.5,30); //33 in
                drive(true,0.5,3);
                rackandpin(true);
                drive(false,0.3,1);
                strafe(true,0.8,35);
                drive(false,0.8,21); //24 in
                drive(false,0.3,3);
                strafe(true,0.8,23);
                mode = 1;
                telemetry.addData("Mode", "0");
                telemetry.update();
            }
            else {
                telemetry.addData("Mode", "Done");
                telemetry.update();
            }
        }
    }
    private void strafe(boolean left, double speed, float distance){
        //12.566/1440 * in to give desired ticks
        int tickValue = (int)(strafeConstant*distance);
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
    private void rackandpin(boolean upward){
        runtime.reset();
        if(upward){
            foundationServo.setPosition(180);
        }else{
            foundationServo.setPosition(0);
        }
        while (runtime.seconds() < 3) {
            continue;
        }
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
