package com.CabBookingBackend.Service;

import com.CabBookingBackend.Exception.AdminException;
import com.CabBookingBackend.Exception.CurrentUserSessionException;
import com.CabBookingBackend.Exception.CustomerException;
import com.CabBookingBackend.Model.CurrentUserSession;
import com.CabBookingBackend.Model.UserLoginDTO;

public interface UserLoginService {

public CurrentUserSession login(UserLoginDTO dto)throws CustomerException, AdminException;
	
	public String LogOut(String uuid)throws CurrentUserSessionException; 
	
	
}
