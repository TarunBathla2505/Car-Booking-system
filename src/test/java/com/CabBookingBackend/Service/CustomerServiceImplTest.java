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
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private CurrentUserSessionRepo currentUserSessionRepo;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CurrentUserSession currentUserSession;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setCustomerId(1);
        customer.setEmail("customer@example.com");
        customer.setPassword("customer123");
        customer.setUserName("customer");
        customer.setUserRole("Customer");

        currentUserSession = new CurrentUserSession();
        currentUserSession.setUuid("uuid");
        currentUserSession.setCurrRole("Customer");
    }

    @Test
    public void testInsertCustomer_Success() throws CustomerException {
        when(customerRepo.findByEmail(customer.getEmail())).thenReturn(Optional.empty());
        when(customerRepo.save(customer)).thenReturn(customer);

        Customer insertedCustomer = customerService.insertCustomer(customer);

        assertNotNull(insertedCustomer);
        assertEquals(customer, insertedCustomer);
    }

    @Test
    public void testInsertCustomer_CustomerAlreadyRegistered() {
        when(customerRepo.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));

        assertThrows(CustomerException.class, () -> customerService.insertCustomer(customer));
    }

    @Test
    public void testUpdateCustomer_Success() throws CustomerException, CurrentUserSessionException {
        when(currentUserSessionRepo.findByUuid(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
        when(customerRepo.findByEmail(customer.getEmail())).thenReturn(Optional.of(customer));
        when(customerRepo.save(any(Customer.class))).thenReturn(customer);

        Customer updatedCustomer = customerService.updateCustomer(customer, currentUserSession.getUuid());

        assertNotNull(updatedCustomer);
        assertEquals(customer, updatedCustomer);
    }

    @Test
    public void testDeleteCustomer_Success() throws CustomerException, CurrentUserSessionException {
        when(currentUserSessionRepo.findByUuidAndRole(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
        when(customerRepo.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));

        Customer deletedCustomer = customerService.deleteCustomer(customer.getCustomerId(), currentUserSession.getUuid());

        assertNotNull(deletedCustomer);
        assertEquals(customer, deletedCustomer);
    }

    @Test
    public void testViewCustomer_Success() throws CustomerException, CurrentUserSessionException {
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(currentUserSessionRepo.findByUuidAndRole(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
        when(customerRepo.findAll()).thenReturn(customers);

        List<Customer> result = customerService.viewCustomer(currentUserSession.getUuid());

        assertNotNull(result);
        assertEquals(customers, result);
    }

    @Test
    public void testViewCustomerById_Success() throws CustomerException, CurrentUserSessionException {
        when(currentUserSessionRepo.findByUuidAndRole(currentUserSession.getUuid())).thenReturn(Optional.of(currentUserSession));
        when(customerRepo.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));

        Customer result = customerService.viewCustomer(customer.getCustomerId(), currentUserSession.getUuid());

        assertNotNull(result);
        assertEquals(customer, result);
    }
}

