package kigali.event.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.event.rw.domain.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {

    List<Participant> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    @Query("SELECT DISTINCT p FROM Participant p JOIN Registration r ON r.participant = p WHERE r.organizer.id = :organizerId")
    List<Participant> findParticipantsByOrganizer(@Param("organizerId") UUID organizerId);

}
