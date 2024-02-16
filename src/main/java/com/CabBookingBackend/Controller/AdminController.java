package com.CabBookingBackend.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CabBookingBackend.Exception.AdminException;
import com.CabBookingBackend.Exception.CurrentUserSessionException;
import com.CabBookingBackend.Exception.CustomerException;
import com.CabBookingBackend.Exception.TripBookingException;
import com.CabBookingBackend.Model.Admin;
import com.CabBookingBackend.Model.TripBooking;
import com.CabBookingBackend.Service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	
	@PostMapping("/register")
	public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) throws AdminException{
		return new ResponseEntity<Admin>(adminService.insertAdmin(admin),HttpStatus.CREATED);
	}
	
	@PutMapping("/Update")
	public ResponseEntity<Admin> updateAdminHandler(@RequestBody Admin admin,@RequestParam String uuid) throws AdminException, CurrentUserSessionException{
		return new ResponseEntity<Admin>(adminService.updateAdmin(admin, uuid),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Admin> deleteAdmin(@RequestParam("adminId") Integer adminId,@RequestParam("uuid")  String uuid) throws AdminException, CurrentUserSessionException{
		return new ResponseEntity<Admin>(adminService.deleteAdmin(adminId, uuid),HttpStatus.OK);
	}
	
}