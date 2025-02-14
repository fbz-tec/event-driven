package com.eazybytes.cards.command.aggregate;

import com.eazybytes.cards.command.CreateCardCommand;
import com.eazybytes.cards.command.DeleteCardCommand;
import com.eazybytes.cards.command.UpdateCardCommand;
import com.eazybytes.cards.command.event.CardCreatedEvent;
import com.eazybytes.cards.command.event.CardDeletedEvent;
import com.eazybytes.cards.command.event.CardUpdatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
public class CardAggregate {

    @AggregateIdentifier
    private Long cardNumber;
    private String mobileNumber;
    private String cardType;
    private int totalLimit;
    private int amountUsed;
    private int availableAmount;
    private boolean activeSw;

    @CommandHandler
    public CardAggregate(CreateCardCommand command) {
        CardCreatedEvent event = new CardCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CardCreatedEvent event) {
        this.cardNumber = event.getCardNumber();
        this.mobileNumber = event.getMobileNumber();
        this.cardType = event.getCardType();
        this.totalLimit = event.getTotalLimit();
        this.amountUsed = event.getAmountUsed();
        this.availableAmount = event.getAvailableAmount();
        this.activeSw = event.isActiveSw();
    }

    @CommandHandler
    public void handle(UpdateCardCommand command) {
        CardUpdatedEvent event = new CardUpdatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CardUpdatedEvent event){
        this.cardType = event.getCardType();
        this.totalLimit = event.getTotalLimit();
        this.amountUsed = event.getAmountUsed();
        this.availableAmount = event.getAvailableAmount();
    }

    @CommandHandler
    public void handle(DeleteCardCommand command){
        CardDeletedEvent event = new CardDeletedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CardDeletedEvent event){
        this.cardNumber = event.getCardNumber();
        this.activeSw = event.isActiveSw();
    }

}
