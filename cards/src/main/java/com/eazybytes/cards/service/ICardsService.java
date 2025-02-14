package com.eazybytes.cards.service;

import com.eazybytes.cards.command.event.CardCreatedEvent;
import com.eazybytes.cards.command.event.CardDeletedEvent;
import com.eazybytes.cards.command.event.CardUpdatedEvent;
import com.eazybytes.cards.dto.CardsDto;

public interface ICardsService {

    /**
     *
     * @param cardCreatedEvent - cardCreatedEvent Object
     */
    void createCard(CardCreatedEvent cardCreatedEvent);

    /**
     *
     * @param mobileNumber - Input mobile Number
     *  @return Card Details based on a given mobileNumber
     */
    CardsDto fetchCard(String mobileNumber);

    /**
     *
     * @param cardUpdatedEvent - CardUpdatedEvent Object
     * @return boolean indicating if the update of card details is successful or not
     */
    boolean updateCard(CardUpdatedEvent cardUpdatedEvent);

    /**
     *
     * @param cardDeletedEvent - CardDeletedEvent Object
     * @return boolean indicating if the delete of card details is successful or not
     */
    boolean deleteCard(CardDeletedEvent cardDeletedEvent);

}
