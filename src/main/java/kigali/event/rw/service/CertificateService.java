package kigali.event.rw.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kigali.event.rw.domain.Certificate;
import kigali.event.rw.domain.Registration;
import kigali.event.rw.domain.RegistrationStatus;
import kigali.event.rw.repository.CertificateRepository;
import kigali.event.rw.repository.RegistrationRepository;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository certificateRepo;

    @Autowired
    private RegistrationRepository registrationRepo;

    public String saveCertificate(UUID registrationId, Certificate certificate) {
        Optional<Registration> registrationOpt = registrationRepo.findById(registrationId);
        if (registrationOpt.isEmpty()) {
            return "The registration with that id does not exist";
        }

        Registration registration = registrationOpt.get();
        if (registration.getStatus() != RegistrationStatus.ATTENDED) {
            return "Certificates are only for ATTENDED registrations";
        }

        certificate.setIssuedDate(LocalDate.now());
        certificate.setRegistration(registration);
        certificateRepo.save(certificate);
        return "Certificate saved successfully";
    }

    public List<Certificate> getCertificatesByParticipantEmail(String email) {
        return certificateRepo.findByParticipantEmail(email);
    }

    public String deleteCertificate(UUID id) {
        Optional<Certificate> existing = certificateRepo.findById(id);
        if (existing.isEmpty()) {
            return "The certificate with that id does not exist";
        }

        certificateRepo.deleteById(id);
        return "Certificate deleted successfully";
    }

}
