package com.xti.spring.cloud.heroku.discovery.example;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.UUID;

@RestController
@RequestMapping(path = "/note")
public class NoteController {

    private final CommandGateway commandGateway;

    @Autowired
    public NoteController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @RequestMapping(method = RequestMethod.POST)
    public CreateNoteResponse createNoteRequest(@RequestBody CreateNoteRequest createNoteRequest){
        for (int i = 0; i < createNoteRequest.getCount(); i++) {
            commandGateway.sendAndWait(new CreateNoteCommand(UUID.randomUUID().toString(), createNoteRequest.getText() + i));
        }
        return new CreateNoteResponse(createNoteRequest.getCount());
    }

    @RequestMapping(path = "/{noteId}", method = RequestMethod.POST)
    public UpdateNoteResponse updateNoteRequest(@PathParam("noteId") String noteId, @RequestBody UpdateNoteRequest updateNoteRequest){

        commandGateway.sendAndWait(new UpdateNoteCommand(noteId, updateNoteRequest.getText()));

        return new UpdateNoteResponse(noteId);
    }
}
