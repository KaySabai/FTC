/*
Copyright 2024 

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;


/**
 * This file contains a minimal example of a Linear "OpMode". An OpMode is a 'program' that runs
 * in either the autonomous or the TeleOp period of an FTC match. The names of OpModes appear on
 * the menu of the FTC Driver Station. When an selection is made from the menu, the corresponding
 * OpMode class is instantiated on the Robot Controller and executed.
 *
 * Remove the @Disabled annotation on the next line or two (if present) to add this OpMode to the
 * Driver Station OpMode list, or add a @Disabled annotation to prevent this OpMode from being
 * added to the Driver Station.
 */
@TeleOp

public class HKFiestOpMode extends LinearOpMode {
    private Blinker control_Hub;
    private IMU imu;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor arm;
    private DcMotor viperSlide;
    private CRServo leftRoller;
    private CRServo rightRoller;
    private Servo wrist;
    
    @Override
    public void runOpMode() {
        control_Hub = hardwareMap.get(Blinker.class, "Control Hub");
        imu = hardwareMap.get(IMU.class, "imu");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        arm = hardwareMap.get(DcMotor.class, "arm");
        viperSlide = hardwareMap.get(DcMotor.class, "viperSlide");
        leftRoller = hardwareMap.get(CRServo.class, "leftRoller");
        rightRoller = hardwareMap.get(CRServo.class, "rightRoller");
        wrist = hardwareMap.get(Servo.class, "wrist");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        double LeftStickYPower = 0;
        double LeftStickXPower=0;

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            LeftStickYPower=-this.gamepad1.left_stick_y;
            LeftStickXPower=-this.gamepad1.left_stick_x;
            
            //drive forward and back
            backLeft.setPower(-LeftStickYPower);
            backRight.setPower(LeftStickYPower);
            frontLeft.setPower(-LeftStickYPower);
            frontRight.setPower(LeftStickYPower);
            
            //turn
            backLeft.setPower(-LeftStickXPower/1.5);
            backRight.setPower(LeftStickXPower/2);
            frontLeft.setPower(LeftStickXPower/1.5);
            frontRight.setPower(LeftStickXPower/2);
            
            //raise arm
            if (this.gamepad1.a) {
                arm.setPower(0.5);
                sleep(100);
                arm.setPower(0);
                telemetry.addData("A Pressed", this.gamepad1.a);
            }
            
            //lower arm
            if (this.gamepad1.x) {
                arm.setPower(-0.5);
                sleep(100);
                arm.setPower(0);

                telemetry.addData("x Pressed", this.gamepad1.x);
            }
            
            
            //pick up 
            if (this.gamepad1.b) {
                leftRoller.setPower(-1);
                rightRoller.setPower(1);
                
                telemetry.addData("b Pressed", this.gamepad1.b);
                telemetry.update();
                sleep(100);
                leftRoller.setPower(0);
                rightRoller.setPower(0);
                
            }
            
            //drop
            if (this.gamepad1.y) {
                leftRoller.setPower(1);
                rightRoller.setPower(-1);
                
                telemetry.addData("b Pressed", this.gamepad1.b);
                telemetry.update();
                sleep(100);
                leftRoller.setPower(0);
                rightRoller.setPower(0);
                
            }
            
            //arm up
            if (this.gamepad1.right_bumper) {
                wrist.setPosition(wrist.getPosition()-0.01);
                sleep(100);
                
                telemetry.addData("right_bumper Pressed", this.gamepad1.right_bumper);
                telemetry.update();
            }
            
            //arm down
            if (this.gamepad1.left_bumper){
                wrist.setPosition(wrist.getPosition()+0.01);
                sleep(100);
                telemetry.addData("left_bumper Pressed", this.gamepad1.left_bumper);
                telemetry.update();
            }
        
            //viper slide
            viperSlide.setPower(this.gamepad1.left_trigger);
            viperSlide.setPower(-this.gamepad1.right_trigger);
    
            
            telemetry.addData("Status", "Running");
            telemetry.update();

        }
    }
}
