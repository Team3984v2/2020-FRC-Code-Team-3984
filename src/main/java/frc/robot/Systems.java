package frc.robot;

import java.util.Timer;
import java.util.TimerTask;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.util.Color;

public class Systems
{


    public void setEncZero(XboxController controller, WPI_TalonSRX talon) 
    {
        

        if (controller.getRawButton(1) == true ) 
        {
            talon.setSelectedSensorPosition(0);
        }
    }

    /**
     * This is a dead band. Input ranges between -.05 to .05 will be returned as 0.
     * @param rawNum The double value
     * @return Returns the raw value if it isn't in the Deadband range
     */
    public static double deadband(double rawNum){
        if (Math.abs(rawNum) < .05){
            return 0;
        }else{
            return rawNum;
        }
    }

    /**
     * InnerSystems
     */
    static class InnerSystems {
    
        public void configTalon(WPI_TalonSRX lMaster, WPI_TalonSRX rMaster,WPI_TalonSRX lSlave,WPI_TalonSRX rSlave){
            lMaster.configFactoryDefault();
            lSlave.configFactoryDefault();
            rMaster.configFactoryDefault();
            rSlave.configFactoryDefault();
            
            lMaster.configNominalOutputForward(0);
            lMaster.configNominalOutputReverse(0);
            lMaster.configPeakOutputForward(1);
            lMaster.configPeakOutputReverse(-1);
            lMaster.setNeutralMode(NeutralMode.Coast);
        
                
            lSlave.configNominalOutputForward(0);
            lSlave.configNominalOutputReverse(0);
            lSlave.configPeakOutputForward(1);
            lSlave.configPeakOutputReverse(-1);
            lSlave.setNeutralMode(NeutralMode.Coast);
        
                
            rMaster.configNominalOutputForward(0);
            rMaster.configNominalOutputReverse(0);
            rMaster.configPeakOutputForward(1);
            rMaster.configPeakOutputReverse(-1);
            rMaster.setNeutralMode(NeutralMode.Coast);
        
                
            rSlave.configNominalOutputForward(0);
            rSlave.configNominalOutputReverse(0);
            rSlave.configPeakOutputForward(1);
            rSlave.configPeakOutputReverse(-1);
            rSlave.setNeutralMode(NeutralMode.Coast);
        }
        //used to get the color from the game
        public int gameData_Color()
        {
            String gameData = DriverStation.getInstance().getGameSpecificMessage();
            int a;
            if(gameData.length() > 0){
                if(gameData.charAt(0) == 'B')
                    {
                        a = 1;
                    }
                else if(gameData.charAt(0) == 'G')
                    {
                        a = 2;
                    }
                else if(gameData.charAt(0) == 'R')
                    {
                        a = 3;
                    }
                else if(gameData.charAt(0) == 'Y')
                    {
                        a = 4;
                    }
                else
                    {
                        a = 0;
                    }
            }else{
                a = 0;
            }
            return a;
        }


        
        
    }


    /**
     * This method, if state == true, will cube the raw value. This is ment to be used for joystick/Trigger
     * Axises wich vary from -1 to 1.
     * @param rawValue The input or raw value of the Axis
     * @param state Weather (true) or not (false) to run the method
     * @return Returns the cubed double value
     */
    public static double cubeDrive(double rawValue, boolean state)
    {
        if(state == true)
        {
            if (rawValue > 0)
                {
                    double rV2 = Math.pow(rawValue,3);
                    return rV2;
                }
            else if (rawValue < 0)
                {
                    double rV3 = (Math.pow(rawValue, 3));
                    return rV3;
                }
            else
                {
                    return 0;
                }
        }
        else
        {
            return rawValue;
        }
        
    }
    /**
     * This Method is used as an arcade dive for four WPI_TalsonSRX Motor controllers. Also inverts the control if needed
     * @param rMaster Right Front Motor
     * @param lMaster Left Front Motor
     * @param rSlave Right Back Motor (make sure that you've set it to follow RMaster in roboInit)
     * @param lSlave Left Back Motor (make sure that you've set it to follow lMaster in roboInit)
     * @param teemo Xbox Controller
     * @param inverse If true, the robot orientation is flipped
     */
    public void driveTeleop(WPI_TalonSRX rMaster, WPI_TalonSRX lMaster, WPI_TalonSRX rSlave, WPI_TalonSRX lSlave, XboxController teemo, boolean inverse) {

        double invert = 1;
        if (inverse == true)
            invert = invert*-1;

        Double speed = (cubeDrive(deadband(-teemo.getX(Hand.kRight)),true)*invert); 
        Double turn = (cubeDrive(deadband(teemo.getY(Hand.kLeft)),true)*invert);
       //teemo is the xbox thing, remember :)
        Double left = speed + turn;
        Double right = speed - turn;
       
        lMaster.set(left);
       // lSlave.set(left*invert);
        rMaster.set(right);
       // rSlave.set(right*invert);

    
    }

