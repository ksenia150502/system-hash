package edu.java.attack.repository;


import edu.java.attack.entity.HashData;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HashDataRepository extends JpaRepository<HashData, Long> {
}
