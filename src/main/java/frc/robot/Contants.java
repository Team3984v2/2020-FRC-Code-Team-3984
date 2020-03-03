package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Contants {


     static class IO {

         static class XController{
            //Xbox controller
            public  XboxController m_drivexbcont = new XboxController(0);

            //Axises..Axi?...Axee?
            public  Double lXaxis = m_drivexbcont.getX(Hand.kLeft);
            public  Double lYaxis = m_drivexbcont.getY(Hand.kLeft);
            public  Double rXaxis = m_drivexbcont.getX(Hand.kRight);
            public  Double rYaxis = m_drivexbcont.getY(Hand.kRight);

            //Buttons
            public  Boolean xButton = m_drivexbcont.getXButton();
            public  Boolean aButton = m_drivexbcont.getAButton();
            public  Boolean bButton = m_drivexbcont.getBButton();
            public  Boolean yButton = m_drivexbcont.getYButton();

            //Triggers and bumpers
            public  Double lTrigger = m_drivexbcont.getTriggerAxis(Hand.kLeft);
            public  Double rTrigger = m_drivexbcont.getTriggerAxis(Hand.kRight);
            public  Boolean lBumper = m_drivexbcont.getBumper(Hand.kLeft); 
            public  Boolean rBumper = m_drivexbcont.getBumper(Hand.kRight);
        }

         static class ButtonBoard{
            public  final Joystick m_buttonboard = new Joystick(1);

            //More buttons than you need
            public  Boolean button1(){
                return m_buttonboard.getRawButton(1);
            } 
            public  Boolean button2 = m_buttonboard.getRawButton(2);
            public  Boolean button3 = m_buttonboard.getRawButton(3);
            public  Boolean button4 = m_buttonboard.getRawButton(4);
            public  Boolean button5 = m_buttonboard.getRawButton(5);
            public  Boolean button6 = m_buttonboard.getRawButton(6);
            public  Boolean button7 = m_buttonboard.getRawButton(7);

        }
        
    }

     static class Objects {
        
        //Talons
        public  final WPI_TalonSRX lMaster = new WPI_TalonSRX(1);
        public  final WPI_TalonSRX lSlave = new WPI_TalonSRX(2);
        public  final WPI_TalonSRX rMaster = new WPI_TalonSRX(4);
        public  final WPI_TalonSRX rSlave = new WPI_TalonSRX(3);
        public  final DifferentialDrive m_autoDrive = new DifferentialDrive(lMaster, rMaster);
        //Sparks
        public  final Spark lBallSpark = new Spark(0);  //cannon intake motor 1
        public  final Spark rBallSpark = new Spark(1);  //cannon intake motor 2
        public  final Spark intakeSpark = new Spark(2); //ball intake motor
        public final Spark cwSpark = new Spark(3);     //color wheel motor

        //Solenoids
       //public final Solenoid soleSole = new Solenoid(0);
       public final Solenoid soleA = new Solenoid(0);
       public final DoubleSolenoid soleB = new DoubleSolenoid(2,3);

       //Color Sensor
       public final ColorSensorV3 colorsensor = new ColorSensorV3(I2C.Port.kOnboard);
    }

}