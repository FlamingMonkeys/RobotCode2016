// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc3352.RobotCode2016;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.AnalogGyro;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static CANTalon drivetrainrightDrive1;
    public static CANTalon drivetrainrightDrive2;
    public static CANTalon drivetrainleftDrive1;
    public static CANTalon drivetrainleftDrive2;
    public static RobotDrive drivetrainrobotDrive;
    public static Encoder drivetrainleftEncoder;
    public static Encoder drivetrainrightEncoder;
    public static CANTalon shooterangle;
    public static CANTalon shootershooter;
    public static DoubleSolenoid pushball;
    public static DoubleSolenoid gearshift;
	public static PIDController shooterPID;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static CANTalon shootershooter2;
	public static AnalogGyro shooterGyro;
	public static DigitalInput shooterUpperLimit;
	public static DigitalInput shooterLowerLimit;
	public static AHRS navx;
	public static PIDController turnController;
	public static PIDController distanceController;
	public static PIDController drivetrainLeftController;
	public static PIDController drivetrainRightController;
	public static Solenoid cameraLED;
	
    public static void init() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        drivetrainrightDrive1 = new CANTalon(1);
        
        drivetrainrightDrive2 = new CANTalon(2);
        
        drivetrainleftDrive1 = new CANTalon(4);
        
        drivetrainleftDrive2 = new CANTalon(5);
        
        drivetrainrobotDrive = new RobotDrive(drivetrainleftDrive1, drivetrainleftDrive2,
              drivetrainrightDrive1, drivetrainrightDrive2);
        
        drivetrainrobotDrive.setSafetyEnabled(true);
        drivetrainrobotDrive.setExpiration(0.1);
        drivetrainrobotDrive.setSensitivity(0.5);
        drivetrainrobotDrive.setMaxOutput(1.0);

        drivetrainleftEncoder = new Encoder(0, 1, false, EncodingType.k4X);
        drivetrainleftEncoder.setDistancePerPulse(.00437);
        drivetrainleftEncoder.setPIDSourceType(PIDSourceType.kRate);
        drivetrainrightEncoder = new Encoder(2, 3, false, EncodingType.k4X);
        drivetrainrightEncoder.setDistancePerPulse(.00437);
        drivetrainrightEncoder.setPIDSourceType(PIDSourceType.kRate);
        
        shooterangle = new CANTalon(7);
		
		shootershooter2 = new CANTalon(3);
        
        shootershooter = new CANTalon(8);
        
        pushball = new DoubleSolenoid(0, 1);
        
        gearshift = new DoubleSolenoid(2, 3);
        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
		shooterGyro = new AnalogGyro(0);
        
        shooterUpperLimit = new DigitalInput(4);//limit switch is hit when arm is straight up
        shooterLowerLimit = new DigitalInput(5);//limit switch is hit when arm is at bottom
        navx = new AHRS(SPI.Port.kMXP);
        
        cameraLED = new Solenoid(7);
        
        
     }
}
