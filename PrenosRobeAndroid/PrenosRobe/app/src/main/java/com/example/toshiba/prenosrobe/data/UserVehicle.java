package com.example.toshiba.prenosrobe.data;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class UserVehicle implements Serializable
{
	private Integer id;

	private User user;

	private Vehicle vehicle;

	/**
	 * Instantiate a new UserVehicle.
	 */
	public UserVehicle()
	{
	}

	/**
	 * Instantiate a new UserVehicle.
	 *
	 * @param user user
	 * @param vehicle vehicle
	 */
	public UserVehicle(final User user, final Vehicle vehicle)
	{
		this.user = user;
		this.vehicle = vehicle;
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
	 * Get the user.
	 *
	 * @return user
	 */
	public User getUser()
	{
		return user;
	}

	/**
	 * Set the user.
	 *
	 * @param userId new user
	 */
	public void setUser(final User user)
	{
		this.user = user;
	}

	/**
	 * Get the vehicle.
	 *
	 * @return vehicle
	 */
	public Vehicle getVehicle()
	{
		return vehicle;
	}

	/**
	 * Set the vehicle.
	 *
	 * @param vehicleId new vehicle 
	 */
	public void setVehicle(final Vehicle vehicle)
	{
		this.vehicle = vehicle;
	}
}
