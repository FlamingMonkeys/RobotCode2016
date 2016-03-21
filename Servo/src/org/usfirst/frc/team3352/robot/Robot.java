package org.usfirst.frc.team3352.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.ColorMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	int autoLoopCounter;
	Servo camAngle;
	double i;
	int imaqError;
    int numParticles;
    final double AREA_MINIMUM = 1;
    Image frame;
    Image binaryFrame;
    Image filteredFrame;
    NIVision.Range hue = new NIVision.Range(74, 204);
    NIVision.Range saturation = new NIVision.Range(2, 123);
    NIVision.Range luminance = new NIVision.Range(209, 254);
    NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
    NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	camAngle = new Servo(0);
    	i = 77.5; //36 degrees (.21) straight ahead, 119 degrees straight down
    	frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		filteredFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, .44, .5, 0, 0);
    }
    
    /**
     * This function is run once each time the robot enters autonomous mode
     */
    public void autonomousInit() {
    	autoLoopCounter = 0;
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
		
    }
    
    /**
     * This function is called once each time the robot enters tele-operated mode
     */
    public void teleopInit(){
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	NIVision.imaqReadFile(frame, "/home/lvuser/SampleImages/image.jpg");
    	NIVision.imaqColorThreshold(binaryFrame, frame, 255, ColorMode.HSL, hue, saturation, luminance);
    	
    	//Send particle count to dashboard
		int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Masked particles", numParticles);
    	
    	//filter out small particles
		imaqError = NIVision.imaqParticleFilter4(filteredFrame, binaryFrame, criteria, filterOptions, null);
		CameraServer.getInstance().setImage(binaryFrame);
    	
    	//Send particle count after filtering to dashboard
		numParticles = NIVision.imaqCountParticles(filteredFrame, 1);
		SmartDashboard.putNumber("Filtered particles", numParticles);
		
		
        camAngle.setAngle(i);
        Timer.delay(.1);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	LiveWindow.run();
    }
    
}
