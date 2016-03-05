package org.usfirst.frc3352.RobotCode2016.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc3352.RobotCode2016.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 *
 */
public class ShooterPneumatics extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private final DoubleSolenoid BallPusher = RobotMap.pushball;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
    public void push()
    {
    	BallPusher.set(DoubleSolenoid.Value.kForward);
    	
    }
    public void pull()
    {
    	BallPusher.set(DoubleSolenoid.Value.kReverse);
    }
    public void stop()
    {
    	BallPusher.set(DoubleSolenoid.Value.kOff);
    }
}

