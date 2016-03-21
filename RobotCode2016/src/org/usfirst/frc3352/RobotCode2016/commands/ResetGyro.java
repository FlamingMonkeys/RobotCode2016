package org.usfirst.frc3352.RobotCode2016.commands;

import org.usfirst.frc3352.RobotCode2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ResetGyro extends Command {

	int i=0;
    public ResetGyro() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooter);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooter.resetGyro();//start reset process
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	i++;//count each time execute() loops
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (i==50);//end after two seconds (100x20ms loop) have passed
    }

    // Called once after isFinished returns true
    protected void end() {
    	i=0;//reset for next time
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
