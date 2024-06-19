package edu.java.gateway.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "hash_data")
public class HashData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String data;

    @Column(columnDefinition = "TEXT")
    private String hash;

    public HashData() {
    }
}