    /**
     * The Method is ment to control the entire intake and shooting system.
     * @param x Xbox controller
     * @param l Left cannon spark
     * @param r Right cannon spark
     * @param b Belt motor (controls intake)
     * 
     */
    public void cannon(XboxController x, Spark l, Spark r, Spark b)
    {
        if (x.getTriggerAxis(Hand.kRight) > .5)
        {
            if(x.getBumper(Hand.kRight) == true)
            {
                l.set(1);
                r.set(1);
                b.set(.25);
            }
            else
            {
                l.set(1);
                r.set(1);
                b.set(0);
            }
        }
        else
        {
            if(x.getBumper(Hand.kRight) == true)
            {
                l.set(0);
                r.set(0);
                b.set(.25);
            }
            else
            {
                l.set(0);
                r.set(0);
                b.set(0);
            }
        }
        
    }

    /**
     * dispalay
     * @param leftV disc
     * 
     * 
     * 
     */
    public void leftrealign(WPI_TalonSRX leftTalonSRX, WPI_TalonSRX rightTalonSRX, int leftV)
    {
        leftV = leftTalonSRX.getSelectedSensorVelocity();
        if (leftV != rightTalonSRX.getSelectedSensorVelocity())
        {
            leftTalonSRX.set(0);
        }
        else
        {

        }
    }
    /**
     * 
     * @param rightV the velocity of the right motor controller
     */
    public void rightrealight(WPI_TalonSRX leftTalon, WPI_TalonSRX rightTalon, int rightV)
    {
        rightV = rightTalon.getSelectedSensorVelocity();    
        if (rightV != leftTalon.getSelectedSensorVelocity());
        {

        }
    }

    public void soloSolControl(XboxController x, Solenoid sole){
        boolean init = false;
        if ( x.getPOV() == 0){
            sole.set(true);
        }if (x.getPOV() == 180) {
            sole.set(false);
        }
        
    }


    /**
     * This method enables the "A" and "B" buttons on the Xbox controller to control the solenoids refrenced.
     * NOTE: this is set up for two "DoubleSolenoid"s, not two "Solenoid"s
     * @param x xbox controller
     * @param soleA this is a double solenoid
     * @param soleB this is lso a double solenoid
     * @deprecated use soloSolControl() instead
     */
    public void soleControl(XboxController x, DoubleSolenoid soleA, DoubleSolenoid soleB){
        if (x.getAButton() == true && x.getBButton() == false) 
        {
            soleA.set(Value.kForward);
            soleB.set(Value.kForward);
        }
        if (x.getBButton() == true && x.getAButton() == false)
        {
            soleA.set(Value.kReverse);
            soleB.set(Value.kReverse);
        }
        if (x.getAButtonReleased() == true || x.getBButtonReleased() == true || (x.getAButton() == true && x.getBButton() == true))
        {
            soleA.set(Value.kOff);
            soleB.set(Value.kOff);
        }else
        {
            soleA.set(Value.kOff);
            soleB.set(Value.kOff);
        }
    }

    @Deprecated /* use soleControl */
    public void solenoidsOut(Solenoid sole, Joystick controller)
    {
        if(controller.getRawButtonPressed(8) == true)
        {
            sole.set(true);
        
        }
        
        else
        {                
            sole.set(false);
     
        }
    }
    //to turn the intake for the cannon
    @Deprecated /* use cannon() */
    public void intake(Spark beltController, Spark lSpark, Spark rSpark, XboxController joystick)
    {
        if(joystick.getBumper(Hand.kRight) == true && joystick.getTriggerAxis(Hand.kRight) < .4)
        {
            beltController.set(.25);
            lSpark.set(-.01);
            rSpark.set(-.01);
        }
        else if (joystick.getBumper(Hand.kRight) == true && !(joystick.getTriggerAxis(Hand.kRight) < .4))
        {
            beltController.set(.25);
        }else{
            beltController.set(0);
        }
    }
    @Deprecated /* use cannon() */
    public void activate(XboxController joystick, Spark leftoutSpark, Spark rightoutSpark)
    {
        if(deadband(joystick.getTriggerAxis(Hand.kRight)) > .5)
        {
            leftoutSpark.set(1);
            rightoutSpark.set(1);
        }
        else
        {
            leftoutSpark.set(0);
            rightoutSpark.set(0);
        }

      //  spin();

       
    }

    static class ColorSys
    {
    
        public static Timer timer = new Timer();

        public void cwMovement(ColorSensorV3 sensor, Spark cwMotor, Double speed, Color initColor,TimerTask sensorGetColor, int desiredRotations){
            int a = 0;
            cwMotor.set(speed);
            timer.schedule(sensorGetColor, 750); //.75 seconds
            
            
        }
        //public void colorStop(ColorSensorV3 cV3, String gdString)
        {
            
        }
    }
}

