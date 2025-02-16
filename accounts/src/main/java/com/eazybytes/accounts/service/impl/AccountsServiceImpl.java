package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.command.event.AccountCreatedEvent;
import com.eazybytes.accounts.command.event.AccountDeletedEvent;
import com.eazybytes.accounts.command.event.AccountUpdatedEvent;
import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.exception.AccountAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountsMapper;
import com.eazybytes.accounts.repository.AccountsRepository;
import com.eazybytes.accounts.service.IAccountsService;
import com.eazybytes.common.event.AccountDataChangedEvent;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountsServiceImpl  implements IAccountsService {

    private AccountsRepository accountsRepository;
    private EventGateway eventGateway;

    /**
     * @param accountCreatedEvent - AccountCreatedEvent Object
     */
    @Override
    public void createAccount(AccountCreatedEvent accountCreatedEvent) {
        Optional<Accounts> optionalAccounts= accountsRepository
                .findByMobileNumberAndActiveSw(accountCreatedEvent.getMobileNumber(),
                AccountsConstants.ACTIVE_SW);
        if(optionalAccounts.isPresent()){
            throw new AccountAlreadyExistsException("Account already registered with given mobileNumber "
                    +accountCreatedEvent.getMobileNumber());
        }
        Accounts accounts =new Accounts();
        BeanUtils.copyProperties(accountCreatedEvent, accounts);
        accountsRepository.save(accounts);
    }
    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    @Override
    public AccountsDto fetchAccount(String mobileNumber) {
        Accounts account = accountsRepository.findByMobileNumberAndActiveSw(mobileNumber, AccountsConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber", mobileNumber)
        );
        AccountsDto accountsDto = AccountsMapper.mapToAccountsDto(account, new AccountsDto());
        return accountsDto;
    }

    /**
     * @param accountUpdatedEvent - AccountUpdatedEvent Object
     * @return boolean indicating if the update of Account details is successful or not
     */
    @Override
    public boolean updateAccount(AccountUpdatedEvent accountUpdatedEvent) {
        Accounts account = accountsRepository.findByMobileNumberAndActiveSw(accountUpdatedEvent.getMobileNumber(),
                AccountsConstants.ACTIVE_SW).orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber",
                accountUpdatedEvent.getMobileNumber()));
        AccountsMapper.mapEventToAccount(accountUpdatedEvent, account);
        accountsRepository.save(account);
        return  true;
    }

    /**
     * @param accountDeletedEvent - AccountDeletedEvent Object
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(AccountDeletedEvent accountDeletedEvent) {
        Accounts account = accountsRepository.findById(accountDeletedEvent.getAccountNumber())
                .orElseThrow(
                () -> new ResourceNotFoundException("Account", "accountNumber",
                        accountDeletedEvent.getAccountNumber().toString())
        );
        account.setActiveSw(accountDeletedEvent.isActiveSw());
        accountsRepository.save(account);
        AccountDataChangedEvent accountDataChangedEvent = new AccountDataChangedEvent();
        accountDataChangedEvent.setMobileNumber(account.getMobileNumber());
        accountDataChangedEvent.setAccountNumber(0L);
        eventGateway.publish(accountDataChangedEvent);
        return true;
    }


}
