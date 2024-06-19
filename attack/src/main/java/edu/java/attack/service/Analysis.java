package edu.java.attack.service;

import edu.java.attack.entity.HashData;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface Analysis {
    List<Double> attack(List<HashData> hashDataList);
}
