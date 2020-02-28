package frc.robot;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NetTables
{
    public void leftTalonInfo(SmartDashboard dashboard, WPI_TalonSRX talonsrx, Faults faults)
    {
        SmartDashboard.putNumber("left Velocity:", talonsrx.getSelectedSensorVelocity());
        SmartDashboard.putNumber("left Position:", talonsrx.getSelectedSensorPosition());
        SmartDashboard.putNumber("left motor out %:", talonsrx.getSelectedSensorPosition());
        SmartDashboard.putBoolean("left Out of phase :", faults.SensorOutOfPhase);
    }

    public void rightTalonInfo(SmartDashboard dashboard, WPI_TalonSRX talonsrx, Faults faults)
    {
        SmartDashboard.putNumber("right Velocity:", talonsrx.getSelectedSensorVelocity());
        SmartDashboard.putNumber("right Position:", talonsrx.getSelectedSensorPosition());
        SmartDashboard.putNumber("right motor out %:", talonsrx.getSelectedSensorPosition());
        SmartDashboard.putBoolean("right Out of phase :", faults.SensorOutOfPhase);
    }

    
    

}