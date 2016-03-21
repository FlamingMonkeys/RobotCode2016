package org.usfirst.frc.team3352.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.ColorMode;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.ShapeMode;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.AxisCamera;

/**
 * This is a demo program showing the use of the NIVision class to do vision processing. 
 * The image is acquired from the USB Webcam, then a circle is overlayed on it. 
 * The NIVision class supplies dozens of methods for different types of processing. 
 * The resulting image can then be sent to the FRC PC Dashboard with setImage()
 */
public class Robot extends SampleRobot {
    int session;
    int imaqError;
    int numParticles;
    int particleIndex=0;
    final double AREA_MINIMUM = .5;
    final double FOV_ANGLE = 49.4;
    final double TARGET_HEIGHT = 90.0;
    final double TARGET_WIDTH = 20.0;
    final double FOV_PIXELS = 320.0;
    double fov_width;
    double distance;
    double area;
    double boundingRectBottom;
    double boundingRectTop;
    double boundingRectLeft;
    double boundingRectRight;
    double boundingArea;
    double pixelWidth;
    double x;
    double y;
    double pitchAngle;
    double yawAngle;
    double targetDistance;
    double equivalentRectLong;
    double equivalentRectShort;
    Image frame;
    Image binaryFrame;
    Image filteredFrame;
    NIVision.Range hue = new NIVision.Range(82, 159);
    NIVision.Range saturation = new NIVision.Range(2, 159);
    NIVision.Range luminance = new NIVision.Range(196, 254);
    NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
    NIVision.ParticleFilterCriteria2 criteria2[] = new NIVision.ParticleFilterCriteria2[1];
    NIVision.ParticleFilterCriteria2 criteria3[] = new NIVision.ParticleFilterCriteria2[1];
    NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(1,0,1,1);
    //AxisCamera camera;
    
    public void robotInit() {

        frame = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		filteredFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
        criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_RATIO_OF_EQUIVALENT_RECT_SIDES, 13.0, 60.0, 0, 0);
        criteria2[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH, 76.0, 300.0, 0, 0);
        criteria3[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, .6, 0, 0);
        //camera = new AxisCamera("10.33.52.22");
        SmartDashboard.putNumber("Area min %", .5);
    }

    public void operatorControl() {

        /**
         * grab an image, draw the circle, and provide it for the camera server
         * which will in turn send it to the dashboard.
         */
//	    NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);

        while (isOperatorControl() && isEnabled()) {

        	//camera.getImage(frame);
        	NIVision.imaqReadFile(frame, "/home/lvuser/SampleImages/image.jpg");
//        	NIVision.imaqDrawShapeOnImage(frame, frame, rect, DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
//	        try{
	        NIVision.imaqColorThreshold(binaryFrame, frame, 255, ColorMode.HSL, hue, saturation, luminance);
//	        }catch(Exception e){
//	        	System.out.println(e.getMessage());
//	        }

	      //filter out small particles
			float areaMin = (float)SmartDashboard.getNumber("Area min %", AREA_MINIMUM);
			criteria3[0].lower = areaMin;
			NIVision.imaqParticleFilter4(filteredFrame, binaryFrame, criteria3, filterOptions, null);
//	        NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);
//	        NIVision.imaqParticleFilter4(frame, frame, criteria2, filterOptions, null);
        	
        	//Send particle count after filtering to dashboard
			numParticles = NIVision.imaqCountParticles(filteredFrame, 1);
        	CameraServer.getInstance().setImage(binaryFrame);
			SmartDashboard.putNumber("Filtered particles", numParticles);
			
			area = NIVision.imaqMeasureParticle(filteredFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
			boundingRectTop = NIVision.imaqMeasureParticle(filteredFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
			boundingRectLeft = NIVision.imaqMeasureParticle(filteredFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
			boundingRectBottom = NIVision.imaqMeasureParticle(filteredFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
			boundingRectRight = NIVision.imaqMeasureParticle(filteredFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
			equivalentRectLong = NIVision.imaqMeasureParticle(filteredFrame, particleIndex, 0, NIVision.MeasurementType.MT_EQUIVALENT_RECT_LONG_SIDE);
			equivalentRectShort = NIVision.imaqMeasureParticle(filteredFrame, particleIndex, 0, NIVision.MeasurementType.MT_EQUIVALENT_RECT_SHORT_SIDE);
			
			SmartDashboard.putNumber("area", area);
			boundingArea = (boundingRectBottom-boundingRectTop)*(boundingRectRight-boundingRectLeft);
			SmartDashboard.putNumber("boundingArea", boundingArea);
			SmartDashboard.putNumber("areaRatio",area/boundingArea);
			
			SmartDashboard.putNumber("equivalent width", equivalentRectLong);
			SmartDashboard.putNumber("equivalent height", equivalentRectShort);
			
			//pixelWidth=(TARGET_WIDTH/(boundingRectRight-boundingRectLeft));
			pixelWidth=(TARGET_WIDTH/(boundingRectRight-boundingRectLeft));
			fov_width = pixelWidth * FOV_PIXELS;
			
			x = (160-((boundingRectLeft+boundingRectRight)/2))*pixelWidth;
			y = (120-((boundingRectTop+boundingRectBottom)/2))*pixelWidth;
			
			distance = (fov_width)/Math.tan((FOV_ANGLE/2)*(Math.PI/180));
			
			yawAngle = Math.atan2(distance, x)*180/Math.PI;
			
			targetDistance = Math.sqrt((x*x)+(distance*distance));
			
			pitchAngle = Math.atan2(TARGET_HEIGHT-7.0, targetDistance)*180/Math.PI;
			
			SmartDashboard.putNumber("distance", distance);
			SmartDashboard.putNumber("Pitch Angle", pitchAngle);
			SmartDashboard.putNumber("Yaw Angle", yawAngle);
			SmartDashboard.putNumber("Pixel Width", pixelWidth);
			SmartDashboard.putNumber("X", x);
			SmartDashboard.putNumber("target pixels", boundingRectRight-boundingRectLeft);

            /** robot code here! **/
            Timer.delay(0.005);		// wait for a motor update time
        }
        
    }

    public void test() {
    }
}
