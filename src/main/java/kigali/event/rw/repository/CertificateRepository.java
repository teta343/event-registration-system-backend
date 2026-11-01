package kigali.event.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.event.rw.domain.Certificate;
import kigali.event.rw.domain.Registration;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, UUID> {

    boolean existsByRegistration(Registration registration);

    @Query("SELECT c FROM Certificate c WHERE c.registration.participant.email = :email ORDER BY c.issuedDate DESC")
    List<Certificate> findByParticipantEmail(@Param("email") String email);

}
