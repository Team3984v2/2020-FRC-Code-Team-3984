/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Timer;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {


  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private static final String kOption1 = "Option 1";

  private String m_autoSelected;
  private SendableChooser<String> m_chooser = new SendableChooser<>();


  Timer timer = new Timer();

  
  //Setting up classes:
  public static Systems systems = new Systems();
  public static Systems.InnerSystems innerSystems = new Systems.InnerSystems();


  //public static Systems ballIntake = new Systems();
  
   //variables and constants
   private final I2C.Port i2cPort = I2C.Port.kOnboard;
   private boolean rdIndicator = false;
   private double chachaslide = -1;


   //private int ball_counter = 0;   

  //OI
  private final XboxController m_drivecont = new XboxController(0);
  private final Joystick m_buttonboard = new Joystick(1);

  private final Faults faults = new Faults();

  //drivetrain
  private final WPI_TalonSRX lMaster = new WPI_TalonSRX(9);
  private final WPI_TalonSRX lSlave = new WPI_TalonSRX(2);
  private final WPI_TalonSRX rMaster = new WPI_TalonSRX(10);
  private final WPI_TalonSRX rSlave = new WPI_TalonSRX(3);
  private final DifferentialDrive m_drive = new DifferentialDrive(lMaster, rMaster);

  //cannon intake motors
  private final Spark lBallSpark = new Spark(0);
  private final Spark rBallSpark = new Spark(1);

  //ball intake motor
  private final Spark intakeSpark = new Spark(2);
  
  //color wheel motor
  private final Spark cwSpark = new Spark(3);

  //DigitalInput ball sensor = new DigitalInput(0); 
  // private final Faults _faults = new Faults();
  
  
//Yay
  //private final Encoder m_encoder = new Encoder(0, 1, false, Encoder.EncodingType.k2X);

  
  //color sensing
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  private final ColorMatch m_colorMatcher = new ColorMatch();
//UwU
  //these colors need to be calibrated
  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);







  @Override
  public void robotInit() {
    UsbCamera m_usbCamera = new UsbCamera("USB Camera 0", 0);
    UsbCamera m_cameraserv = CameraServer.getInstance().startAutomaticCapture(0);
    m_cameraserv.setVideoMode(VideoMode.PixelFormat.kYUYV, 604, 480, 30);

    m_chooser.setDefaultOption("Option 1:", kOption1);
    m_chooser.addOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  
   //Config Talons
    innerSystems.configTalon(lMaster, rMaster, lSlave, rSlave);
    lSlave.follow(lMaster);
    rSlave.follow(rMaster);

    lMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    lMaster.setSelectedSensorPosition(0);
    //rMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    //m_encoder.setDistancePerPulse(1./256.);

    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);   

    rdIndicator = m_buttonboard.getRawButtonPressed(1);




  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    //color detection code / color matching
    Color detectedColor = m_colorSensor.getColor();

    String colorString;
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    if (match.color == kBlueTarget) {
      colorString = "Blue";
    } else if (match.color == kRedTarget) {
      colorString = "Red";
    } else if (match.color == kGreenTarget) {
      colorString = "Green";
    } else if (match.color == kYellowTarget) {
      colorString = "Yellow";
    } else {
      colorString = "Unknown";
    }

    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);

    //read RGB values
    double IR = m_colorSensor.getIR();
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("IR", IR);

    //encoder values
    SmartDashboard.putNumber("left Vel:", lMaster.getSelectedSensorVelocity());
    SmartDashboard.putNumber("left Pos:", lMaster.getSelectedSensorPosition());
    SmartDashboard.putNumber("left out %:", lMaster.getMotorOutputPercent());
    SmartDashboard.putBoolean("Out of Phase", faults.SensorOutOfPhase);

    //joystick values



    //Hello

    //if(ball_sensor.get() == true)
    /*{
      ball_counter = ball_counter + 1;
      System.out.println("Ball in!" + "Total ball count: " + ball_counter);
    }
    */

  }  
  /**
   * This autonomous (along with the chooser code above) shows how to select
   /* between different autonomous modes using the dashboard. The sendable
      * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

 
    switch (m_autoSelected) 
    {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:

        break;
      case kOption1:
       
        break;
      default:
        // Put default auto code her



          }
    }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {


    //the color of the wheel; B = 1; G = 2; R = 3; Y = 4; none = 0;
    int gameColor = innerSystems.gameData_Color();
    lMaster.getFaults(faults);

    rdIndicator = m_buttonboard.getRawButton(1);
    if(rdIndicator == true)
    {
      systems.driveTeleop(rMaster, lMaster, rSlave, lSlave, m_drivecont,true); 
    }
    else
    {
      systems.driveTeleop(rMaster, lMaster, rSlave, lSlave, m_drivecont,false);
    }

    systems.activate(m_buttonboard, lBallSpark, rBallSpark);
  }
    
  @Override
  public void testPeriodic() {

    //System.out.println(m_controller.getYChannel());

    
  }
}
