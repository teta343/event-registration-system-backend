package kigali.event.rw.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kigali.event.rw.domain.EventType;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, UUID> {

    @Query("SELECT e FROM EventType e WHERE e.organizers IS EMPTY")
    List<EventType> findUnusedEventTypes();

}
