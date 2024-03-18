
package fr.cp.subscriber.controllers;


import fr.cp.subscriber.dto.SubscriberReq;
import fr.cp.subscriber.dto.SubscriberResp;
import fr.cp.subscriber.services.SubscriberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@AllArgsConstructor
@Validated
@RequestMapping("api/v1/subscribers")
public class SubscriberController {

    private SubscriberService subscriberService;

    @Operation(
            summary = "Create a new subscriber",
            description = "Api to create a new subscriber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Incorrect request"),
            @ApiResponse(responseCode = "201", description = "successful operation, Subscriber created"),
            @ApiResponse(responseCode = "409", description = "Conflict, a subscriber with the same name and email already exists")
    })
    @PostMapping("/create")
    @Validated(SubscriberReq.CreateValidation.class)
    public ResponseEntity<String> create(@RequestBody @Valid SubscriberReq subscriber, UriComponentsBuilder uriBuilder) {
        final var subscriberResp = subscriberService.create(subscriber);
        String uriString = uriBuilder.path("api/v1/subscribers/{id}").buildAndExpand(subscriberResp.id()).toUriString();

        return new ResponseEntity<>(uriString, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update a subscriber",
            description = "Api to update a subscriber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Subscriber not found"),
            @ApiResponse(responseCode = "400", description = "Incorrect request"),
            @ApiResponse(responseCode = "200", description = "successful operation, Subscriber updated")
    })
    @PutMapping("/update")
    public ResponseEntity<SubscriberResp> update(@RequestBody @Valid SubscriberReq subscriberReq) {
        final SubscriberResp subscriberResp = subscriberService.update(subscriberReq);
        return new ResponseEntity<>(subscriberResp, HttpStatus.OK);
    }

    @Operation(
            summary = "deactivate a subscriber",
            description = "API to deactivate a subscriber by setting the 'isActive' field to 'false'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Subscriber not found"),
            @ApiResponse(responseCode = "200", description = "successful operation, Subscriber deactivated")
    })
    @DeleteMapping("{id}")
    public ResponseEntity<?> deactivate(@PathVariable @NotNull(message = "empty id") Long id) {
        subscriberService.deactivate(id);
        return new ResponseEntity<>("Subscriber deactivated", HttpStatus.OK);

    }
    @Operation(
            summary = "Find subscribers",
            description = "API to search for subscribers based on specified criteria. Returns a list of subscribers matching the criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation, Subscriber deactivated")
    })
    @GetMapping("/search")
    public ResponseEntity<List<SubscriberResp>> searchSubscriber(@RequestParam(required = false) String fName,
                                                                 @RequestParam(required = false) String lName,
                                                                 @RequestParam(required = false) String mail,
                                                                 @RequestParam(required = false) String phone,
                                                                 @RequestParam(required = false) Boolean isActive) {
        List<SubscriberResp> subscribers = subscriberService.searchSubscribers(fName, lName, mail, phone, isActive);
        return ResponseEntity.ok(subscribers);
    }
}
