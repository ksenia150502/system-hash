package edu.java.gateway.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.Base64;
import edu.java.gateway.entity.*;
import edu.java.gateway.exception.FileIsEmptyException;
import edu.java.gateway.exception.FileNotUploadException;
import edu.java.gateway.exception.InvalidFileException;
import edu.java.gateway.repository.FileMetadataRepository;
import edu.java.gateway.repository.HashDataRepository;
import edu.java.gateway.repository.ResultRepository;
import edu.java.gateway.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
public class FileUploadService {
    private static final String INVALID_FILE_EXTENSION_MESSAGE = "Неверное расширение файла";
    private static final String FILE_NOT_UPLOAD_MESSAGE = "Не удалось загрузить файл";
    private static final String FILE_IS_EMPTY_MESSAGE = "Файл не должен быть пустым";

    @Value("${bucket.name}")
    private String bucketName;

    @Value("${access.key}")
    private String accessKey;

    @Value("${secret.access.key}")
    private String secretAccessKey;

    @Value("${s3.base.url}")
    private String s3BaseUrl;

    @Value("${s3.signing.region")
    private String s3SigningUrl;

    private final FileMetadataRepository fileMetadataRepository;
    private final HashDataRepository hashDataRepository;
    private final ResultRepository resultRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public FileUploadService(FileMetadataRepository fileMetadataRepository,
                             HashDataRepository hashDataRepository,
                             ResultRepository resultRepository,
                             TaskRepository taskRepository) {
        this.fileMetadataRepository = fileMetadataRepository;
        this.hashDataRepository = hashDataRepository;
        this.resultRepository = resultRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public Url saveNewFileWithData(MultipartFile file, MultipartFile data) {
        if (!Objects.equals(FilenameUtils.getExtension(file.getOriginalFilename()), "py")) {
            throw new InvalidFileException(INVALID_FILE_EXTENSION_MESSAGE);
        }

        final AmazonS3 s3Client;
        try {
            s3Client = AmazonS3ClientBuilder
                    .standard()
                    .withEndpointConfiguration(new com.amazonaws.client.builder.AwsClientBuilder
                            .EndpointConfiguration(s3BaseUrl, s3SigningUrl))
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretAccessKey)))
                    .build();
        } catch (SdkClientException ex) {
            throw new FileNotUploadException(FILE_NOT_UPLOAD_MESSAGE);
        }
        byte[] dataBytes;
        try {
            dataBytes = data.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String strData = new String(dataBytes, StandardCharsets.UTF_8);
        String[] dataArray = strData.split("\n");

        Result result = new Result();
        try {
            FileMetadata fileMetadata = getFileMetadata(file);
            Task task = new Task();
            //task.setInitialData(data);
            task.setStatus(ProcessingStatus.NOT_PROCESSED);
            task.setFileMetadata(fileMetadata);
            task.setResult(result);
            List<HashData> hashDataList = new ArrayList<>();
            for (String d : dataArray) {
                HashData hashData = new HashData();
                hashData.setData(d);
                hashDataList.add(hashData);
            }
            task.setHashDataList(hashDataList);
            fileMetadataRepository.save(fileMetadata);
            taskRepository.save(task);
            resultRepository.save(result);
            hashDataRepository.saveAll(hashDataList);

            String fileName = fileMetadata.getId().toString() + "/" + file.getOriginalFilename();
            byte[] fileBytes = file.getBytes();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(fileBytes);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(fileBytes.length);
            if (inputStream.available() == 0) {
                throw new FileIsEmptyException(FILE_IS_EMPTY_MESSAGE);
            }
            s3Client.putObject(bucketName, fileName, inputStream, objectMetadata);

            String url = s3Client.getUrl(bucketName, fileName).toExternalForm();
            fileMetadata.setUrl(url);
            fileMetadataRepository.save(fileMetadata);
        } catch (IOException ex) {
            throw new FileNotUploadException(FILE_NOT_UPLOAD_MESSAGE);
        }
        String encode = Base64.encodeAsString(result.getId().toString().getBytes());
        return new Url("/api/v1/result/" + encode);
    }

    private FileMetadata getFileMetadata(MultipartFile file) {
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setFileName(file.getOriginalFilename());
        fileMetadata.setFileSize(file.getSize());
        return fileMetadata;
    }
}
