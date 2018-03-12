package com.example.toshiba.prenosrobe.data;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Vehicle implements Serializable
{
	private Integer id;

	private String registrationNumber;

	private VehicleType vehicleType;

	/**
	 * Instantiate a new Vehicle.
	 */
	public Vehicle()
	{
	}

	/**
	 * Instantiate a new Vehicle.
	 *
	 * @param registrationNumber registration number
	 * @param vehicleType vehicle type
	 */
	public Vehicle(final String registrationNumber, final VehicleType vehicleType)
	{
		this.registrationNumber = registrationNumber;
		this.vehicleType = vehicleType;
	}

	/**
	 * Get the id.
	 *
	 * @return id
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * Set the id.
	 *
	 * @param id new id
	 */
	public void setId(final Integer id)
	{
		this.id = id;
	}

	/**
	 * Get the registration number.
	 *
	 * @return registration number
	 */
	public String getRegistrationNumber()
	{
		return registrationNumber;
	}

	/**
	 * Set the registration number.
	 *
	 * @param registrationNumber new registration number
	 */
	public void setRegistrationNumber(final String registrationNumber)
	{
		this.registrationNumber = registrationNumber;
	}

	/**
	 * Get the vehicle type.
	 *
	 * @return vehicle type
	 */
	public VehicleType getVehicleType()
	{
		return vehicleType;
	}

	/**
	 * Set the vehicle type.
	 *
	 * @param vehicleType new vehicle type
	 */
	public void setVehicleType(final VehicleType vehicleType)
	{
		this.vehicleType = vehicleType;
	}
}
