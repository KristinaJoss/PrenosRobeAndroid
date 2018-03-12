package com.example.toshiba.prenosrobe.data;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class VehicleType implements Serializable
{
	private Integer id;

	private String name;

	/**
	 * Instantiate a new VehicleType.
	 */
	public VehicleType()
	{
	}

	/**
	 * Instantiate a new VehicleType.
	 *
	 * @param name name
	 */
	public VehicleType(final String name)
	{
		this.name = name;
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
	 * Get the name.
	 *
	 * @return name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Set the name.
	 *
	 * @param name new name
	 */
	public void setName(final String name)
	{
		this.name = name;
	}
}
