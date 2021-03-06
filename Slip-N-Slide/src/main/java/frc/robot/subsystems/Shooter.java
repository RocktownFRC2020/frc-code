/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.PortMap;
import frc.robot.Robot;
import frc.robot.commands.AlignToTarget;
import frc.robot.commands.RevShooter;
public class Shooter extends SubsystemBase {
  private static TalonSRX shooter1 = new TalonSRX(PortMap.SHOOTER1_PORT);
  private VictorSPX shooter2 = new VictorSPX(PortMap.SHOOTER2_PORT);

  private int kTimeoutMs = Constants.KTIMEOUTMS;
  private int edgesPerCycle = Constants.SHOOTER_STRIPES;
  private static double prevRPM = 0;
  private boolean isRunning=false;
  //private final AlignToTarget m_alignToTarget = new AlignToTarget();

  /**
   * Creates a new Shooter.
   */
  public Shooter() {
    shooter1.configFactoryDefault();

    shooter1.configSelectedFeedbackSensor(FeedbackDevice.Tachometer, 0, kTimeoutMs);
    shooter1.configPulseWidthPeriod_EdgesPerRot(edgesPerCycle, kTimeoutMs);
    shooter2.configFactoryDefault();

    shooter2.configSelectedFeedbackSensor(FeedbackDevice.Tachometer, 0, kTimeoutMs);
    shooter2.configPulseWidthPeriod_EdgesPerRot(edgesPerCycle, kTimeoutMs);

  }


  public static double getTachVel() {
    double tachVel = shooter1.getSelectedSensorVelocity(0);
    return tachVel;

  }
  public void changeRunning() {
    this.isRunning = !this.isRunning;
  }
  public boolean getIsRunning(){
    return isRunning;
  }
  public void setRPM(){
    SmartDashboard.putNumber("Shooter RPM", prevRPM);
    SmartDashboard.putNumber("RPM Graph_", prevRPM);
    if(getRPM()>0){
      prevRPM=getRPM();
    }
  }

  public static double getRPM() {
    double tachVel_UnitsPer100ms = shooter1.getSelectedSensorVelocity(0);

    double tachRPM = -tachVel_UnitsPer100ms * 600 / 1024;
    if(tachRPM>10000){
      return(prevRPM);
    }
    return tachRPM;
  }

  public void shooterSpeed(double speed) {
    shooter1.set(ControlMode.PercentOutput, -speed);
    shooter2.set(ControlMode.PercentOutput, speed);
  }

  public void pidControl() {
    double designatedRPM = Constants.OPTIMUMRPM;
    double shooterRPM = getRPM();

    double pError = designatedRPM - shooterRPM;
    double dError = shooterRPM - prevRPM;

  

    SmartDashboard.putNumber("pError", pError);
    SmartDashboard.putNumber("dError", dError);

    double pFactor = pError * Constants.PID_P;
    double dFactor = dError * Constants.PID_D;

    
    SmartDashboard.putNumber("pFactor", pFactor);
    SmartDashboard.putNumber("dFactor", dFactor);

    double shooterPIDSpeed = pFactor + dFactor;

    SmartDashboard.putNumber("Shooter %", shooterPIDSpeed);
    if (shooterPIDSpeed > 1) {
      shooterPIDSpeed = 1;
    }

    shooterSpeed(shooterPIDSpeed);

    prevRPM = shooterRPM;
  }


  @Override
  public void periodic() {
    if(Robot.m_oi.isButtonPressed(PortMap.XBOX_BY, true)){
     // m_alignToTarget.schedule();
    }
    setDefaultCommand(new RevShooter());
  }
}
