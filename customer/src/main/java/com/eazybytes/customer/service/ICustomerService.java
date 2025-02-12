package com.eazybytes.customer.service;

import com.eazybytes.customer.command.event.CustomerCreatedEvent;
import com.eazybytes.customer.command.event.CustomerDeletedEvent;
import com.eazybytes.customer.command.event.CustomerUpdatedEvent;
import com.eazybytes.customer.dto.CustomerDto;
import com.eazybytes.customer.entity.Customer;

public interface ICustomerService {

    /**
     * @param customerCreatedEvent- CustomerCreatedEvent Object
     */
    void createCustomer(CustomerCreatedEvent customerCreatedEvent);

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    CustomerDto fetchCustomer(String mobileNumber);

    /**
     * @param customerUpdatedEvent - CustomerUpdatedEvent Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    boolean updateCustomer(CustomerUpdatedEvent customerUpdatedEvent);

    /**
     * @param customerDeletedEvent - Input customerDeletedEvent Object
     * @return boolean indicating if the delete of Customer details is successful or not
     */
    boolean deleteCustomer(CustomerDeletedEvent customerDeletedEvent);
}
