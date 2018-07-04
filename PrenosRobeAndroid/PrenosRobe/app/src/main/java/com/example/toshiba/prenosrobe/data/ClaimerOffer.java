package com.example.toshiba.prenosrobe.data;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ClaimerOffer implements Serializable
{
	private Integer id;

	private String departureLocation;

	private String arrivalLocation;

	private String data;

	private String photo;

	private User user;

	private DriverOffer driverOffer;

	private OfferStatus offerStatus;

	/**
	 * Instantiate a new ClaimerOffer.
	 */
	public ClaimerOffer()
	{
	}

	/**
	 * Instantiate a new ClaimerOffer.
	 *
	 * @param departureLocation departure location
	 * @param arrivalLocation arrival location
	 * @param data data
	 * @param user user
	 * @param driverOffer driver offer
	 * @param offerStatus offer status
	 */
	public ClaimerOffer(final String departureLocation, final String arrivalLocation,
			final String data, final User user, final DriverOffer driverOffer,
			final OfferStatus offerStatus)
	{
		this.departureLocation = departureLocation;
		this.arrivalLocation = arrivalLocation;
		this.data = data;
		this.user = user;
		this.driverOffer = driverOffer;
		this.offerStatus = offerStatus;
	}

	/**
	 * Instantiate a new ClaimerOffer.
	 *
	 * @param departureLocation departure location
	 * @param arrivalLocation arrival location
	 * @param data data
	 * @param photo photo
	 * @param user user
	 * @param driverOffer driver offer
	 * @param offerStatus offer status
	 */
	public ClaimerOffer(final String departureLocation, final String arrivalLocation,
			final String data, final String photo, final User user, final DriverOffer driverOffer,
			final OfferStatus offerStatus)
	{
		this.departureLocation = departureLocation;
		this.arrivalLocation = arrivalLocation;
		this.data = data;
		this.photo = photo;
		this.user = user;
		this.driverOffer = driverOffer;
		this.offerStatus = offerStatus;
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
	 * Get the departure location.
	 *
	 * @return departure location
	 */
	public String getDepartureLocation()
	{
		return departureLocation;
	}

	/**
	 * Set the departure location.
	 *
	 * @param departureLocation new departure location
	 */
	public void setDepartureLocation(final String departureLocation)
	{
		this.departureLocation = departureLocation;
	}

	/**
	 * Get the arrival location.
	 *
	 * @return arrival location
	 */
	public String getArrivalLocation()
	{
		return arrivalLocation;
	}

	/**
	 * Set the arrival location.
	 *
	 * @param arrivalLocation new arrival location
	 */
	public void setArrivalLocation(final String arrivalLocation)
	{
		this.arrivalLocation = arrivalLocation;
	}

	/**
	 * Get the data.
	 *
	 * @return data
	 */
	public String getData()
	{
		return data;
	}

	/**
	 * Set the data.
	 *
	 * @param data new data
	 */
	public void setData(final String data)
	{
		this.data = data;
	}

	/**
	 * Get the photo.
	 *
	 * @return photo
	 */
	public String getPhoto()
	{
		return photo;
	}

	/**
	 * Set the photo.
	 *
	 * @param photo new photo
	 */
	public void setPhoto(final String photo)
	{
		this.photo = photo;
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
	 * @param user new user 
	 */
	public void setUser(final User user)
	{
		this.user = user;
	}

	/**
	 * Get the driver offer.
	 *
	 * @return driver offer
	 */
	public DriverOffer getDriverOffer()
	{
		return driverOffer;
	}

	/**
	 * Set the driver offer.
	 *
	 * @param driverOffer new driver offer 
	 */
	public void setDriverOffer(final DriverOffer driverOffer)
	{
		this.driverOffer = driverOffer;
	}

	/**
	 * Get the offer status.
	 *
	 * @return offer status 
	 */
	public OfferStatus getOfferStatus()
	{
		return offerStatus;
	}

	/**
	 * Set the offer status.
	 *
	 * @param offerStatus new offer status 
	 */
	public void setOfferStatus(final OfferStatus offerStatus)
	{
		this.offerStatus = offerStatus;
	}
}
