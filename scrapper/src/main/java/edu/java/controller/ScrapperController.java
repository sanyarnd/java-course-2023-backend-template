package edu.java.controller;

import edu.java.controller.dto.AddLinkRequest;
import edu.java.controller.dto.ApiErrorResponse;
import edu.java.controller.dto.LinkResponse;
import edu.java.controller.dto.ListLinkResponse;
import edu.java.controller.dto.RemoveLinkRequest;
import edu.java.data.UserTracksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScrapperController {

    final private UserTracksService userTracksService;

    public ScrapperController(UserTracksService userTracksService) {
        this.userTracksService = userTracksService;
    }

    @PostMapping("/tg-chat/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат зарегистрирован"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @Operation(summary = "Зарегистрировать чат")
    public ResponseEntity<Void> postTgChat(@PathVariable(name = "id") Long id) {
        userTracksService.addUser(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @DeleteMapping("/tg-chat/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат успешно удалён"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "404",
                     description = "Чат не существует",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @Operation(summary = "Удалить чат")
    public ResponseEntity<Void> deleteTgChat(@PathVariable(name = "id") Long id) {
        userTracksService.removeUser(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping("/links")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылки успешно получены",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ListLinkResponse.class))),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @Operation(summary = "Получить все отслеживаемые ссылки")
    public ResponseEntity<ListLinkResponse> getLinks(@RequestHeader(name = "Tg-Chat-Id") Long id) {
        List<URI> urls = userTracksService.getTrackedURLs(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ListLinkResponse(urls));
    }

    @PostMapping("/links")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @Operation(summary = "Добавить отслеживание ссылки")
    public ResponseEntity<LinkResponse> postLinks(
        @RequestHeader(name = "Tg-Chat-Id") Long id,
        @Valid @RequestBody AddLinkRequest addLinkRequest
    ) throws URISyntaxException {
        Long linkId = userTracksService.addTrackedURLs(id, addLinkRequest.getLink());
        return ResponseEntity.status(HttpStatus.OK).body(new LinkResponse(linkId, new URI(addLinkRequest.getLink())));
    }

    @DeleteMapping("/links")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "404",
                     description = "Ссылка не найдена",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @Operation(summary = "Убрать отслеживание ссылки")
    public ResponseEntity<LinkResponse> deleteLinks(
        @RequestHeader(name = "Tg-Chat-Id") Long id,
        @RequestBody @Valid RemoveLinkRequest removeLinkRequest
    ) throws URISyntaxException {
        Long linkId = userTracksService.removeTrackedURLs(id, removeLinkRequest.getLink());
        return ResponseEntity.status(HttpStatus.OK)
            .body(new LinkResponse(linkId, new URI(removeLinkRequest.getLink())));
    }
}
