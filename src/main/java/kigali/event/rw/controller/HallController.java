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

import kigali.event.rw.domain.Hall;
import kigali.event.rw.service.HallService;

@RestController
@RequestMapping(value = "/api/halls")
public class HallController {

    @Autowired
    private HallService hallService;
    

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveHall(@RequestBody Hall hall) {
        String returnedMessage = hallService.saveHall(hall);

        if (returnedMessage.equals("Hall saved successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllHalls() {
        return new ResponseEntity<>(hallService.getAllHalls(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getHallById(@PathVariable UUID id) {
        Optional<Hall> hall = hallService.getHallById(id);
        if (hall.isPresent()) {
            return new ResponseEntity<>(hall.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("The hall with that id does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/free")
    public ResponseEntity<?> getFreeHalls() {
        return new ResponseEntity<>(hallService.getFreeHalls(), HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateHall(@PathVariable UUID id, @RequestBody Hall hall) {
        String returnedMessage = hallService.updateHall(id, hall);

        if (returnedMessage.equals("Hall updated successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteHall(@PathVariable UUID id) {
        String returnedMessage = hallService.deleteHall(id);

        if (returnedMessage.equals("Hall deleted successfully")) {
            return new ResponseEntity<>(returnedMessage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(returnedMessage, HttpStatus.NOT_FOUND);
        }
    }

}
