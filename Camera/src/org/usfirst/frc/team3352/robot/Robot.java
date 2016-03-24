
package org.usfirst.frc.team3352.robot;


import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import com.ni.vision.NIVision;

/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot {
    AxisCamera cam;
    Image frame;
    Image binaryFrame;

    public Robot() {
    	
    }
    
    public void robotInit() {
    	cam = new AxisCamera("axis-camera.local");
    	frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
    	binaryFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_U8, 0);
    }
    
    public void disabled(){
    	cam.getImage(frame);
    	
    	CameraServer.getInstance().setImage(frame);
    }

    public void autonomous() {
    	
    }

    public void operatorControl() {
        
    }

    public void test() {
    	
    }
}
