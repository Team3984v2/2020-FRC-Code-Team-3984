package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.XboxController;

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
     * InnerSystems
     */
    static class InnerSystems {
    
        public void configTalon(WPI_TalonSRX lMaster, WPI_TalonSRX rMaster,WPI_TalonSRX lSlave,WPI_TalonSRX rSlave){
            lMaster.configFactoryDefault();
            lSlave.configFactoryDefault();
            rMaster.configFactoryDefault();
            rSlave.configFactoryDefault();
            
            lMaster.configNominalOutputForward(0, 30);
            lMaster.configNominalOutputReverse(0, 30);
            lMaster.configPeakOutputForward(1, 30);
            lMaster.configPeakOutputReverse(-1, 30);
            lMaster.setNeutralMode(NeutralMode.Brake);
        
                
            lSlave.configNominalOutputForward(0, 30);
            lSlave.configNominalOutputReverse(0, 30);
            lSlave.configPeakOutputForward(1, 30);
            lSlave.configPeakOutputReverse(-1, 30);
            lSlave.setNeutralMode(NeutralMode.Brake);
        
                
            rMaster.configNominalOutputForward(0, 30);
            rMaster.configNominalOutputReverse(0, 30);
            rMaster.configPeakOutputForward(1, 30);
            rMaster.configPeakOutputReverse(-1, 30);
            rMaster.setNeutralMode(NeutralMode.Brake);
        
                
            rSlave.configNominalOutputForward(0, 30);
            rSlave.configNominalOutputReverse(0, 30);
            rSlave.configPeakOutputForward(1, 30);
            rSlave.configPeakOutputReverse(-1, 30);
            rSlave.setNeutralMode(NeutralMode.Brake);
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

    //used to drive the robot
    public void driveTeleop(WPI_TalonSRX rMaster, WPI_TalonSRX lMaster, WPI_TalonSRX rSlave, WPI_TalonSRX lSlave, XboxController teemo, boolean inverse) {

        double invert = 1;
        if (inverse = true)
            invert = invert*-1;

        Double speed = teemo.getRawAxis(1); 
        Double turn = teemo.getRawAxis(4);
       //teemo is the xbox thing, remember :)
        Double left = speed + turn;
        Double right = speed - turn;
       
        lMaster.set(left*invert);
       // lSlave.set(left*invert);
        rMaster.set(right*invert);
       // rSlave.set(right*invert);



    
    }


    public static void spin(Joystick buttonthing, Spark soloSpark){

    }

    //used to turn on the cannon
    public void activate(Joystick joystick, Spark leftoutSpark, Spark rightoutSpark)
    {
        if(joystick.getRawButtonPressed(1) == true)
        {
            leftoutSpark.set(1);
            rightoutSpark.set(1);
        }
        else
        {
            leftoutSpark.stopMotor();
            rightoutSpark.stopMotor();
        }

      //  spin();

       
    }

    static class ColorSys
    {
        public void colorStop(ColorSensorV3 cV3, String gdString)
        {
            
        }
    }
    //used to tilt the solenoids up
    public void solenoidsOut(Solenoid sole, Solenoid sole2, Joystick controller)
    {
        if(controller.getRawButtonPressed(8) == true)
        {
                sole.set(true);
        }
        
        else
        {                
            sole.set(false);
            sole2.set(false);
        }
    }

    //to turn the intake for the cannon
    public void intake(SpeedController beltController, Joystick joystick)
    {
        if(joystick.getRawButtonPressed(4) == true)
        {
            beltController.set(1);
        }
        else
        {
            beltController.set(0);
        }
    }
}