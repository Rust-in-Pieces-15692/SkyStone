package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class PIDController {
    private ElapsedTime timePID = new ElapsedTime();
    private float currentLocation;
    private float goalLocation;
    private float currentError;
    private float currentTime;
    private float previousError;
    private float previousTime;
    private float iTerm = 0;
    private float kP;
    private float kI;
    private float kD;
    public PIDController(){}
    public void setPID(float currentLocation, float goalLocation){
        this.currentLocation = currentLocation;
        this.goalLocation = goalLocation;
        if (currentTime == 0 && previousTime == 0){
            timePID.reset();
        }
    }
    public void reset(){
        this.currentLocation = 0;
        this.goalLocation = 0;
        this.currentError = 0;
        this.previousError = 0;
        this.currentTime = 0;
        this.previousError = 0;
        this.iTerm = 0;
        timePID.reset();
    }
    public void setDriveConstants(){
        //TODO: add drive PID constants
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }
    public void setStrafeConstants(){
        //TODO: add strafe PID constants
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }
    public void setCustomConstants(float kP, float kI, float kD){
        //TODO: add strafe PID constants
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }
    public float computePID(){
        this.currentTime = timePID.nanoseconds();
        this.currentError = goalLocation - currentLocation;

        float pTerm = kP * currentError;

        iTerm = iTerm + (kI * (currentTime - previousTime));

        float dTerm = kD * ((currentError - previousError)/(currentTime - previousTime));

        float power = pTerm + iTerm + dTerm;

        this.previousError = currentError;
        this.previousTime = currentTime;

        if (currentError == 0){
            reset();
        }

        return power;
    }
}
