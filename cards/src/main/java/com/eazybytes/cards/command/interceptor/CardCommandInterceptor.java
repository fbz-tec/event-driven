package com.eazybytes.cards.command.interceptor;

import com.eazybytes.cards.command.CreateCardCommand;
import com.eazybytes.cards.command.DeleteCardCommand;
import com.eazybytes.cards.command.UpdateCardCommand;
import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.exception.CardAlreadyExistsException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.repository.CardsRepository;
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
public class CardCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final CardsRepository cardsRepository;

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            if (CreateCardCommand.class.equals(command.getPayload().getClass())) {
                CreateCardCommand createCardCommand = (CreateCardCommand) command.getPayload();
                Optional<Cards> optionalCard = cardsRepository.findByMobileNumberAndActiveSw(
                        createCardCommand.getMobileNumber(),
                        CardsConstants.ACTIVE_SW);
                if (optionalCard.isPresent()) {
                    throw new CardAlreadyExistsException("Card already registered with given mobileNumber "
                            + createCardCommand.getMobileNumber());
                }
            } else if (UpdateCardCommand.class.equals(command.getPayload().getClass())) {
                UpdateCardCommand updateCardCommand = (UpdateCardCommand) command.getPayload();
                cardsRepository.findByMobileNumberAndActiveSw(updateCardCommand.getMobileNumber(),
                        CardsConstants.ACTIVE_SW).orElseThrow(() -> new ResourceNotFoundException("Card", "CardNumber",
                        updateCardCommand.getCardNumber().toString()));

            } else if (DeleteCardCommand.class.equals(command.getPayload().getClass())) {
                DeleteCardCommand deleteCardCommand = (DeleteCardCommand) command.getPayload();
                cardsRepository.findById(deleteCardCommand.getCardNumber())
                        .orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber",
                                deleteCardCommand.getCardNumber().toString())
                        );

            }
            return command;
        };
    }
}
