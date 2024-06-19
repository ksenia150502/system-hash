package edu.java.interpret.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FileDownloadService {
    private final WebClient webClient;
    @Value("${s3.base.host}")
    private String host;

    public FileDownloadService() {
        webClient = WebClient.builder()
                .baseUrl("")
                .build();
    }

    public byte[] downloadFile(String url) {
        return webClient
                .get()
                .uri(builder ->
                        builder.scheme("http")
                                .host(host)
                                .path(url)
                                .build())
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }
}
