package com.CabBookingBackend.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
public class DriverServiceImplTest {

    @Mock
    private DriverRepo driverRepo;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private TripBookingRepo tripBookingRepo;

    @Mock
    private CurrentUserSessionRepo currentUserSessionRepo;

    @InjectMocks
    private DriverServiceImpl driverService;

    private Driver driver;
    private CurrentUserSession currentUserSession;

    @BeforeEach
    public void setUp() {
        driver = new Driver();
        driver.setDriverId(1);
        driver.setEmail("driver@example.com");
        driver.setPassword("driver123");
        driver.setUserName("driver");
        driver.setUserRole("Driver");
        driver.setLicenceNo("1234567890");

        currentUserSession = new CurrentUserSession();
        currentUserSession.setUuid("uuid");
    }

    @Test
    public void testInsertDriver_Success() throws DriverException {
        when(driverRepo.findByLicenceNo(driver.getLicenceNo())).thenReturn(Optional.empty());
        when(driverRepo.save(driver)).thenReturn(driver);

        Driver insertedDriver = driverService.insertDriver(driver);

        assertNotNull(insertedDriver);
        assertEquals(driver, insertedDriver);
    }

    @Test
    public void testInsertDriver_DriverAlreadyRegistered() {
        when(driverRepo.findByLicenceNo(driver.getLicenceNo())).thenReturn(Optional.of(driver));

        assertThrows(DriverException.class, () -> driverService.insertDriver(driver));
    }

    @Test
    public void testUpdateDriver_Success() throws DriverException, CurrentUserSessionException {
        when(currentUserSessionRepo.findByUuid(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
        when(driverRepo.findByEmail(driver.getEmail())).thenReturn(Optional.of(driver));
        when(driverRepo.save(any(Driver.class))).thenReturn(driver);

        Driver updatedDriver = driverService.updateDriver(driver, currentUserSession.getUuid());

        assertNotNull(updatedDriver);
        assertEquals(driver, updatedDriver);
    }

    @Test
    public void testDeleteDriver_Success() throws DriverException, CurrentUserSessionException {
        when(currentUserSessionRepo.findByUuid(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
        when(driverRepo.findById(driver.getDriverId())).thenReturn(Optional.of(driver));

        Driver deletedDriver = driverService.deleteDriver(driver.getDriverId(), currentUserSession.getUuid());

        assertNotNull(deletedDriver);
        assertEquals(driver, deletedDriver);
    }

//    @Test
//    public void testViewBestDriver_Success() throws DriverException, CurrentUserSessionException {
//        List<Driver> allDrivers = new ArrayList<>();
//        allDrivers.add(driver);
//
//        when(driverRepo.findAll()).thenReturn(allDrivers);
//
//        List<Driver> result = driverService.viewBestDriver(currentUserSession.getUuid());
//
//        assertNotNull(result);
//        assertFalse(result.isEmpty());
//    }

    @Test
    public void testViewDriver_Success() throws DriverException, CurrentUserSessionException {
        when(currentUserSessionRepo.findByUuid(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
        when(driverRepo.findById(driver.getDriverId())).thenReturn(Optional.of(driver));

        Driver result = driverService.viewDriver(driver.getDriverId(), currentUserSession.getUuid());

        assertNotNull(result);
        assertEquals(driver, result);
    }
}
