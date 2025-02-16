package com.eazybytes.profile.service;

import com.eazybytes.common.event.AccountDataChangedEvent;
import com.eazybytes.common.event.CardDataChangedEvent;
import com.eazybytes.common.event.CustomerDataChangedEvent;
import com.eazybytes.common.event.LoanDataChangedEvent;
import com.eazybytes.profile.dto.ProfileDto;

public interface IProfileService {

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    ProfileDto fetchProfile(String mobileNumber);

    void handleCustomerDataChangedEvent(CustomerDataChangedEvent customerDataChangedEvent);

    void handleLoanDataChangedEvent(LoanDataChangedEvent loanDataChangedEvent);

    void handleAccountDataChangedEvent(AccountDataChangedEvent accountDataChangedEvent);

    void handleCardDataChangedEvent(CardDataChangedEvent cardDataChangedEvent);

}
