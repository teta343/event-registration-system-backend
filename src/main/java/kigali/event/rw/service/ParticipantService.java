package kigali.event.rw.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.event.rw.domain.Participant;
import kigali.event.rw.repository.OrganizerRepository;
import kigali.event.rw.repository.ParticipantRepository;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository participantRepo;

    @Autowired
    private OrganizerRepository organizerRepo;

    public String saveParticipant(Participant participant) {
        participantRepo.save(participant);
        return "Participant saved successfully";
    }

    public List<Participant> getAllParticipants() {
        return participantRepo.findAll();
    }

    public Optional<Participant> getParticipantById(UUID id) {
        return participantRepo.findById(id);
    }

    public List<Participant> getParticipantsByLastName(String lastName) {
        return participantRepo.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }

    public Object getParticipantsByOrganizer(UUID organizerId) {
        if (!organizerRepo.existsById(organizerId)) {
            return "The organizer with that id does not exist";
        }
        return participantRepo.findParticipantsByOrganizer(organizerId);
    }

    public String updateParticipant(UUID id, Participant participant) {
        Optional<Participant> existing = participantRepo.findById(id);
        if (existing.isEmpty()) {
            return "The participant with that id does not exist";
        }

        Participant updatedParticipant = existing.get();
        updatedParticipant.setFirstName(participant.getFirstName());
        updatedParticipant.setLastName(participant.getLastName());
        updatedParticipant.setEmail(participant.getEmail());
        updatedParticipant.setPhone(participant.getPhone());
        updatedParticipant.setOrganization(participant.getOrganization());
        participantRepo.save(updatedParticipant);
        return "Participant updated successfully";
    }

    public String deleteParticipant(UUID id) {
        Optional<Participant> existing = participantRepo.findById(id);
        if (existing.isEmpty()) {
            return "The participant with that id does not exist";
        }

        participantRepo.deleteById(id);
        return "Participant deleted successfully";
    }

}
