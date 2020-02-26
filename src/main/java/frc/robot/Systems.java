package frc.robot;

import java.util.Timer;
import java.util.TimerTask;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.XboxController;
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
    public static double deadband(double rawNum){
        if (rawNum == .05){
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
        public int gameData_Color(){
            String gameData = DriverStation.getInstance().getGameSpecificMessage();
            int a;
            if(gameData.length() > 0){
                if(gameData.charAt(0) == 'B'){
                    a = 1;
                }
                else if(gameData.charAt(0) == 'G'){
                    a = 2;
                }
                else if(gameData.charAt(0) == 'R'){
                    a = 3;
                }
                else if(gameData.charAt(0) == 'Y'){
                    a = 4;
                }else{
                    a = 0;
                }
            }else{
                a = 0;
            }
            return a;
        }


        
        
    }

    public static double quadDrive(double rawValue, boolean state){
        if(state == true){
            if (rawValue > 0){
                double rV2 = Math.pow(rawValue,2);
                return rV2;
            }else if (rawValue < 0){
                double rV3 = -(Math.pow(rawValue, 2));
                return rV3;
            }else{
                return 0;
            }
        }else{
            return rawValue;
        }
        
    }
    //used to drive the robot
    public void driveTeleop(WPI_TalonSRX rMaster, WPI_TalonSRX lMaster, WPI_TalonSRX rSlave, WPI_TalonSRX lSlave, XboxController teemo, boolean inverse) {

        double invert = 1;
        if (inverse = true)
            invert = invert*-1;

        Double speed = (quadDrive(deadband(-teemo.getX(Hand.kRight)),true)*invert); 
        Double turn = (quadDrive(deadband(teemo.getY(Hand.kLeft)),true)*invert);
       //teemo is the xbox thing, remember :)
        Double left = speed + turn;
        Double right = speed - turn;
       
        lMaster.set(left);
       // lSlave.set(left*invert);
        rMaster.set(right);
       // rSlave.set(right*invert);



    
    }


    public static void spin(Joystick buttonthing, Spark soloSpark){

    }

    //used to turn on the cannon
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
    //used to tilt the solenoids up
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
    public void intake(Spark beltController, Spark lSpark, Spark rSpark, XboxController joystick)
    {
        if(joystick.getBumper(Hand.kRight) == true && joystick.getTriggerAxis(Hand.kRight) < .4)
        {
            beltController.set(1);
            lSpark.set(-.01);
            rSpark.set(-.01);
        }
        else if (joystick.getBumper(Hand.kRight) == true && !(joystick.getTriggerAxis(Hand.kRight) < .4))
        {
            beltController.set(1);
        }else{
            beltController.set(0);
        }
    }
}

class cannon
{
    public static double deadband(double rawNum){
        if (rawNum == .5){
            return 0;
        }else{
            return rawNum;
        }
    }

    public void activate(Spark lSpark, Spark rSpark, XboxController controller)
    {
        if(deadband(controller.getTriggerAxis(Hand.kRight)) != 0)
        {
            lSpark.set(1);
            rSpark.set(1);
        }
        else
        {
            lSpark.set(0);
            rSpark.set(0);
        }
    }
}


class overcannon
{

}
////////////////////
