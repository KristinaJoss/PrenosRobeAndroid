package com.example.toshiba.prenosrobe.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RestRespondeDto<T> implements Serializable
{
    private static final long serialVersionUID = 266446195575446911L;

    private List<String> errorList = new ArrayList<>();
    private int statusCode;
    private T data;

    /**
     * Instantiate a new RestRespondeDto.
     */
    public RestRespondeDto()
    {
    }

    /**
     * Instantiate a new RestRespondeDto.
     *
     * @param statusCode status code
     */
    public RestRespondeDto(final int statusCode)
    {
        this.statusCode = statusCode;
    }

    /**
     * Instantiate a new RestRespondeDto.
     *
     * @param statusCode status code
     * @param data data
     */
    public RestRespondeDto(final int statusCode, final T data)
    {
        this.statusCode = statusCode;
        this.data = data;
    }

    /**
     * Instantiates a new RestRespondeDto.
     *
     * @param statusCode status code
     * @param errorList error list
     */
    public RestRespondeDto(final int statusCode, final List<String> errorList)
    {
        this.statusCode = statusCode;
        this.errorList = errorList;
    }

    /**
     * Get the error list.
     *
     * @return error list
     */
    public List<String> getErrorList()
    {
        return errorList;
    }

    /**
     * Set the error list.
     *
     * @param errorList new error list
     */
    public void setErrorList(final List<String> errorList)
    {
        this.errorList = errorList;
    }

    /**
     * Get the status code.
     *
     * @return status code
     */
    public int getStatusCode()
    {
        return statusCode;
    }

    /**
     * Set the status code.
     *
     * @param statusCode new status code
     */
    public void setStatusCode(final int statusCode)
    {
        this.statusCode = statusCode;
    }

    /**
     * Get the data.
     *
     * @return data
     */
    public T getData()
    {
        return data;
    }

    /**
     * Set the data.
     *
     * @param data new data
     */
    public void setData(final T data)
    {
        this.data = data;
    }
}
