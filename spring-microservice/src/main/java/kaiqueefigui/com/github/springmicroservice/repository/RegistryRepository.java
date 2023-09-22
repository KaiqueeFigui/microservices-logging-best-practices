package kaiqueefigui.com.github.springmicroservice.repository;

import kaiqueefigui.com.github.springmicroservice.entities.Registry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistryRepository extends JpaRepository<Registry, Long> {
}
