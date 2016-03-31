package org.usfirst.frc3352.RobotCode2016.commands;

import org.usfirst.frc3352.RobotCode2016.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraight extends Command {
	double angle;

    public DriveStraight() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	angle = -Robot.drivetrain.getAngle();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.arcade(Robot.oi.getForwardVal(), -(Robot.drivetrain.getAngle()-angle)*.08);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !Robot.oi.driveJoy.getRawButton(6);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.arcade(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
