package com.eazybytes.accounts.service;

import com.eazybytes.accounts.command.event.AccountCreatedEvent;
import com.eazybytes.accounts.command.event.AccountDeletedEvent;
import com.eazybytes.accounts.command.event.AccountUpdatedEvent;
import com.eazybytes.accounts.dto.AccountsDto;

public interface IAccountsService {

    /**
     *
     * @param accountCreatedEvent - AccountCreatedEvent Object
     */
    void createAccount(AccountCreatedEvent accountCreatedEvent);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    AccountsDto fetchAccount(String mobileNumber);

    /**
     *
     * @param accountUpdatedEvent - AccountUpdatedEvent Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    boolean updateAccount(AccountUpdatedEvent accountUpdatedEvent);

    /**
     *
     * @param accountDeletedEvent - AccountDeletedEvent Object
     * @return boolean indicating if the delete of Account details is successful or not
     */
    boolean deleteAccount(AccountDeletedEvent accountDeletedEvent);


}
