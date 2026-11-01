package kigali.event.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.event.rw.domain.EventType;
import kigali.event.rw.repository.EventTypeRepository;

@Service
public class EventTypeService {

    @Autowired
    private EventTypeRepository eventTypeRepo;

    public String saveEventType(EventType eventType) {
        eventTypeRepo.save(eventType);
        return "Event type saved successfully";
    }

    public List<EventType> getAllEventTypes() {
        return eventTypeRepo.findAll();
    }

    public Optional<EventType> getEventTypeById(UUID id) {
        return eventTypeRepo.findById(id);
    }

    public List<EventType> getUnusedEventTypes() {
        return eventTypeRepo.findUnusedEventTypes();
    }

    public String updateEventType(UUID id, EventType eventType) {
        Optional<EventType> existing = eventTypeRepo.findById(id);
        if (existing.isEmpty()) {
            return "The event type with that id does not exist";
        }

        EventType updatedEventType = existing.get();
        updatedEventType.setName(eventType.getName());
        eventTypeRepo.save(updatedEventType);
        return "Event type updated successfully";
    }

    public String deleteEventType(UUID id) {
        Optional<EventType> existing = eventTypeRepo.findById(id);
        if (existing.isEmpty()) {
            return "The event type with that id does not exist";
        }

        eventTypeRepo.deleteById(id);
        return "Event type deleted successfully";
    }

}
