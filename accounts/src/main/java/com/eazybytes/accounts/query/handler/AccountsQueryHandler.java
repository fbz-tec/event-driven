package com.eazybytes.accounts.query.handler;

import com.eazybytes.accounts.dto.AccountsDto;
import com.eazybytes.accounts.query.FindAccountQuery;
import com.eazybytes.accounts.service.IAccountsService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountsQueryHandler {
    private final IAccountsService iAccountsService;

    @QueryHandler
    public AccountsDto findAccount(FindAccountQuery findAccountQuery) {
        return iAccountsService.fetchAccount(findAccountQuery.getMobileNumber());
    }
}
