package org.usfirst.frc3352.RobotCode2016.commands;

import org.usfirst.frc3352.RobotCode2016.Robot;
import org.usfirst.frc3352.RobotCode2016.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AnglePID extends Command {
	boolean direction;
	static double target, lastTarget, setpoint;
	double angle;
	double error;
	final double kRampLimit = .01;
	double delta;
	double p, i, l, output;
	double integral;
   	int inTargetCounter;
   	int loopCounter;
	
	final double kP = 2,
				 kI = .12,
				 kLeadInput = -.25,
				 kLeadI = -.415,
				 kOutput = 1.9;
	

    public AnglePID() {
    	requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	angle = Robot.shooter.getAngle();
    	angle = angle/115;
    	Robot.shooter.resetGyro();
        lastTarget = Robot.shooter.getAngle()/115;
        
        if(Robot.wasDisabled){
        	integral = 0;
        	l = 0;
        	target = angle;
        	Robot.wasDisabled = false;
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.isAuto){
    		target = Robot.autoAltitude/115;
    	}else if(Robot.isVisionControl){
    		target = Robot.results[0]/115;
    	}else{
    		target += Robot.oi.getTarget()/115;
    	}
    	if(target>1.5) target = 1.5;
    	if(target<-.5) target = -.5;
    	
    	if(Robot.oi.operatorJoy.getRawButton(6)) target = (90.0/115.0);

    	SmartDashboard.putNumber("preramp setpoint", setpoint);
    	SmartDashboard.putNumber("preramp target", target);
    	SmartDashboard.putNumber("preramp lasttarget", lastTarget);
    	delta = Math.abs(target - lastTarget);
    	if(delta>kRampLimit){
    		if(target>lastTarget){
    			setpoint = lastTarget + kRampLimit;
    		}else{
    			setpoint = lastTarget - kRampLimit;
    		}
    	}else{
    		setpoint = target;
    	}
    	SmartDashboard.putNumber("delta", delta);
    	SmartDashboard.putNumber("postramp setpoint", setpoint);
    	SmartDashboard.putNumber("postramp target", target);
    	SmartDashboard.putNumber("postramp lasttarget", lastTarget);
    	
    	angle = Robot.shooter.getAngle()/115;
    	
    	error = -(setpoint - angle);
    	
    	if(Math.abs(error)<.005) error = 0;
    	
    	p = error*kP;
    	
    	integral += kI*error;
    	if(integral<-1) integral = -1; 
    	if(integral>1) integral = 1;
    	
    	if(Robot.oi.operatorJoy.getRawButton(4)) integral = 0;
    	
    	l = kLeadInput*error + kLeadI*l;
    	
    	output = p + integral + l;
    	output *= kOutput;
    	
    	if(RobotMap.shooterUpperLimit.get()&&error<0){
    		Robot.shooter.setAngle(-.3);
    		SmartDashboard.putNumber("output", -.3);
    	}else{
    		Robot.shooter.setAngle(output);
    		SmartDashboard.putNumber("output", output);
    	}
    	
    	SmartDashboard.putBoolean("limit", RobotMap.shooterUpperLimit.get());
		SmartDashboard.putNumber("integral", integral);
		SmartDashboard.putNumber("error", error*115);
		SmartDashboard.putNumber("angle", angle*115);
		SmartDashboard.putNumber("setpoint", target*115);
		SmartDashboard.putNumber("lead lag", l);
		SmartDashboard.putNumber("target", target);
		SmartDashboard.putNumber("loop counter", loopCounter);
    	
       	lastTarget = setpoint;
       	
		if(Robot.isAuto||Robot.isVisionControl){
			loopCounter++;
			if(error == 0){
				inTargetCounter++;
			}else{
       		inTargetCounter = 0;
			}
       	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return inTargetCounter>4||loopCounter>200;
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Robot.shooter.setAngle(.2);
    	Robot.isAuto = false;
    	Robot.isVisionControl = false;
    	inTargetCounter = 0;
    	loopCounter = 0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
