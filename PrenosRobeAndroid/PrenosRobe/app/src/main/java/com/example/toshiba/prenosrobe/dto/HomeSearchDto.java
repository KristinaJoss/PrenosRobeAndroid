package com.example.toshiba.prenosrobe.dto;

import java.io.Serializable;
import java.util.Date;

public class HomeSearchDto implements Serializable
{

    private String departureLocation;
    private String arrivalLocation;
    private Date date;

    public HomeSearchDto(final String departureLocation, final String arrivalLocation, final Date date)
    {
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.date = date;
    }

}
