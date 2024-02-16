package com.CabBookingBackend.Service;

import java.util.List;

import com.CabBookingBackend.Exception.CurrentUserSessionException;
import com.CabBookingBackend.Exception.DriverException;
import com.CabBookingBackend.Model.Driver;

public interface DriverService {

    Driver insertDriver(Driver driver)throws DriverException;
	
	Driver updateDriver(Driver driver,String uuid)throws DriverException,CurrentUserSessionException;
	
	Driver deleteDriver(Integer driverId, String uuid)throws DriverException,CurrentUserSessionException;
	
	List<Driver> viewBestDriver(String uuid)throws DriverException,CurrentUserSessionException;
	
	Driver viewDriver(Integer driverId,String uuid)throws DriverException,CurrentUserSessionException;
	
}
