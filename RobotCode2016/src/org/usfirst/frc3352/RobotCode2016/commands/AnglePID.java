package org.usfirst.frc3352.RobotCode2016.commands;

import org.usfirst.frc3352.RobotCode2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AnglePID extends Command {
	boolean direction;
	double target;
	double angle;
	double kP;
	double error;
	double p;
	double kI;
	double integral;
	double i;
	

    public AnglePID(double degrees) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooter);
    	
        while(degrees>=360){
        	degrees -= 360;
        }
        while(degrees<0){
        	degrees +=360;
        }
        target = degrees;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	angle = Robot.shooter.getAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(target>angle){
    		kP = .1;
    		kI = .01;
    	}else {
    		kP = .2;
    		kI = .02;
    	}
    	
    	error = target - angle;
    	integral += error;
    	
    	if(error<0 && direction) integral = 0;
    	
    	p = error*kP;
    	i = integral*kI;

		Robot.shooter.setAngle(p+i);
		
    	angle = Robot.shooter.getAngle();
    	if(error<0) direction = false;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return error<5;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.setAngle(0);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}