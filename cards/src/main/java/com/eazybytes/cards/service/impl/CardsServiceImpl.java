package com.eazybytes.cards.service.impl;

import com.eazybytes.cards.command.event.CardCreatedEvent;
import com.eazybytes.cards.command.event.CardDeletedEvent;
import com.eazybytes.cards.command.event.CardUpdatedEvent;
import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.CardsDto;
import com.eazybytes.cards.entity.Cards;
import com.eazybytes.cards.exception.CardAlreadyExistsException;
import com.eazybytes.cards.exception.ResourceNotFoundException;
import com.eazybytes.cards.mapper.CardsMapper;
import com.eazybytes.cards.repository.CardsRepository;
import com.eazybytes.cards.service.ICardsService;
import com.eazybytes.common.event.CardDataChangedEvent;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;
    private EventGateway eventGateway;

    @Override
    public void createCard(CardCreatedEvent cardCreatedEvent) {
        Optional<Cards> optionalCard = cardsRepository.findByMobileNumberAndActiveSw(cardCreatedEvent.getMobileNumber(),
                CardsConstants.ACTIVE_SW);
        if (optionalCard.isPresent()) {
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " +
                    cardCreatedEvent.getMobileNumber());
        }
        Cards card = new Cards();
        BeanUtils.copyProperties(cardCreatedEvent, card);
        cardsRepository.save(card);
    }

    /**
     * @param mobileNumber - Input mobile Number
     * @return Card Details based on a given mobileNumber
     */
    @Override
    public CardsDto fetchCard(String mobileNumber) {
        Cards card = cardsRepository.findByMobileNumberAndActiveSw(mobileNumber, CardsConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
                );
        return CardsMapper.mapToCardsDto(card, new CardsDto());
    }

    @Override
    public boolean updateCard(CardUpdatedEvent cardUpdatedEvent) {
        Cards card = cardsRepository.findByMobileNumberAndActiveSw(cardUpdatedEvent.getMobileNumber(),
                CardsConstants.ACTIVE_SW).orElseThrow(() -> new ResourceNotFoundException("Card", "CardNumber",
                cardUpdatedEvent.getCardNumber().toString()));

        CardsMapper.mapEventToCard(cardUpdatedEvent, card);
        cardsRepository.save(card);
        return true;
    }

    @Override
    public boolean deleteCard(CardDeletedEvent cardDeletedEvent) {
        Cards card = cardsRepository.findById(cardDeletedEvent.getCardNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Card", "cardNumber", cardDeletedEvent.toString())
                );
        card.setActiveSw(cardDeletedEvent.isActiveSw());
        cardsRepository.save(card);
        CardDataChangedEvent cardDataChangedEvent = new CardDataChangedEvent();
        cardDataChangedEvent.setMobileNumber(card.getMobileNumber());
        cardDataChangedEvent.setCardNumber(0L);
        eventGateway.publish(cardDataChangedEvent);
        return true;
    }

}
