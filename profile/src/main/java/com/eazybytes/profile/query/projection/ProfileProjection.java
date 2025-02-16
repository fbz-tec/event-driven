package com.eazybytes.profile.query.projection;

import com.eazybytes.common.event.AccountDataChangedEvent;
import com.eazybytes.common.event.CardDataChangedEvent;
import com.eazybytes.common.event.CustomerDataChangedEvent;
import com.eazybytes.common.event.LoanDataChangedEvent;
import com.eazybytes.profile.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("profile-group")
public class ProfileProjection {

    private final IProfileService iProfileService;

    @EventHandler
    public void on(CustomerDataChangedEvent event) {
        iProfileService.handleCustomerDataChangedEvent(event);
    }

    @EventHandler
    public void on(AccountDataChangedEvent event) {
        iProfileService.handleAccountDataChangedEvent(event);
    }

    @EventHandler
    public void on(CardDataChangedEvent event) {
        iProfileService.handleCardDataChangedEvent(event);
    }

    @EventHandler
    public void on(LoanDataChangedEvent event) {
        iProfileService.handleLoanDataChangedEvent(event);
    }
}
