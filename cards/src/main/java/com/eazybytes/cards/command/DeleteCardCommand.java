package com.eazybytes.cards.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteCardCommand {
    private final Long cardNumber;
    private final boolean activeSw;
}
