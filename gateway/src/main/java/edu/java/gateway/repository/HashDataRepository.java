package edu.java.gateway.repository;

import edu.java.gateway.entity.HashData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashDataRepository extends JpaRepository<HashData, Long> {
}
