package kigali.event.rw.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.event.rw.domain.Organizer;
import kigali.event.rw.domain.Registration;
import kigali.event.rw.domain.RegistrationStatus;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, UUID> {

    List<Registration> findByStatusOrderByEventDateAsc(RegistrationStatus status);

    List<Registration> findByEventDateBetweenOrderByEventDateAsc(LocalDate start, LocalDate end);

    boolean existsByOrganizerAndEventDateAndStatusNot(Organizer organizer, LocalDate eventDate, RegistrationStatus status);

    long countByStatus(RegistrationStatus status);

    long countByEventDate(LocalDate eventDate);

    @Query("SELECT r.status, COUNT(r) FROM Registration r GROUP BY r.status")
    List<Object[]> countByStatusGrouped();

    @Modifying
    @Query("UPDATE Registration r SET r.status = kigali.event.rw.domain.RegistrationStatus.CANCELLED WHERE r.organizer.id = :organizerId AND r.eventDate = :date AND r.status <> kigali.event.rw.domain.RegistrationStatus.ATTENDED")
    int cancelOrganizerDay(@Param("organizerId") UUID organizerId, @Param("date") LocalDate date);

}
