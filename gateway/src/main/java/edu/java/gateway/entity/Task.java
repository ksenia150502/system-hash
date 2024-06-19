package edu.java.gateway.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String initialData;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "task_id")
    private List<HashData> hashDataList;

    @Column
    private ProcessingStatus status;

    @OneToOne
    private FileMetadata fileMetadata;

    @OneToOne
    private Result result;

    public Task() {
    }
}
