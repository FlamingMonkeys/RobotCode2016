package org.usfirst.frc3352.RobotCode2016.commands;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc3352.RobotCode2016.GoalFinder;
import org.usfirst.frc3352.RobotCode2016.Robot;

/**
 *
 */
public class AimAtGoal extends Command {
	
	Image frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    Image binaryFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
    
    NIVision.ParticleFilterCriteria2 criteria3[] = new NIVision.ParticleFilterCriteria2[1];
    NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);

    NIVision.Range hue = new NIVision.Range(76, 104);
    NIVision.Range saturation = new NIVision.Range(95, 255);
    NIVision.Range luminance = new NIVision.Range(16, 62);
    
    final NIVision.Rect rect = new NIVision.Rect(10, 0, 100, 100);
    
    int session;
    public static double[] results = new double[5];
    
    int i;

    public AimAtGoal(Image frame) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.frame = frame;

	    criteria3[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, .2, 3, 0, 0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putBoolean("running", true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {	
//    	NIVision.imaqReadFile(frame, "/home/lvuser/SampleImages/image.jpg");

	    NIVision.imaqColorThreshold(binaryFrame, frame, 255, NIVision.ColorMode.HSL, hue, saturation, luminance);
        
//	    CameraServer.getInstance().setImage(binaryFrame);
        
		NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria3, filterOptions, null);
		
		results = GoalFinder.calculate(binaryFrame);
		frame = Robot.frame;
		SmartDashboard.putNumber("particle", NIVision.imaqCountParticles(binaryFrame, 1));
		SmartDashboard.putNumber("altitude", results[0]);
		SmartDashboard.putNumber("azimuth", results[1]);
		SmartDashboard.putNumber("distance", results[2]);
		SmartDashboard.putNumber("azimuth distance", results[3]);
		SmartDashboard.putNumber("x", results[4]);
		i++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return i>50||results[2]<300;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.results = results;
    	Robot.isVisionControl = true;
    	SmartDashboard.putBoolean("running", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
