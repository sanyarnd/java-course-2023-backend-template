package edu.java.bot.controller;

import edu.java.bot.bot.Bot;
import edu.java.bot.controller.dto.ApiErrorResponse;
import edu.java.bot.controller.dto.LinkUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//Is it correct project structure??
@RestController
public class BotController {
    private Bot bot;

    public BotController(Bot bot) {
        this.bot = bot;
    }

    @PostMapping("/updates")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Обновление обработано"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @Operation(summary = "Отправить обновление")
    public ResponseEntity<Void> postUpdates(@RequestBody @Valid LinkUpdate body) {
        bot.callUsers(body.getUrl(), body.getDescription(), body.getTgChatIds());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
