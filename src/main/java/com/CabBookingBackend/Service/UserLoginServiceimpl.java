package com.CabBookingBackend.Service;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CabBookingBackend.Exception.AdminException;
import com.CabBookingBackend.Exception.CurrentUserSessionException;
import com.CabBookingBackend.Exception.CustomerException;
import com.CabBookingBackend.Model.Admin;
import com.CabBookingBackend.Model.CurrentUserSession;
import com.CabBookingBackend.Model.Customer;
import com.CabBookingBackend.Model.UserLoginDTO;
import com.CabBookingBackend.Repositary.AdminRepo;
import com.CabBookingBackend.Repositary.CurrentUserSessionRepo;
import com.CabBookingBackend.Repositary.CustomerRepo;

@Service
public class UserLoginServiceimpl implements UserLoginService{

	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private CurrentUserSessionRepo currRepo;

	@Override
	public CurrentUserSession login(UserLoginDTO dto) throws CustomerException, AdminException {
		
			Optional<Admin> findAdmin = adminRepo.findByEmail(dto.getEmail());
			Optional<Customer> findCustomer = customerRepo.findByEmail(dto.getEmail());
			
			if(findAdmin.isPresent() && findCustomer.isEmpty()) {
				Admin currAdmin = findAdmin.get();
				Optional<CurrentUserSession> validAdminSession = currRepo.findById(currAdmin.getAdminId());
				if(validAdminSession.isPresent()) {
					throw new AdminException("Admin is currently Login Please Logout and then try");
				}
				else {
					if(currAdmin.getPassword().equals(dto.getPassword())) {
						String key = RandomStringUtils.randomAlphanumeric(6);
						CurrentUserSession curr = new CurrentUserSession();
						curr.setUuid(key);
						curr.setCurrRole("Admin");
						curr.setCurrStatus("Login Successfull");
						curr.setCurrUserId(currAdmin.getAdminId());
						return currRepo.save(curr);
					}
					else {
						throw new AdminException("Please Enter the correct Password");
					}
				}
			}
			else if(findAdmin.isEmpty() && findCustomer.isPresent()){
					Customer currCustomer = findCustomer.get();
					Optional<CurrentUserSession> validCustomerSession = currRepo.findById(currCustomer.getCustomerId());
					if(validCustomerSession.isPresent()) {
						throw new CustomerException("Customer is currently Login Please Logout and then try");
					}
					else {
						if(currCustomer.getPassword().equals(dto.getPassword())) {
							String key = RandomStringUtils.randomAlphanumeric(6);
							CurrentUserSession curr = new CurrentUserSession();
							curr.setUuid(key);
							curr.setCurrRole("Customer");
							curr.setCurrStatus("Login Successfull");
							curr.setCurrUserId(currCustomer.getCustomerId());
							return currRepo.save(curr);
						}
						else {
							throw new CustomerException("Please Enter the correct Password");
						}
					}
				}
			else {
				throw new CustomerException("User is Not Registered");
			}
		}

	@Override
	public String LogOut(String uuid) throws CurrentUserSessionException {
		// TODO Auto-generated method stub
		Optional<CurrentUserSession> validAdminOrCustomer = currRepo.findByUuid(uuid);
		if(validAdminOrCustomer.isPresent()) {
			
			currRepo.delete(validAdminOrCustomer.get());
			return "User Logged Out Successfully";
			
		}
		else {
			throw new CurrentUserSessionException("User Not Logged In with this Credentials");
		}
	}

	
	
	

	
}
