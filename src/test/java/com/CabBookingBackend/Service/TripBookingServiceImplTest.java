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
public class TripBookingServiceImplTest {

    @Mock
    private TripBookingRepo tripBookingRepo;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private CabRepo cabRepo;

    @Mock
    private CurrentUserSessionRepo currentUserSessionRepo;

    @Mock
    private DriverRepo driverRepo;

    @InjectMocks
    private TripBookingServiceImpl tripBookingService;

    private TripBooking tripBooking;
    private CurrentUserSession currentUserSession;

    @BeforeEach
    public void setUp() {
        tripBooking = new TripBooking();
        tripBooking.setTripBookingId(1);
        tripBooking.setPickupLocation("Location A");
        tripBooking.setDropLocation("Location B");

        currentUserSession = new CurrentUserSession();
        currentUserSession.setUuid("uuid");
        currentUserSession.setCurrUserId(1);
    }

    @Test
    public void testSearchByLocation_Success() throws TripBookingException, CurrentUserSessionException {
        when(currentUserSessionRepo.findByUuid(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));

        Cab cab1 = new Cab();
        cab1.setCabId(1);
        cab1.setCabCurrStatus("Available");
        cab1.setCurrLocation("Location A");

        Cab cab2 = new Cab();
        cab2.setCabId(2);
        cab2.setCabCurrStatus("Unavailable");
        cab2.setCurrLocation("Location A");

        List<Cab> availableCabs = new ArrayList<>();
        availableCabs.add(cab1);
        availableCabs.add(cab2);

        when(cabRepo.findAll()).thenReturn(availableCabs);

        List<Cab> result = tripBookingService.searchByLocation("Location A", currentUserSession.getUuid());

        assertNotNull(result);
        assertEquals(1, result.size());
    }

}
