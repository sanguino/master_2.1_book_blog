package sanguino.board.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import sanguino.board.dtos.request.BookRequestDto;
import sanguino.board.dtos.request.CommentRequestDto;
import sanguino.board.dtos.response.BookBasicResponseDto;
import sanguino.board.dtos.response.BookCompleteResponseDto;
import sanguino.board.dtos.response.CommentResponseDto;

import java.util.Collection;

public interface BookController {

    @Operation(summary = "Get a list of books (id and title only)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Books found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = BookBasicResponseDto.class)
                                    )
                            )
                    }
            )
    })
    Collection<BookBasicResponseDto> listBooks();

    @Operation(summary = "Create a book")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookCompleteResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content
            ),
    })
    BookCompleteResponseDto newBook(@RequestBody BookRequestDto book);

    @Operation(summary = "Show book info and comments by id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Book found",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BookCompleteResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Book not found",
                    content = @Content
            )
    })
    BookCompleteResponseDto showBook(@PathVariable long id);

    @Operation(summary = "Create a comment by book id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment created",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponseDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content
            ),
    })
    CommentResponseDto newComment(@RequestBody CommentRequestDto comment, @PathVariable long id);

    @Operation(summary = "Delete a comment by book id")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Comment deleted",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CommentResponseDto.class)
                            )
                    }

            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Comment not found",
                    content = @Content
            )
    })
    CommentResponseDto deleteComment(@PathVariable long bookId, @PathVariable long commentId);
}
