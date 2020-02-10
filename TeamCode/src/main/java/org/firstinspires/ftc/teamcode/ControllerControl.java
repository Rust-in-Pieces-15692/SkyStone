//https://github.com/Rust-in-Pieces-15692/SkyStone/blob/master/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples/BasicOpMode_Linear.java

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="TeleOp", group="")
public class ControllerControl extends LinearOpMode {

	private Robot robot = new Robot();

	@Override
	public void runOpMode() {
		robot.initRobot(hardwareMap);
		telemetry.addData("Status", "Initialized");
		telemetry.update();

		double padOneLeftStickX;
		double padOneLeftStickY;
		double padOneLeftTrigger;
		double padOneRightTrigger;
		double padOneRightStickY;

		;
		waitForStart();
		while (opModeIsActive()) {
			/*Buttons in Use:
			Gamepad 1:
			  Left Stick X, Left Stick Y
			  Left Bumper, Right Bumper
			  Left Trigger, Right Trigger
			  A
			 */
			padOneLeftStickY = this.gamepad1.left_stick_y;
			padOneLeftStickX = this.gamepad1.left_stick_x;
			padOneRightStickY = this.gamepad1.right_stick_y;

			//reads the left and right triggers on gamepad 1
			padOneLeftTrigger = this.gamepad1.left_trigger;
			padOneRightTrigger = this.gamepad1.right_trigger;

			if (padOneLeftTrigger > 0 && padOneRightTrigger == 0){
				robot.middleDrive.setPower(padOneLeftTrigger);
			} else if (padOneLeftTrigger == 0 && padOneRightTrigger > 0){
				robot.middleDrive.setPower(-padOneRightTrigger);
			} else {
				robot.middleDrive.setPower(0);
			}
			robot.leftDrive.setPower(padOneLeftStickY - padOneLeftStickX);
			robot.rightDrive.setPower(padOneLeftStickY + padOneLeftStickX);
			if (this.gamepad1.left_bumper) {
				robot.foundationGrab(1);
			} else if (this.gamepad1.right_bumper){
				robot.foundationGrab(0);
			}
			robot.lift.setPower(-padOneRightStickY);
			if (this.gamepad1.a){
				robot.capstone.setPosition(0);
			} else {
				robot.capstone.setPosition(115);
			}
			if (this.gamepad1.dpad_up){
				robot.stoneGrab(1);
			} else if (this.gamepad1.dpad_down) {
				robot.stoneGrab(0);
			}
		}
	}
}