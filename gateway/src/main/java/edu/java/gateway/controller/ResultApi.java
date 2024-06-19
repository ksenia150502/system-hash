package edu.java.gateway.controller;

import edu.java.gateway.entity.ResultDTO;
import edu.java.gateway.exception.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

public interface ResultApi {
    @Operation(summary = "Получить результаты тестирования")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос выполнен успешно",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResultDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})
    })
    ResultDTO getResult(String id);
}
