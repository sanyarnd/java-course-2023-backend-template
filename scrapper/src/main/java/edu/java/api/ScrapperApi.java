package edu.java.api;

import edu.java.request.AddLinkRequest;
import edu.java.request.RemoveLinkRequest;
import edu.java.response.ApiErrorResponse;
import edu.java.response.LinkResponse;
import edu.java.response.ListLinksResponse;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Validated
@Api(value = "scrapper")
public interface ScrapperApi {
    @Operation(summary = "Получить все отслеживаемые ссылки", responses = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылки успешно получены",
                     content = @Content(schema = @Schema(implementation = LinkResponse.class))),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
    @GetMapping(value = "/links", produces = {"application/json"})
    ListLinksResponse linksGet(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId);

    @Operation(summary = "Добавить отслеживание ссылки", responses = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылка успешно добавлена",
                     content = @Content(schema = @Schema(implementation = LinkResponse.class))),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
    @PostMapping(value = "/links", produces = {"application/json"}, consumes = {"application/json"})
    LinkResponse linksPost(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId, @RequestBody AddLinkRequest body);

    @Operation(summary = "Убрать отслеживание ссылки", responses = {
        @ApiResponse(responseCode = "200",
                     description = "Ссылка успешно убрана",
                     content = @Content(schema = @Schema(implementation = LinkResponse.class))),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "404",
                     description = "Ссылка не найдена",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
    @DeleteMapping(value = "/links", produces = {"application/json"}, consumes = {"application/json"})
    LinkResponse linksDelete(@RequestHeader(value = "Tg-Chat-Id") Long tgChatId, @RequestBody RemoveLinkRequest body);

    @Operation(summary = "Удалить чат", responses = {
        @ApiResponse(responseCode = "200", description = "Чат успешно удалён"),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "Чат не существует",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
    @DeleteMapping(value = "/tg-chat/{id}", produces = {"application/json"})
    void tgChatIdDelete(@PathVariable("id") Long id);

    @Operation(summary = "Зарегистрировать чат", responses = {
        @ApiResponse(responseCode = "200", description = "Чат зарегистрирован"),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
    @PostMapping(value = "/tg-chat/{id}", produces = {"application/json"})
    void tgChatIdPost(@PathVariable("id") Long id);
}
