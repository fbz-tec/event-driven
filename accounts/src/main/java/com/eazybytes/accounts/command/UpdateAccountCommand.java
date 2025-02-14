package com.eazybytes.accounts.command;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Data
@Builder
@Accessors(fluent = true)
public class UpdateAccountCommand{
        @TargetAggregateIdentifier
        private final Long accountNumber;
        private final String mobileNumber;
        private final String accountType;
        private final String branchAddress;
        private final boolean activeSw;
}
