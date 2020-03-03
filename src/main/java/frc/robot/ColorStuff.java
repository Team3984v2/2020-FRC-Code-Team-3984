package frc.robot;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;



public class ColorStuff
{
    edu.wpi.first.wpilibj.util.Color finalColor;
    edu.wpi.first.wpilibj.util.Color color;


    ColorStuff(final edu.wpi.first.wpilibj.util.Color seenColor)
    {
        finalColor = seenColor;
    }

    /**This method should read the color of the wheel.
     * 
     * @param sensor the color sensor reading the color
     * @param color the color that will be matched from the sensor
     * @param colormRes the result of the method that is closest to the color perceived
     * @return
     */
    public edu.wpi.first.wpilibj.util.Color Color(final ColorSensorV3 sensor, final ColorMatch color, final ColorMatchResult colormRes)
    {
        sensor.getColor();
      //  color.matchClosestColor(kRed);
        
        return sensor.getColor();
    }
}