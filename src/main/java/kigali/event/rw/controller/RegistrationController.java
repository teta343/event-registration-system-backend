package kigali.event.rw.controller;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import kigali.event.rw.domain.Registration;
import kigali.event.rw.domain.RegistrationStatus;
import kigali.event.rw.service.RegistrationService;

@RestController
@RequestMapping(value = "/api/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveRegistration(@RequestBody Registration registration) {
        String returnedMessage = registrationService.saveRegistration(registration);

        if (returnedMessage.equals("Registration saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        } else if (returnedMessage.equals("Registration date cannot be in the past")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllRegistrations() {
        return new ResponseEntity<>(registrationService.getAllRegistrations(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getRegistrationById(@PathVariable UUID id) {
        Optional<Registration> registration = registrationService.getRegistrationById(id);
        if (registration.isPresent()) {
            return new ResponseEntity<>(registration.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The registration with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/by-status")
    public ResponseEntity<?> getRegistrationsByStatus(@RequestParam RegistrationStatus status) {
        return new ResponseEntity<>(registrationService.getRegistrationsByStatus(status), HttpStatus.OK);
    }

    @GetMapping(value = "/between")
    public ResponseEntity<?> getRegistrationsBetween(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return new ResponseEntity<>(registrationService.getRegistrationsBetween(startDate, endDate), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateRegistration(@PathVariable UUID id, @RequestBody Registration registration) {
        String returnedMessage = registrationService.updateRegistration(id, registration);

        if (returnedMessage.equals("Registration updated successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable UUID id, @RequestParam RegistrationStatus status) {
        String returnedMessage = registrationService.updateStatus(id, status);

        if (returnedMessage.equals("Registration status updated successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else if (returnedMessage.equals("The registration with that id does not exist")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteRegistration(@PathVariable UUID id) {
        String returnedMessage = registrationService.deleteRegistration(id);

        if (returnedMessage.equals("Registration deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else if (returnedMessage.equals("That registration has certificates, delete them first")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/stats/by-status")
    public ResponseEntity<?> getStatsByStatus() {
        return new ResponseEntity<>(registrationService.getRegistrationStatsByStatus(), HttpStatus.OK);
    }

    @PatchMapping(value = "/cancel-day")
    public ResponseEntity<?> cancelDay(@RequestParam UUID organizerId, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        return new ResponseEntity<>(registrationService.cancelOrganizerDay(organizerId, localDate), HttpStatus.OK);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<?> getPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "eventDate,desc") String sort) {

        String[] parts = sort.split(",");
        Sort.Direction direction = parts.length > 1 && parts[1].equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, parts[0]));

        return new ResponseEntity<>(registrationService.getRegistrationsPage(pageable), HttpStatus.OK);
    }

}
