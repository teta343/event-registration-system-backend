package kigali.event.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kigali.event.rw.domain.Registration;
import kigali.event.rw.domain.RegistrationStatus;
import kigali.event.rw.repository.CertificateRepository;
import kigali.event.rw.repository.RegistrationRepository;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepo;

    @Autowired
    private CertificateRepository certificateRepo;

    public String saveRegistration(Registration registration) {
        if (registration.getEventDate().isBefore(LocalDate.now())) {
            return "Registration date cannot be in the past";
        }

        if (registrationRepo.existsByOrganizerAndEventDateAndStatusNot(
                registration.getOrganizer(), registration.getEventDate(), RegistrationStatus.CANCELLED)) {
            return "Organizer already has a registration on that date";
        }

        registration.setStatus(RegistrationStatus.REGISTERED);
        registrationRepo.save(registration);
        return "Registration saved successfully";
    }

    public List<Registration> getAllRegistrations() {
        return registrationRepo.findAll();
    }

    public Optional<Registration> getRegistrationById(UUID id) {
        return registrationRepo.findById(id);
    }

    public List<Registration> getRegistrationsByStatus(RegistrationStatus status) {
        return registrationRepo.findByStatusOrderByEventDateAsc(status);
    }

    public List<Registration> getRegistrationsBetween(LocalDate start, LocalDate end) {
        return registrationRepo.findByEventDateBetweenOrderByEventDateAsc(start, end);
    }

    public String updateRegistration(UUID id, Registration registration) {
        Optional<Registration> existing = registrationRepo.findById(id);
        if (existing.isEmpty()) {
            return "The registration with that id does not exist";
        }

        Registration updatedRegistration = existing.get();
        updatedRegistration.setEventDate(registration.getEventDate());
        updatedRegistration.setEventName(registration.getEventName());
        updatedRegistration.setSeats(registration.getSeats());
        updatedRegistration.setParticipant(registration.getParticipant());
        updatedRegistration.setOrganizer(registration.getOrganizer());
        registrationRepo.save(updatedRegistration);
        return "Registration updated successfully";
    }

    public String updateStatus(UUID id, RegistrationStatus newStatus) {
        Optional<Registration> existing = registrationRepo.findById(id);
        if (existing.isEmpty()) {
            return "The registration with that id does not exist";
        }

        Registration registration = existing.get();
        RegistrationStatus current = registration.getStatus();

        if (current == RegistrationStatus.REGISTERED && (newStatus == RegistrationStatus.PAID || newStatus == RegistrationStatus.CANCELLED)) {
            registration.setStatus(newStatus);
            registrationRepo.save(registration);
            return "Registration status updated successfully";
        } else if (current == RegistrationStatus.PAID && (newStatus == RegistrationStatus.ATTENDED || newStatus == RegistrationStatus.CANCELLED)) {
            registration.setStatus(newStatus);
            registrationRepo.save(registration);
            return "Registration status updated successfully";
        } else {
            return "Cannot change status from " + current + " to " + newStatus;
        }
    }

    public String deleteRegistration(UUID id) {
        Optional<Registration> existing = registrationRepo.findById(id);
        if (existing.isEmpty()) {
            return "The registration with that id does not exist";
        }

        if (certificateRepo.existsByRegistration(existing.get())) {
            return "That registration has certificates, delete them first";
        }

        registrationRepo.deleteById(id);
        return "Registration deleted successfully";
    }

    public List<Object[]> getRegistrationStatsByStatus() {
        return registrationRepo.countByStatusGrouped();
    }

    @Transactional
    public String cancelOrganizerDay(UUID organizerId, LocalDate date) {
        int count = registrationRepo.cancelOrganizerDay(organizerId, date);
        return count + " registrations cancelled";
    }

    public Page<Registration> getRegistrationsPage(Pageable pageable) {
        return registrationRepo.findAll(pageable);
    }

}
