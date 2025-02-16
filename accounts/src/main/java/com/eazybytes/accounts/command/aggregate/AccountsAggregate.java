package com.eazybytes.accounts.command.aggregate;

import com.eazybytes.accounts.command.CreateAccountCommand;
import com.eazybytes.accounts.command.DeleteAccountCommand;
import com.eazybytes.accounts.command.UpdateAccountCommand;
import com.eazybytes.accounts.command.event.AccountCreatedEvent;
import com.eazybytes.accounts.command.event.AccountDeletedEvent;
import com.eazybytes.accounts.command.event.AccountUpdatedEvent;
import com.eazybytes.common.event.AccountDataChangedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
public class AccountsAggregate {

    @AggregateIdentifier
    private Long accountNumber;
    private String mobileNumber;
    private String accountType;
    private String branchAddress;
    private boolean activeSw;

    @CommandHandler
    public AccountsAggregate(CreateAccountCommand command) {
        AccountCreatedEvent event = new AccountCreatedEvent();
        AccountDataChangedEvent accountDataChangedEvent = new AccountDataChangedEvent();
        BeanUtils.copyProperties(command, event);
        BeanUtils.copyProperties(command, accountDataChangedEvent);
        AggregateLifecycle.apply(event)
                .andThen(() -> AggregateLifecycle.apply(accountDataChangedEvent));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountNumber = event.getAccountNumber();
        this.mobileNumber = event.getMobileNumber();
        this.accountType = event.getAccountType();
        this.branchAddress = event.getBranchAddress();
        this.activeSw = event.isActiveSw();
    }

    @CommandHandler
    public void handle(UpdateAccountCommand command){
        AccountUpdatedEvent event = new AccountUpdatedEvent();
        AccountDataChangedEvent accountDataChangedEvent = new AccountDataChangedEvent();
        BeanUtils.copyProperties(command, event);
        BeanUtils.copyProperties(command, accountDataChangedEvent);
        AggregateLifecycle.apply(event);
        AggregateLifecycle.apply(accountDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(AccountUpdatedEvent event){
        this.accountType = event.getAccountType();
        this.branchAddress = event.getBranchAddress();
    }

    @CommandHandler
    public void handle(DeleteAccountCommand command){
        AccountDeletedEvent event = new AccountDeletedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(AccountDeletedEvent event){
        this.accountNumber = event.getAccountNumber();
        this.activeSw = event.isActiveSw();
    }

}
