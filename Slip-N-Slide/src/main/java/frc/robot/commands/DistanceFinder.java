/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.PortMap;
import frc.robot.Robot;

public class DistanceFinder extends CommandBase {
  /**
   * Creates a new DistanceFinder.
   */
  private boolean longRange= true;
  public DistanceFinder() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.m_limeLight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Robot.m_oi.isButtonPressed(PortMap.XBOX_BY, true)){
      longRange=!longRange;
    }
    SmartDashboard.putBoolean("Long Range", longRange);
    if(longRange){
      Robot.m_limeLight.longRange();
    }else{
      Robot.m_limeLight.shortRange();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}