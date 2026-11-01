package kigali.event.rw.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kigali.event.rw.domain.Organizer;
import kigali.event.rw.service.OrganizerService;

@RestController
@RequestMapping(value = "/api/organizers")
public class OrganizerController {

    @Autowired
    private OrganizerService organizerService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveOrganizer(@RequestBody Organizer organizer) {
        String returnedMessage = organizerService.saveOrganizer(organizer);

        if (returnedMessage.equals("Organizer saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllOrganizers() {
        return new ResponseEntity<>(organizerService.getAllOrganizers(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getOrganizerById(@PathVariable UUID id) {
        Optional<Organizer> organizer = organizerService.getOrganizerById(id);
        if (organizer.isPresent()) {
            return new ResponseEntity<>(organizer.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The organizer with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/by-event-type")
    public ResponseEntity<?> getOrganizersByEventType(@RequestParam String name) {
        return new ResponseEntity<>(organizerService.getOrganizersByEventType(name), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateOrganizer(@PathVariable UUID id, @RequestBody Organizer organizer) {
        String returnedMessage = organizerService.updateOrganizer(id, organizer);

        if (returnedMessage.equals("Organizer updated successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteOrganizer(@PathVariable UUID id) {
        String returnedMessage = organizerService.deleteOrganizer(id);

        if (returnedMessage.equals("Organizer deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{organizerId}/event-types/{eventTypeId}")
    public ResponseEntity<?> addEventType(@PathVariable UUID organizerId, @PathVariable UUID eventTypeId) {
        String returnedMessage = organizerService.addEventType(organizerId, eventTypeId);

        if (returnedMessage.equals("Event type added to the organizer")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else if (returnedMessage.equals("The organizer already has that event type")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/{organizerId}/hall")
    public ResponseEntity<?> changeHall(@PathVariable UUID organizerId, @RequestParam int hallNumber) {
        String returnedMessage = organizerService.changeHall(organizerId, hallNumber);

        if (returnedMessage.equals("Hall changed successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else if (returnedMessage.equals("That hall is taken by another organizer")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

}
