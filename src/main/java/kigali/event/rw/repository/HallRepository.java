package kigali.event.rw.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kigali.event.rw.domain.Hall;

@Repository
public interface HallRepository extends JpaRepository<Hall, UUID> {

    Optional<Hall> findByHallNumber(int hallNumber);

    @Query("SELECT h FROM Hall h WHERE NOT EXISTS (SELECT o FROM Organizer o WHERE o.hall = h) ORDER BY h.hallNumber")
    List<Hall> findFreeHalls();

}
