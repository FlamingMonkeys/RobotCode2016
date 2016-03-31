package org.usfirst.frc3352.RobotCode2016.subsystems;

import org.usfirst.frc3352.RobotCode2016.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterWheels extends Subsystem {
	

    private final CANTalon shooter = RobotMap.shootershooter;
    private final CANTalon shooter2 = RobotMap.shootershooter2;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
    public void setWheelSpeed(double speed){
    	shooter.set(-speed);
    	shooter2.set(speed);
    }
}

