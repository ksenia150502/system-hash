package edu.java.gateway.repository;

import edu.java.gateway.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ResultRepository extends JpaRepository<Result, UUID> {
}
