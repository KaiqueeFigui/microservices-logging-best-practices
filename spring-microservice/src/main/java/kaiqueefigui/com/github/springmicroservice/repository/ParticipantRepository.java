package kaiqueefigui.com.github.springmicroservice.repository;

import kaiqueefigui.com.github.springmicroservice.entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
