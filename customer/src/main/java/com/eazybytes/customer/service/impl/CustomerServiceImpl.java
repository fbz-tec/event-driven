package com.eazybytes.customer.service.impl;

import com.eazybytes.common.event.CustomerDataChangedEvent;
import com.eazybytes.customer.command.event.CustomerCreatedEvent;
import com.eazybytes.customer.command.event.CustomerDeletedEvent;
import com.eazybytes.customer.command.event.CustomerUpdatedEvent;
import com.eazybytes.customer.dto.CustomerDto;
import com.eazybytes.customer.entity.Customer;
import com.eazybytes.customer.exception.CustomerAlreadyExistsException;
import com.eazybytes.customer.exception.ResourceNotFoundException;
import com.eazybytes.customer.mapper.CustomerMapper;
import com.eazybytes.customer.repository.CustomerRepository;
import com.eazybytes.customer.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private CustomerRepository customerRepository;
    private EventGateway eventGateway;

    @Override
    public void createCustomer(CustomerCreatedEvent customerCreatedEvent) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumberAndActiveSw(
                customerCreatedEvent.getMobileNumber(), true);
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "
                    + customerCreatedEvent.getMobileNumber());
        }
        Customer ccustomer =new Customer();
        BeanUtils.copyProperties(customerCreatedEvent, ccustomer);
        customerRepository.save(ccustomer);
    }

    @Override
    public CustomerDto fetchCustomer(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(mobileNumber, true).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        return customerDto;
    }

    @Override
    public boolean updateCustomer(CustomerUpdatedEvent event) {
        Customer customer = customerRepository.findByMobileNumberAndActiveSw(event.getMobileNumber(), true)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", event.getMobileNumber()));
        customer =CustomerMapper.mapEventToCustomer(event, customer);
        customerRepository.save(customer);
        return true;
    }

    @Override
    public boolean deleteCustomer(CustomerDeletedEvent event) {
        Customer customer = customerRepository.findById(event.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "customerId", event.getCustomerId())
        );
        customer.setActiveSw(event.isActiveSw());
        customerRepository.save(customer);
        CustomerDataChangedEvent customerDataChangedEvent = new CustomerDataChangedEvent();
        BeanUtils.copyProperties(customer, customerDataChangedEvent);
        eventGateway.publish(customerDataChangedEvent);
        return true;
    }

}
