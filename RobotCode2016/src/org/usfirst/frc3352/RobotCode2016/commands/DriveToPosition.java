package org.usfirst.frc3352.RobotCode2016.commands;

import org.usfirst.frc3352.RobotCode2016.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;

/**
 *
 */
public class DriveToPosition extends Command {

	double distance;
	double angle;
	Encoder encoder;
	/**
	 * Drives the robot straight forward a certain distance
	 * @param distance distance to drive in inches
	 */
    public DriveToPosition(double inches) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	distance = inches;
    }
    public DriveToPosition(double feet, double inches) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	distance = (feet*12) + inches;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	encoder = Robot.drivetrain.leftEncoder;
    	encoder.reset();
    	SmartDashboard.putNumber("distance", encoder.getDistance());
    	SmartDashboard.putNumber("target distance", distance);
    	angle = Robot.drivetrain.getAngle();
    	/*Robot.drivetrain.resetAngle();
    	SmartDashboard.putNumber("drivetrain angle", Robot.drivetrain.getAngle());
    	Timer.delay(1);
    	SmartDashboard.putNumber("drivetrain angle", Robot.drivetrain.getAngle());
    	Timer.delay(1);*/
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		Robot.drivetrain.arcade(1, (angle-Robot.drivetrain.getAngle())*.15);
    		SmartDashboard.putNumber("distance", encoder.getDistance());
    		SmartDashboard.putNumber("drivetrain angle", Robot.drivetrain.getAngle());
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return encoder.getDistance() >= distance;
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
