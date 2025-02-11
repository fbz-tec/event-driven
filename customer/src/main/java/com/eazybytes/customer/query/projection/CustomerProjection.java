package com.eazybytes.customer.query.projection;

import com.eazybytes.customer.command.event.CustomerCreatedEvent;
import com.eazybytes.customer.command.event.CustomerDeletedEvent;
import com.eazybytes.customer.command.event.CustomerUpdatedEvent;
import com.eazybytes.customer.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerProjection {

    private final ICustomerService iCustomerService;

    @EventHandler
    public void on(CustomerCreatedEvent customerCreatedEvent) {
        iCustomerService.createCustomer(customerCreatedEvent);
    }

    @EventHandler
    public void on(CustomerUpdatedEvent customerUpdatedEvent) {
        iCustomerService.updateCustomer(customerUpdatedEvent);
    }

    public void on(CustomerDeletedEvent customerDeletedEvent){
        iCustomerService.deleteCustomer(customerDeletedEvent.getCustomerId());
    }
}
