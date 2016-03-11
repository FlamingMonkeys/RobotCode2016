package org.usfirst.frc3352.RobotCode2016.commands;

import org.usfirst.frc3352.RobotCode2016.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.Encoder;

/**
 *
 */
public class DriveToPosition extends Command {

	double distance;
	Encoder encoder;
	/**
	 * Drives the robot straight forward a certain distance
	 * @param distance distance to drive in inches
	 */
    public DriveToPosition(double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	encoder = Robot.drivetrain.leftEncoder;
    	encoder.reset();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
