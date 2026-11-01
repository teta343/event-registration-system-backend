package kigali.event.rw.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.event.rw.domain.Participant;
import kigali.event.rw.service.ParticipantService;

@RestController
@RequestMapping(value = "/api/participants")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveParticipant(@RequestBody Participant participant) {
        String returnedMessage = participantService.saveParticipant(participant);

        if (returnedMessage.equals("Participant saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllParticipants() {
        return new ResponseEntity<>(participantService.getAllParticipants(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getParticipantById(@PathVariable UUID id) {
        Optional<Participant> participant = participantService.getParticipantById(id);
        if (participant.isPresent()) {
            return new ResponseEntity<>(participant.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The participant with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/by-last-name")
    public ResponseEntity<?> getParticipantsByLastName(@RequestParam String lastName) {
        return new ResponseEntity<>(participantService.getParticipantsByLastName(lastName), HttpStatus.OK);
    }

    @GetMapping(value = "/of-organizer/{organizerId}")
    public ResponseEntity<?> getParticipantsOfOrganizer(@PathVariable UUID organizerId) {
        Object response = participantService.getParticipantsByOrganizer(organizerId);
        if (response instanceof String) {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateParticipant(@PathVariable UUID id, @RequestBody Participant participant) {
        String returnedMessage = participantService.updateParticipant(id, participant);

        if (returnedMessage.equals("Participant updated successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteParticipant(@PathVariable UUID id) {
        String returnedMessage = participantService.deleteParticipant(id);

        if (returnedMessage.equals("Participant deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

}
