package com.eazybytes.customer.command.event;

/*
* NOUN+VERB(PastTense)+Event naming convention
*/

import lombok.Data;

@Data
public class CustomerCreatedEvent {

    private String customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private boolean activeSw;
}
