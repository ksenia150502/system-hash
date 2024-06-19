package edu.java.attack.repository;

import edu.java.attack.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, UUID> {
}
