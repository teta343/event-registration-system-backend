package kigali.event.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.event.rw.domain.Hall;
import kigali.event.rw.repository.HallRepository;

@Service
public class HallService {

    @Autowired
    private HallRepository hallRepo;

    public String saveHall(Hall hall) {
        hallRepo.save(hall);
        return "Hall saved successfully";
    }

    public List<Hall> getAllHalls() {
        return hallRepo.findAll();
    }

    public Optional<Hall> getHallById(UUID id) {
        return hallRepo.findById(id);
    }

    public List<Hall> getFreeHalls() {
        return hallRepo.findFreeHalls();
    }

    public String updateHall(UUID id, Hall hall) {
        Optional<Hall> existing = hallRepo.findById(id);
        if (existing.isEmpty()) {
            return "The hall with that id does not exist";
        }

        Hall updatedHall = existing.get();
        updatedHall.setHallNumber(hall.getHallNumber());
        updatedHall.setName(hall.getName());
        hallRepo.save(updatedHall);
        return "Hall updated successfully";
    }

    public String deleteHall(UUID id) {
        Optional<Hall> existing = hallRepo.findById(id);
        if (existing.isEmpty()) {
            return "The hall with that id does not exist";
        }

        hallRepo.deleteById(id);
        return "Hall deleted successfully";
    }

}
