package com.eazybytes.accounts.command;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@Accessors(fluent = true)
public class DeleteAccountCommand{
        @TargetAggregateIdentifier
        private final Long accountNumber;
        private final boolean activeSw;
}
