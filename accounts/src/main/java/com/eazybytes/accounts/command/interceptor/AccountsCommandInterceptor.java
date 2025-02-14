package com.eazybytes.accounts.command.interceptor;

import com.eazybytes.accounts.command.CreateAccountCommand;
import com.eazybytes.accounts.command.DeleteAccountCommand;
import com.eazybytes.accounts.command.UpdateAccountCommand;
import com.eazybytes.accounts.constants.AccountsConstants;
import com.eazybytes.accounts.entity.Accounts;
import com.eazybytes.accounts.exception.AccountAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class AccountsCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final AccountsRepository accountsRepository;

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) ->{
            if (CreateAccountCommand.class.equals(command.getPayload().getClass())) {
                CreateAccountCommand createAccountCommand = (CreateAccountCommand) command.getPayload();
                Optional<Accounts> optionalAccounts= accountsRepository.findByMobileNumberAndActiveSw(createAccountCommand.mobileNumber(),
                        AccountsConstants.ACTIVE_SW);
                if(optionalAccounts.isPresent()){
                    throw new AccountAlreadyExistsException("Account already registered with given mobileNumber "
                            +createAccountCommand.mobileNumber());
                }
            } else if (UpdateAccountCommand.class.equals(command.getPayload().getClass())) {
                UpdateAccountCommand updateAccountCommand = (UpdateAccountCommand) command.getPayload();

                accountsRepository.findByMobileNumberAndActiveSw(updateAccountCommand.mobileNumber(),
                        AccountsConstants.ACTIVE_SW).orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber",
                        updateAccountCommand.mobileNumber()));
            } else if (DeleteAccountCommand.class.equals(command.getPayload().getClass())) {
                DeleteAccountCommand deleteAccountCommand = (DeleteAccountCommand) command.getPayload();
                accountsRepository.findByAccountNumberAndActiveSw(deleteAccountCommand.accountNumber(),
                        AccountsConstants.ACTIVE_SW).orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber",
                        deleteAccountCommand.accountNumber().toString()));

            }
            return command;
        };
    }
}
