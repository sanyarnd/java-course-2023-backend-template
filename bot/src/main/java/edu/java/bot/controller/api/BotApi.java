package edu.java.bot.controller.api;

import edu.java.payload.dto.request.LinkUpdateRequest;
import edu.java.payload.dto.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
public interface BotApi {
    @Operation(summary = "Отправить обновление", responses = {
        @ApiResponse(responseCode = "200", description = "Обновление обработано"),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
    @PostMapping(value = "/updates", consumes = {"application/json"})
    void updatesPost(@RequestBody LinkUpdateRequest request);
}
