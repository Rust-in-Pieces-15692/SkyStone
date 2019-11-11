//https://github.com/Rust-in-Pieces-15692/SkyStone/blob/master/FtcRobotController/src/main/java/org/firstinspires/ftc/robotcontroller/external/samples/BasicOpMode_Linear.java

package org.firstinspires.ftc.teamcode.ControllerControl;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Controller control", group="Linear Opmode")
@Disabled
public class BasicOpMode_Linear extends LinearOpMode {
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
		//waitForStart();
		runtime.reset();
		while (opModeIsActive()) {
			double leftPower;
			double rightPower;
			double middlePower;
			
		}
	}
}
