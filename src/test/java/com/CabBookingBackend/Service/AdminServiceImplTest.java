package com.CabBookingBackend.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.CabBookingBackend.Exception.*;
import com.CabBookingBackend.Model.*;
import com.CabBookingBackend.Repositary.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private AdminRepo adminRepo;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private TripBookingRepo tripBookingRepo;

    @Mock
    private CurrentUserSessionRepo currentUserSessionRepo;

    @InjectMocks
    private AdminServiceImpl adminService;

    private Admin admin;
    private CurrentUserSession currentUserSession;
    private Customer customer;
    private TripBooking tripBooking;

    @BeforeEach
    public void setUp() {
        admin = new Admin();
        admin.setAdminId(1);
        admin.setEmail("admin@example.com");
        admin.setPassword("admin123");
        admin.setUserName("admin");
        admin.setUserRole("Admin");

        currentUserSession = new CurrentUserSession();
        currentUserSession.setUuid("uuid");
        currentUserSession.setCurrRole("Admin");

        customer = new Customer();
        customer.setCustomerId(1);
        customer.setEmail("customer@example.com");
        customer.setPassword("customer123");
        customer.setUserName("customer");

        tripBooking = new TripBooking();
        tripBooking.setTripBookingId(1);
        tripBooking.setCustomer(customer);
        tripBooking.setFromDateTime("2024-02-16T10:15:30");
        tripBooking.setToDateTime("2024-02-16T12:15:30");
    }

    @Test
    public void testInsertAdmin_Success() throws AdminException {
        when(adminRepo.findByEmail(admin.getEmail())).thenReturn(Optional.empty());
        when(adminRepo.save(admin)).thenReturn(admin);

        Admin insertedAdmin = adminService.insertAdmin(admin);

        assertEquals(admin, insertedAdmin);
    }

    @Test
    public void testInsertAdmin_AdminAlreadyRegistered() {
        when(adminRepo.findByEmail(admin.getEmail())).thenReturn(Optional.of(admin));

        assertThrows(AdminException.class, () -> adminService.insertAdmin(admin));
    }

    @Test
    public void testUpdateAdmin_Success() throws AdminException, CurrentUserSessionException {
        when(currentUserSessionRepo.findByUuidAndRole(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
        when(adminRepo.findByEmail(admin.getEmail())).thenReturn(Optional.of(admin));
        when(adminRepo.save(any(Admin.class))).thenReturn(admin);

        Admin updatedAdmin = adminService.updateAdmin(admin, currentUserSession.getUuid());

        assertNotNull(updatedAdmin);
        assertEquals(admin, updatedAdmin);
    }

    @Test
    public void testDeleteAdmin_Success() throws AdminException, CurrentUserSessionException {
        when(currentUserSessionRepo.findByUuidAndRole(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
        when(adminRepo.findById(admin.getAdminId())).thenReturn(Optional.of(admin));

        Admin deletedAdmin = adminService.deleteAdmin(admin.getAdminId(), currentUserSession.getUuid());

        assertNotNull(deletedAdmin);
        assertEquals(admin, deletedAdmin);
    }

    @Test
    public void testGetAllTrips_Success() throws AdminException, TripBookingException, CurrentUserSessionException {
        List<TripBooking> tripBookings = new ArrayList<>();
        tripBookings.add(tripBooking);

        when(currentUserSessionRepo.findByUuidAndRole(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
        when(tripBookingRepo.findAll()).thenReturn(tripBookings);

        List<TripBooking> result = adminService.getAllTrips(currentUserSession.getUuid());

        assertNotNull(result);
        assertEquals(tripBookings, result);
    }

//    @Test
//    public void testGetTripsCabwise_Success() throws TripBookingException, CurrentUserSessionException {
//        List<TripBooking> tripBookings = new ArrayList<>();
//        tripBookings.add(tripBooking);
//
//        when(currentUserSessionRepo.findByUuidAndRole(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
//        when(tripBookingRepo.findAll()).thenReturn(tripBookings);
//
//        List<TripBooking> result = adminService.getTripsCabwise("SUV", currentUserSession.getUuid());
//
//        assertNotNull(result);
//        assertEquals(tripBookings, result);
//    }
//
//    @Test
//    public void testGetTripsCustomerwise_Success() throws TripBookingException, CustomerException, CurrentUserSessionException {
//        List<TripBooking> tripBookings = new ArrayList<>();
//        tripBookings.add(tripBooking);
//
//        when(currentUserSessionRepo.findByUuidAndRole(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
//        when(customerRepo.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
//
//        List<TripBooking> result = adminService.getTripsCustomerwise(customer.getCustomerId(), currentUserSession.getUuid());
//
//        assertNotNull(result);
//        assertEquals(tripBookings, result);
//    }
//
//    @Test
//    public void testGetAllTripsForDays_Success() throws TripBookingException, CustomerException, CurrentUserSessionException {
//        List<TripBooking> tripBookings = new ArrayList<>();
//        tripBookings.add(tripBooking);
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime fromDateTime = LocalDateTime.parse("2024-02-16 00:00", formatter);
//        LocalDateTime toDateTime = LocalDateTime.parse("2024-02-17 00:00", formatter);
//
//        when(currentUserSessionRepo.findByUuidAndRole(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
//        when(customerRepo.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
//        when(tripBookingRepo.findAll()).thenReturn(tripBookings);
//
//        List<TripBooking> result = adminService.getAllTripsForDays(customer.getCustomerId(), "2024-02-16 00:00", "2024-02-17 00:00", currentUserSession.getUuid());
//
//        assertNotNull(result);
//        assertEquals(tripBookings, result);
//    }
}
