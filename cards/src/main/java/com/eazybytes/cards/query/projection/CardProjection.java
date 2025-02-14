package com.eazybytes.cards.query.projection;

import com.eazybytes.cards.command.event.CardCreatedEvent;
import com.eazybytes.cards.command.event.CardDeletedEvent;
import com.eazybytes.cards.command.event.CardUpdatedEvent;
import com.eazybytes.cards.service.ICardsService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("card-group")
public class CardProjection {

    private final ICardsService iCardsService;

    @EventHandler
    public void on(CardCreatedEvent cardCreatedEvent) {
        iCardsService.createCard(cardCreatedEvent);
    }

    @EventHandler
    public void on(CardDeletedEvent cardDeletedEvent) {
        iCardsService.deleteCard(cardDeletedEvent);
    }

    @EventHandler
    public void on(CardUpdatedEvent cardUpdatedEvent) {
        iCardsService.updateCard(cardUpdatedEvent);
    }
}
