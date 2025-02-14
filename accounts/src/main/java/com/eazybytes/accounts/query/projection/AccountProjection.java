package com.eazybytes.accounts.query.projection;

import com.eazybytes.accounts.command.event.AccountCreatedEvent;
import com.eazybytes.accounts.command.event.AccountDeletedEvent;
import com.eazybytes.accounts.command.event.AccountUpdatedEvent;
import com.eazybytes.accounts.service.IAccountsService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("accounts-group")
public class AccountProjection {

    private final IAccountsService accountsService;

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        accountsService.createAccount(accountCreatedEvent);
    }

    @EventHandler
    public void on(AccountUpdatedEvent accountUpdatedEvent){
        accountsService.updateAccount(accountUpdatedEvent) ;
    }

    @EventHandler
    public void on(AccountDeletedEvent accountDeletedEvent){
        accountsService.deleteAccount(accountDeletedEvent);
    }

}
