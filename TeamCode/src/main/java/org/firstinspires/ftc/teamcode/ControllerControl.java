//https://github.com/Rust-in-Pieces-15692/SkyStone/blob/master/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples/BasicOpMode_Linear.java

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp", group="")
public class ControllerControl extends LinearOpMode {
	private DcMotor leftDrive = null;
	private DcMotor rightDrive = null;
	private DcMotor middleDrive = null;
	private Servo clawServo = null;
	@Override
	public void runOpMode() {
		telemetry.addData("Status", "Initialized");
		telemetry.update();
		leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
		rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
		middleDrive = hardwareMap.get(DcMotor.class, "middle_drive");
		clawServo = hardwareMap.get(Servo.class,"claw_servo");
		leftDrive.setDirection(DcMotor.Direction.FORWARD);
		rightDrive.setDirection(DcMotor.Direction.REVERSE);
		middleDrive.setDirection(DcMotor.Direction.FORWARD);
		double padOneLeftStickX;
		double padOneLeftStickY;
		double padOneLeftTrigger;
		double padOneRightTrigger;
		int servoLocation = 0;

		;
		waitForStart();
		while (opModeIsActive()) {
			padOneLeftStickY = this.gamepad1.left_stick_y;
			padOneLeftStickX = this.gamepad1.left_stick_x;
			//reads the left and right triggers on gamepad 1
			padOneLeftTrigger = this.gamepad1.left_trigger;
			padOneRightTrigger = this.gamepad1.right_trigger;
			if (padOneLeftTrigger > 0 && padOneRightTrigger == 0){
				middleDrive.setPower(padOneLeftTrigger);
			} else if (padOneLeftTrigger == 0 && padOneRightTrigger > 0){
				middleDrive.setPower(-padOneRightTrigger);
			} else {
				middleDrive.setPower(0);
			}
			leftDrive.setPower(padOneLeftStickY - padOneLeftStickX);
			rightDrive.setPower(padOneLeftStickY + padOneLeftStickX);
			if (this.gamepad1.a) {
				servoLocation = 180;
			} else if (this.gamepad1.b){
				servoLocation = 0;
			}
			clawServo.setPosition(servoLocation);
		}
	}
}