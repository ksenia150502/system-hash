package edu.java.gateway.controller;

import edu.java.gateway.entity.Url;
import edu.java.gateway.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadApi {
    @Operation(summary = "Загрузить файл",
            description = "Загрузить файл с хэш-функцией и данными для тестирования")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Файл успешно загружен",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Url.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "507", description = "Не удалось загрузить файл из-за ошибки хранилища",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    Url uploadFile(MultipartFile multipartFile, MultipartFile data);
}
