package com.CabBookingBackend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CabBookingBackend.Service.UserLoginService;
import com.CabBookingBackend.Exception.AdminException;
import com.CabBookingBackend.Exception.CurrentUserSessionException;
import com.CabBookingBackend.Exception.CustomerException;
import com.CabBookingBackend.Model.CurrentUserSession;
import com.CabBookingBackend.Model.UserLoginDTO;


@RestController
@RequestMapping("/Userlogin")
public class LoginController {

	@Autowired
	private UserLoginService userlogService;
	
	@PostMapping("/Login")
	public ResponseEntity<CurrentUserSession> loginHandler(@RequestBody UserLoginDTO dto) throws CustomerException, AdminException {
		return new ResponseEntity<CurrentUserSession>(userlogService.login(dto), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/logout")
	public ResponseEntity<String> logoutHandler(@RequestParam String uuid) throws CurrentUserSessionException {
		return new ResponseEntity<String>(userlogService.LogOut(uuid), HttpStatus.OK);
	}
	
}
