package com.CabBookingBackend.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.CabBookingBackend.Service.CabServiceImpl;
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
public class CabServiceImplTest {

    @Mock
    private CabRepo cabRepo;

    @Mock
    private CurrentUserSessionRepo currentUserSessionRepo;

    @InjectMocks
    private CabServiceImpl cabService;

    private Cab cab;
    private CurrentUserSession currentUserSession;

    @BeforeEach
    public void setUp() {
        cab = new Cab();
        cab.setCabId(1);
        cab.setCarName("Toyota");
        cab.setCarNumber("XYZ123");
        cab.setCarType("SUV");
        cab.setPerKmRate(10.0F);

        currentUserSession = new CurrentUserSession();
        currentUserSession.setUuid("uuid");
        currentUserSession.setCurrRole("Admin");
    }

    @Test
    public void testInsertCab_Success() throws CabException {
        when(cabRepo.findByCarNumber(cab.getCarNumber())).thenReturn(Optional.empty());
        when(cabRepo.save(cab)).thenReturn(cab);

        Cab insertedCab = cabService.insertCab(cab);

        assertNotNull(insertedCab);
        assertEquals(cab, insertedCab);
    }

    @Test
    public void testInsertCab_CabAlreadyRegistered() {
        when(cabRepo.findByCarNumber(cab.getCarNumber())).thenReturn(Optional.of(cab));

        assertThrows(CabException.class, () -> cabService.insertCab(cab));
    }

    @Test
    public void testUpdateCab_Success() throws CabException, CurrentUserSessionException {
        when(currentUserSessionRepo.findByUuidAndRole(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
        when(cabRepo.findByCarNumber(cab.getCarNumber())).thenReturn(Optional.of(cab));
        when(cabRepo.save(any(Cab.class))).thenReturn(cab);

        Cab updatedCab = cabService.updateCab(cab, currentUserSession.getUuid());

        assertNotNull(updatedCab);
        assertEquals(cab, updatedCab);
    }

    @Test
    public void testDeleteCab_Success() throws CabException, CurrentUserSessionException {
        when(currentUserSessionRepo.findByUuidAndRole(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
        when(cabRepo.findById(cab.getCabId())).thenReturn(Optional.of(cab));

        Cab deletedCab = cabService.deleteCab(cab.getCabId(), currentUserSession.getUuid());

        assertNotNull(deletedCab);
        assertEquals(cab, deletedCab);
    }

    @Test
    public void testViewCabsOfType_Success() throws CabException, CurrentUserSessionException {
        List<Cab> cabs = new ArrayList<>();
        cabs.add(cab);

        when(currentUserSessionRepo.findByUuidAndRole(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
        when(cabRepo.findAll()).thenReturn(cabs);

        List<Cab> result = cabService.viewCabsOfType("SUV", currentUserSession.getUuid());

        assertNotNull(result);
        assertEquals(cabs, result);
    }

//    @Test
//    public void testCountCabsOfType_Success() throws CabException, CurrentUserSessionException {
//        List<Cab> cabs = new ArrayList<>();
//        cabs.add(cab);
//
//        when(currentUserSessionRepo.findByUuidAndRole(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
//        when(cabRepo.findAll()).thenReturn(cabs);
//
//        int count = cabService.countCabsOfType("SUV", currentUserSession.getUuid());
//
//        assertEquals(1, count);
//    }
}
