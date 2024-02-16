package com.CabBookingBackend.Controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CabBookingBackend.Service.TripBookingService;
import com.CabBookingBackend.Exception.CabException;
import com.CabBookingBackend.Exception.CurrentUserSessionException;
import com.CabBookingBackend.Exception.TripBookingException;
import com.CabBookingBackend.Model.Cab;
import com.CabBookingBackend.Model.TripBooking;
import com.CabBookingBackend.Model.TripBookingDTO;

@RestController
@RequestMapping("/tripBooking")
public class TripController {

	@Autowired
	private TripBookingService tripBookingService;
	
	
	@GetMapping("/searchCab")
	public ResponseEntity<List<Cab>> searchByPickupLocation(@RequestParam("pickUpLocation") String pickUpLocation, @RequestParam("uuid") String uuid) throws TripBookingException, CurrentUserSessionException{
		return new ResponseEntity<List<Cab>>(tripBookingService.searchByLocation(pickUpLocation, uuid),HttpStatus.OK);
	}
	
	@PostMapping("/BookRequest")
	public ResponseEntity<TripBooking> BookRequest(@RequestParam("cabId")   Integer cabId, @RequestBody TripBooking tripBooking,@RequestParam("uuid") String uuid ) throws TripBookingException, CabException, CurrentUserSessionException{
		return new ResponseEntity<TripBooking>(tripBookingService.BookRequest(cabId, tripBooking, uuid),HttpStatus.OK);
	}
	
	@GetMapping("/viewBookingbyId")
	public ResponseEntity<TripBookingDTO> viewBookingbyId(@RequestParam("TripBookingId") Integer TripBookingId,@RequestParam("uuid") String uuid) throws TripBookingException, CabException, CurrentUserSessionException{
		return new ResponseEntity<TripBookingDTO>(tripBookingService.viewBookingById(TripBookingId, uuid),HttpStatus.OK);
	}
	
	@GetMapping("/markCompleteTrip")
	public ResponseEntity<String>  markCompleteTrip(@RequestParam("TripBookingId") Integer TripBookingId,@RequestParam("uuid") String uuid) throws TripBookingException, CurrentUserSessionException{
		return new ResponseEntity<String>(tripBookingService.MarkTripAsCompleted(TripBookingId, uuid),HttpStatus.OK);
	}
	
}

