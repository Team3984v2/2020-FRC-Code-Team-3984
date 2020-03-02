package frc.robot;

import com.revrobotics.ColorSensorV3;



public class ColorStuff
{
    int rot;
    
    ColorStuff(int bob){
        rot = bob;
    }
    public int egg(ColorSensorV3 sensor)
    {
        sensor.getColor();
        return rot;
    }
}