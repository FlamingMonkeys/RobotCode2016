package org.usfirst.frc3352.RobotCode2016.subsystems;

import org.usfirst.frc3352.RobotCode2016.Robot;
import org.usfirst.frc3352.RobotCode2016.RobotMap;
import org.usfirst.frc3352.RobotCode2016.commands.AnglePID;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterAngle extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private final CANTalon angle = RobotMap.shooterangle;
    private final AnalogGyro gyro = RobotMap.shooterGyro;
    private final AnalogInput pot = RobotMap.pot;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new AnglePID());
    }
    public void setAngle(double angle){
    	SmartDashboard.putNumber("shooter output", angle);
    	this.angle.set(angle); //invert to correct direction on practice bot
    }

    public double getAngle(){
    	return ((5-pot.getAverageVoltage())*54)-0;//30 for practice bot
    }

    public void resetGyro(){
    	gyro.reset();
    }
}

