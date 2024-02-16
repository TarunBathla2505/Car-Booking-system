package com.CabBookingBackend.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.CabBookingBackend.Exception.*;
import com.CabBookingBackend.Model.*;
import com.CabBookingBackend.Repositary.*;

public class UserLoginServiceImplTest {

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private AdminRepo adminRepo;

    @Mock
    private CurrentUserSessionRepo currentUserSessionRepo;

    @InjectMocks
    private UserLoginServiceimpl userLoginService;

    private UserLoginDTO userLoginDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userLoginDTO = new UserLoginDTO();
        userLoginDTO.setEmail("test@example.com");
        userLoginDTO.setPassword("password123");
    }

//    @Test
//    public void testLogin_Customer_Success() throws CustomerException, AdminException {
//        Customer customer = new Customer();
//        customer.setCustomerId(1);
//        customer.setEmail("test@example.com");
//        customer.setPassword("password123");
//
//        when(customerRepo.findByEmail(userLoginDTO.getEmail())).thenReturn(Optional.of(customer));
//        when(currentUserSessionRepo.findById(customer.getCustomerId())).thenReturn(Optional.empty());
//
//        CurrentUserSession currentUserSession = userLoginService.login(userLoginDTO);
//
////        assertNotNull(currentUserSession);
//        assertEquals("Customer", currentUserSession.getCurrRole());
//        assertEquals("Login Successfull", currentUserSession.getCurrStatus());
//    }

//    @Test
//    public void testLogin_Admin_Success() throws CustomerException, AdminException {
//        Admin admin = new Admin();
//        admin.setAdminId(1);
//        admin.setEmail("test@example.com");
//        admin.setPassword("password123");
//
//        when(adminRepo.findByEmail(userLoginDTO.getEmail())).thenReturn(Optional.of(admin));
//        when(currentUserSessionRepo.findById(admin.getAdminId())).thenReturn(Optional.empty());
//
//        CurrentUserSession currentUserSession = userLoginService.login(userLoginDTO);
//
////        assertNotNull(currentUserSession);
//        assertEquals("Admin", currentUserSession.getCurrRole());
//        assertEquals("Login Successfull", currentUserSession.getCurrStatus());
//    }

    @Test
    public void testLogOut_Success() throws CurrentUserSessionException {
        CurrentUserSession currentUserSession = new CurrentUserSession();
        currentUserSession.setUuid("uuid");

        when(currentUserSessionRepo.findByUuid(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));

        String result = userLoginService.LogOut(currentUserSession.getUuid());

        assertEquals("User Logged Out Successfully", result);
    }
}

