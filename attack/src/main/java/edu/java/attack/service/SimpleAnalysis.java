package edu.java.attack.service;

import edu.java.attack.entity.HashData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleAnalysis implements Analysis {
    @Override
    public List<Double> attack(List<HashData> hashDataList) {
        List<Double> result = new ArrayList<>();
        for (HashData hashData : hashDataList) {
            loop1:
            for (HashData hashData1 : hashDataList) {
                if (hashData1 == hashData) {
                    continue;
                } else {
                    String str = hashData.getData();
                    String str1 = hashData1.getData();
                    List<Integer> diff = new ArrayList<>();
                    for (int i = 0; i < str.length()-1; i++) {
                        if (str.charAt(i) != str1.charAt(i)) {
                            diff.add(i);
                        }
                        if (diff.size() > 2) {
                            continue loop1;
                        }
                    }
                    String hash = hashData.getHash();
                    String hash1 = hashData1.getHash();

                    int countDown = 0;
                    for (Integer d : diff) {
                        for (int i = 0; i < hash.length(); i++) {
                            if (hash1.charAt(i) != hash.charAt(i)) {
                                countDown++;
                            }
                        }
                    }
                    result.add(1 - ((double)countDown / hash.length()));
                }
            }
        }
        return result;
    }
}
