/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc4904.standard.subsystems.chassis;


import org.usfirst.frc4904.standard.custom.sensors.CANTalonEncoder;

import org.usfirst.frc4904.standard.commands.chassis.SimpleSplines;
import org.usfirst.frc4904.standard.custom.sensors.NavX;

import edu.wpi.first.math.geometry.Pose2d;

public class SplinesDrive extends SensorDrive {
  private final SimpleSplines.AutoConstants autoConstants;
  private final SimpleSplines.DriveConstants driveConstants;

  /**
   * Creates a new DriveSubsystem.
   */
  public SplinesDrive(TankDrive driveBase, SimpleSplines.AutoConstants autoConstants, SimpleSplines.DriveConstants driveConstants, CANTalonEncoder leftEncoder, CANTalonEncoder rightEncoder, NavX gyro, Pose2d initialPose) {
    super(driveBase,leftEncoder, rightEncoder, gyro, initialPose);
    this.autoConstants = autoConstants;
    this.driveConstants = driveConstants;
  }

  public SimpleSplines.AutoConstants getAutoConstants(){
    return autoConstants;
  }

  public SimpleSplines.DriveConstants getDriveConstants(){
    return driveConstants;
  }
}
