package kigali.event.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.event.rw.domain.EventType;
import kigali.event.rw.domain.Hall;
import kigali.event.rw.domain.Organizer;
import kigali.event.rw.repository.EventTypeRepository;
import kigali.event.rw.repository.HallRepository;
import kigali.event.rw.repository.OrganizerRepository;

@Service
public class OrganizerService {

    @Autowired
    private OrganizerRepository organizerRepo;

    @Autowired
    private EventTypeRepository eventTypeRepo;

    @Autowired
    private HallRepository hallRepo;

    public String saveOrganizer(Organizer organizer) {
        organizerRepo.save(organizer);
        return "Organizer saved successfully";
    }

    public List<Organizer> getAllOrganizers() {
        return organizerRepo.findAll();
    }

    public Optional<Organizer> getOrganizerById(UUID id) {
        return organizerRepo.findById(id);
    }

    public List<Organizer> getOrganizersByEventType(String name) {
        return organizerRepo.findByEventTypeName(name);
    }

    public String updateOrganizer(UUID id, Organizer organizer) {
        Optional<Organizer> existing = organizerRepo.findById(id);
        if (existing.isEmpty()) {
            return "The organizer with that id does not exist";
        }

        Organizer updatedOrganizer = existing.get();
        updatedOrganizer.setFirstName(organizer.getFirstName());
        updatedOrganizer.setLastName(organizer.getLastName());
        updatedOrganizer.setEmail(organizer.getEmail());
        updatedOrganizer.setHall(organizer.getHall());
        organizerRepo.save(updatedOrganizer);
        return "Organizer updated successfully";
    }

    public String deleteOrganizer(UUID id) {
        Optional<Organizer> existing = organizerRepo.findById(id);
        if (existing.isEmpty()) {
            return "The organizer with that id does not exist";
        }

        organizerRepo.deleteById(id);
        return "Organizer deleted successfully";
    }

    public String addEventType(UUID organizerId, UUID eventTypeId) {
        Optional<Organizer> organizerOpt = organizerRepo.findById(organizerId);
        if (organizerOpt.isEmpty()) {
            return "The organizer with that id does not exist";
        }

        Optional<EventType> eventTypeOpt = eventTypeRepo.findById(eventTypeId);
        if (eventTypeOpt.isEmpty()) {
            return "That event type does not exist";
        }

        Organizer organizer = organizerOpt.get();
        EventType eventType = eventTypeOpt.get();

        if (organizer.getEventTypes().contains(eventType)) {
            return "The organizer already has that event type";
        }

        organizer.getEventTypes().add(eventType);
        organizerRepo.save(organizer);
        return "Event type added to the organizer";
    }

    public String changeHall(UUID organizerId, int hallNumber) {
        Optional<Organizer> organizerOpt = organizerRepo.findById(organizerId);
        if (organizerOpt.isEmpty()) {
            return "The organizer with that id does not exist";
        }

        Optional<Hall> hallOpt = hallRepo.findByHallNumber(hallNumber);
        if (hallOpt.isEmpty()) {
            return "The hall with that hallNumber does not exist";
        }

        Hall hall = hallOpt.get();

        if (organizerRepo.existsByHall(hall)) {
            return "That hall is taken by another organizer";
        }

        Organizer organizer = organizerOpt.get();
        organizer.setHall(hall);
        organizerRepo.save(organizer);
        return "Hall changed successfully";
    }

}
