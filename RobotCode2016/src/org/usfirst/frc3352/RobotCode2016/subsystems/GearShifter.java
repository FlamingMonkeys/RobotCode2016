package org.usfirst.frc3352.RobotCode2016.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc3352.RobotCode2016.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
/**
 *
 */
public class GearShifter extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private final DoubleSolenoid Shifter = RobotMap.gearshift;
	
	
	
	public void ShiftUp()
	{
		Shifter.set(Value.kForward);
	}
	
	public void ShiftDown()
	{
		Shifter.set(Value.kReverse);
	}
	
	public Value ShiftState()
	{
		return Shifter.get();	
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}	

