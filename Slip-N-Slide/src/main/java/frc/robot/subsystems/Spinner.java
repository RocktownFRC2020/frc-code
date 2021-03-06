/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.PortMap;
import frc.robot.commands.SpinSpinner;

public class Spinner extends SubsystemBase {

  private VictorSPX spinnerMotor = new VictorSPX(PortMap.SPINNER_PORT);

  /**
   * Creates a new Spinner.
   */
  public Spinner() {

  }


  public void engageSpinner(){
    spinnerMotor.set(ControlMode.PercentOutput, -Constants.SPINNER_SPEED);
  }

  public void disengageSpinner(){
    spinnerMotor.set(ControlMode.PercentOutput, 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    setDefaultCommand(new SpinSpinner());
  }
}
