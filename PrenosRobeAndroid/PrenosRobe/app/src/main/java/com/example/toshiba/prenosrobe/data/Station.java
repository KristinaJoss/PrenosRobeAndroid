package com.example.toshiba.prenosrobe.data;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Station implements Serializable
{
	private Integer id;

	private String name;

	private double xCoordinate;

	private double yCoordinate;

	private Area area;

	/**
	 * Instantiates a new Station.
	 */
	public Station()
	{
	}

	/**
	 * Instantiate a new Station.
	 *
	 * @param name the name
	 */
	public Station(final String name)
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

	/**
	 * Get the x coordinate.
	 *
	 * @return x coordinate
	 */
	public double getXCoordinate()
	{
		return xCoordinate;
	}

	/**
	 * Set the x coordinate.
	 *
	 * @param xCoordinate new x coordinate
	 */
	public void setXCoordinate(final double xCoordinate)
	{
		this.xCoordinate = xCoordinate;
	}

	/**
	 * Get the y coordinate.
	 *
	 * @return y coordinate
	 */
	public double getYCoordinate()
	{
		return yCoordinate;
	}

	/**
	 * Set the y coordinate.
	 *
	 * @param yCoordinate new y coordinate
	 */
	public void setYCoordinate(final double yCoordinate)
	{
		this.yCoordinate = yCoordinate;
	}

	/**
	 * Get the area.
	 *
	 * @return area
	 */
	public Area getArea()
	{
		return area;
	}

	/**
	 * Set the area.
	 *
	 * @param area new area
	 */
	public void setArea(final Area area)
	{
		this.area = area;
	}
}
