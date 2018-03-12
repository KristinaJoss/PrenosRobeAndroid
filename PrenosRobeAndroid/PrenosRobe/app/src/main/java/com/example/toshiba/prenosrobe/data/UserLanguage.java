package com.example.toshiba.prenosrobe.data;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class UserLanguage implements Serializable
{
	private Integer id;

	private Integer userId;

	private Language language;

	/**
	 * Instantiate a new UserLanguage.
	 */
	public UserLanguage()
	{
	}

	/**
	 * Instantiate a new UserLanguage.
	 *
	 * @param userId user id
	 * @param language language
	 */
	public UserLanguage(final Integer userId, final Language language)
	{
		this.userId = userId;
		this.language = language;
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
	 * Get the user id.
	 *
	 * @return user id
	 */
	public Integer getUserId()
	{
		return userId;
	}

	/**
	 * Set the user id.
	 *
	 * @param userId new user id
	 */
	public void setUserId(final Integer userId)
	{
		this.userId = userId;
	}

	/**
	 * Get the language.
	 *
	 * @return language
	 */
	public Language getLanguage()
	{
		return language;
	}

	/**
	 * Set the language.
	 *
	 * @param language new language
	 */
	public void setLanguage(final Language language)
	{
		this.language = language;
	}
}
