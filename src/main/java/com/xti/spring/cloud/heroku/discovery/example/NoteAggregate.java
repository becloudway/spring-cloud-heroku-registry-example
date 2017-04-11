package com.xti.spring.cloud.heroku.discovery.example;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class NoteAggregate {

    @AggregateIdentifier
    private String id;
    private String text;

    public NoteAggregate() {
    }

    @CommandHandler
    public NoteAggregate(CreateNoteCommand command) {
        System.out.println("Created note with id: " + command.getNoteId());
        apply(new NoteCreatedEvent(command.getNoteId(), command.getText()));
    }

    @EventSourcingHandler
    public void on(NoteCreatedEvent event) {
        this.id = event.getNoteId();
        this.text = event.getText();
    }
}
