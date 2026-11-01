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
import org.springframework.web.bind.annotation.RestController;

import kigali.event.rw.domain.EventType;
import kigali.event.rw.service.EventTypeService;

@RestController
@RequestMapping(value = "/api/event-types")
public class EventTypeController {

    @Autowired
    private EventTypeService eventTypeService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveEventType(@RequestBody EventType eventType) {
        String returnedMessage = eventTypeService.saveEventType(eventType);

        if (returnedMessage.equals("Event type saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllEventTypes() {
        return new ResponseEntity<>(eventTypeService.getAllEventTypes(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getEventTypeById(@PathVariable UUID id) {
        Optional<EventType> eventType = eventTypeService.getEventTypeById(id);
        if (eventType.isPresent()) {
            return new ResponseEntity<>(eventType.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The event type with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/unused")
    public ResponseEntity<?> getUnusedEventTypes() {
        return new ResponseEntity<>(eventTypeService.getUnusedEventTypes(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateEventType(@PathVariable UUID id, @RequestBody EventType eventType) {
        String returnedMessage = eventTypeService.updateEventType(id, eventType);

        if (returnedMessage.equals("Event type updated successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteEventType(@PathVariable UUID id) {
        String returnedMessage = eventTypeService.deleteEventType(id);

        if (returnedMessage.equals("Event type deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

}
