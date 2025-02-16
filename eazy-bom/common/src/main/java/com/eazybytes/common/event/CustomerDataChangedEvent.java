package com.eazybytes.common.event;

import lombok.Data;

@Data
public class CustomerDataChangedEvent {
    private String mobileNumber;
    private String name;
    private boolean activeSw;
}
