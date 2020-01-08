/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Dom's secret project", group="Crazy")
@Disabled
public class Auto_Red6Stones extends OpMode
{
    // Declare OpMode members.
    private enum State {
        STATE_INITIAL,
        STATE_GOING_HOME,
        STATE_NAVIGATING_QUARRY,
        STATE_INTAKING_STONE,
        STATE_PROCESSING_STONE,
        STATE_NAVIGATING_TO_FOUNDATION,
        STATE_PLACING_BLOCK,
        STATE_PARKING,
        STATE_STOP
    }

    private ElapsedTime runtime = new ElapsedTime();

    int currentXLocation = 0;
    int currentYLocation = 0;
    static int startingXLocation = 0;
    static int startingYLocation = 0;
    static int parkingXLocation = 0;
    static int parkingYLocation = 0;
    static int homeXLocation = 0;
    static int homeYLocation = 0;
    static int foundationXLocation = 0;
    static int foundationYLocation = 0;

    private State currentState;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {

    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
        setState(State.STATE_INITIAL);
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        if (runtime.seconds() > 25){
            setState(State.STATE_PARKING);
        }
        switch (currentState){
            case STATE_INITIAL:
                setState(State.STATE_GOING_HOME);
            case STATE_GOING_HOME:
                if (currentXLocation == homeXLocation && currentYLocation == homeYLocation){
                    if (1 == 1) { //TODO: add code to check for block
                        setState(State.STATE_NAVIGATING_TO_FOUNDATION);
                    }
                    else {
                        setState(State.STATE_NAVIGATING_QUARRY);
                    }
                }
                else {
                    //TODO: add code to navigate robot home, X first than Y
                }
            case STATE_NAVIGATING_QUARRY:
                //TODO: Figure out how to determine necessary position
            case STATE_INTAKING_STONE:
                if (1 == 1) { //TODO: add color sensor code

                }
                else {
                    //TODO: add intake spinning code
                    //TODO: add slow drive forward
                }
            case STATE_PROCESSING_STONE:
                if (1 == 1) { //TODO: add code for if skystone
                    setState(State.STATE_GOING_HOME);
                } else if (2 == 1){ //TODO: add code for if stone
                    setState(State.STATE_INTAKING_STONE);
                } else {
                    //TODO: add code for positioning stone correctly
                }
            case STATE_NAVIGATING_TO_FOUNDATION:
                if (currentXLocation == foundationXLocation && currentYLocation == foundationXLocation){
                    setState(State.STATE_PLACING_BLOCK);
                }
                else {
                    if (currentYLocation != foundationYLocation){
                        //TODO: add Y drive
                    }
                    else {
                        //TODO: add X drive
                    }
                }
            case STATE_PLACING_BLOCK:
                if (1 == 1){ //TODO: setup detecting if block is placed
                    setState(State.STATE_GOING_HOME);
                }
                else {

                }
            case STATE_PARKING:
                if (currentXLocation == parkingXLocation && currentYLocation == parkingYLocation){
                    setState(State.STATE_STOP);
                }
                else {
                    //TODO: add Y drive code
                    //TODO: add X drive code
                    //TODO: add lower arm code
                }
            case STATE_STOP:
                stopAll();
        }

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

    private void setState(State newState){
        currentState = newState;
    }

    private void strafe(boolean moveLeft, float power){
        //TODO: setup Strafe
    }

    private void drive(boolean driveForward, float power){
        //TODO: setup drive
    }
    
    private void stopAll(){
        //TODO: Stop all the things
    }
}
