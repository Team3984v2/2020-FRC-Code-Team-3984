package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
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
    public class InnerSystems {
    
        
        
    }


    public void driveSenpai (WPI_TalonSRX rMaster, WPI_TalonSRX lMaster, WPI_TalonSRX rSlave, WPI_TalonSRX lSlave, XboxController teemo, boolean inverse) {

        double invert = 1;
        if (inverse = true){
            invert = invert*-1;
        }else if (inverse = false){
            invert = invert*1;
        }
        Double speedFam = teemo.getRawAxis(1); 
        Double turnPop = teemo.getRawAxis(4);

        Double left = speedFam + turnPop;
        Double right = speedFam - turnPop;
       
        lMaster.set(left*invert);
        lSlave.set(left*invert);
        rMaster.set(-right);
        rSlave.set(-right);



    
    }


    public static void spin(Joystick buttonthing, Spark soloSpark){

    }

    public void activate(Joystick joystick, Spark leftoutSpark, Spark rightoutSpark)
    {
        if(joystick.getRawButtonPressed(1) == true)
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
}