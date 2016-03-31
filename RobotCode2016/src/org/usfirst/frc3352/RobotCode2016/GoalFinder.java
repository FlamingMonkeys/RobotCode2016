package org.usfirst.frc3352.RobotCode2016;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

public class GoalFinder {
	
	static double altitude, azimuth, distance; //variables to return
	static double x, y, targetPixelWidth, targetPixelHeight, pixelWidth, pixelHeight, frameWidth, distanceToFrame, azimuthDistance; //values for calculation
	static double area, boundedArea, percentArea, bestPercent;
	static int particleCount, bestParticle;
	static double[] particleAnalysis; 
	static double[] results = new double[5];
	
	final static double TARGET_WIDTH = 20.0; //width of vision target in inches
	final static double TARGET_HEIGHT = 14.0; //height of vision target in inches
	final static double TARGET_ELEVATION = 90.0; //inches to center of vision target
	final static double FRAME_SIZE_WIDTH = 320.0;
	final static double FRAME_SIZE_HEIGHT = 240.0;
	final static double CAMERA_FOV = 22.3;//37.4 from documentation
	final static double CAMERA_X_OFFSET = 8.0; //inches from center of robot to camera
	final static double CAMERA_Y_OFFSET = 0.0; //inches from shooter axle to camera
	final static double CAMERA_ELEVATION_OFFSET = 10.5;
	

	public static double[] calculate(Image binaryFrame){
		particleCount = NIVision.imaqCountParticles(binaryFrame, 0);
		if(particleCount > 0){
			bestParticle = particleChooser(binaryFrame);
			targetPixelWidth = NIVision.imaqMeasureParticle(binaryFrame, bestParticle, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH);
			pixelWidth = TARGET_WIDTH/targetPixelWidth;
			frameWidth = pixelWidth*FRAME_SIZE_WIDTH;
			targetPixelHeight = NIVision.imaqMeasureParticle(binaryFrame, bestParticle, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
			pixelHeight = TARGET_HEIGHT/targetPixelHeight;
			
			distanceToFrame = (frameWidth/2)/Math.tan(Math.PI*CAMERA_FOV/(180*2));//convert to radian
			
			x = NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT) - targetPixelWidth/2; //compute center of target
			y = NIVision.imaqMeasureParticle(binaryFrame, 0, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM) - targetPixelHeight/2; //compute center of target
			x = pixelToCartesian(x,FRAME_SIZE_WIDTH, pixelWidth);
			y =-pixelToCartesian(y,FRAME_SIZE_HEIGHT, pixelHeight);
			
			azimuth = Math.atan2(x, distanceToFrame);
			azimuth = (azimuth/Math.PI)*180;
			azimuthDistance = Math.sqrt((x*x)+(distanceToFrame*distanceToFrame))/2;
			
			altitude = Math.atan2(TARGET_ELEVATION, azimuthDistance);
			altitude = (altitude/Math.PI)*180;
			distance = Math.sqrt((azimuthDistance*azimuthDistance)+((TARGET_ELEVATION-CAMERA_ELEVATION_OFFSET)*(TARGET_ELEVATION-CAMERA_ELEVATION_OFFSET)));
		}
		results[0] = altitude;
		results[1] = azimuth;
		results[2] = distance;
		results[3] = azimuthDistance;
		results[4] = x;
		
		return results;
	}

	private static double pixelToCartesian(double pixelLocation, double frameSize, double pixelSize){
		return (pixelLocation-(frameSize/2))*pixelSize;
	}
	
	private static int particleChooser(Image binaryFrame){
		if(particleCount >1){
			particleAnalysis = new double[particleCount];
			for(int i = 0; i<particleCount;i++){
				area = NIVision.imaqMeasureParticle(binaryFrame, i, 0, NIVision.MeasurementType.MT_AREA);
				boundedArea = NIVision.imaqMeasureParticle(binaryFrame, i, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH) * NIVision.imaqMeasureParticle(binaryFrame, i, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
				percentArea = area/boundedArea;
				if(percentArea>.2&&percentArea<.36){
					if(Math.abs(bestPercent-.28)>Math.abs(percentArea-.28)){
						bestPercent = percentArea;
						bestParticle = i;
					}
				}
			}
		}else{
			bestParticle = 0;
		}
		return bestParticle;
	}
}
