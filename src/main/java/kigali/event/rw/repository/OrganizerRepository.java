package kigali.event.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kigali.event.rw.domain.Hall;
import kigali.event.rw.domain.Organizer;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, UUID> {

    boolean existsByHall(Hall hall);

    @Query("SELECT o FROM Organizer o JOIN o.eventTypes e WHERE LOWER(e.name) = LOWER(:name)")
    List<Organizer> findByEventTypeName(@Param("name") String name);

}
