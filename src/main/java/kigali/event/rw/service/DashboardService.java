package kigali.event.rw.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.event.rw.domain.RegistrationStatus;
import kigali.event.rw.repository.CertificateRepository;
import kigali.event.rw.repository.HallRepository;
import kigali.event.rw.repository.OrganizerRepository;
import kigali.event.rw.repository.ParticipantRepository;
import kigali.event.rw.repository.RegistrationRepository;

@Service
public class DashboardService {

    @Autowired
    private ParticipantRepository participantRepo;

    @Autowired
    private OrganizerRepository organizerRepo;

    @Autowired
    private HallRepository hallRepo;

    @Autowired
    private RegistrationRepository registrationRepo;

    @Autowired
    private CertificateRepository certificateRepo;

    public Map<String, Long> getSummary() {
        Map<String, Long> map = new LinkedHashMap<>();
        map.put("participants", participantRepo.count());
        map.put("organizers", organizerRepo.count());
        map.put("freeHalls", (long) hallRepo.findFreeHalls().size());
        map.put("registrationsToday", registrationRepo.countByEventDate(LocalDate.now()));
        map.put("registered", registrationRepo.countByStatus(RegistrationStatus.REGISTERED));
        map.put("certificates", certificateRepo.count());
        return map;
    }

}
