package org.example.stcapstonebackend.findTeam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.stcapstonebackend.findTeam.dto.FindTeamRequestRequest;
import org.example.stcapstonebackend.findTeam.dto.FindTeamRequestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/find-team/posts/{postId}/requests")
@RequiredArgsConstructor
public class FindTeamRequestController {

    private final FindTeamRequestService findTeamRequestService;

    @PostMapping
    public ResponseEntity<FindTeamRequestResponse> createRequest(
            @PathVariable Long postId,
            @Valid @RequestBody FindTeamRequestRequest request
    ) {
        FindTeamRequestResponse response = findTeamRequestService.createRequest(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<FindTeamRequestResponse>> getRequestsByPostId(@PathVariable Long postId) {
        List<FindTeamRequestResponse> responses = findTeamRequestService.getRequestsByPostId(postId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{requestId}")
    public ResponseEntity<FindTeamRequestResponse> updateRequest(
            @PathVariable Long postId,
            @PathVariable Long requestId,
            @Valid @RequestBody FindTeamRequestRequest request
    ) {
        FindTeamRequestResponse response = findTeamRequestService.updateRequest(postId, requestId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<Void> deleteRequest(
            @PathVariable Long postId,
            @PathVariable Long requestId
    ) {
        findTeamRequestService.deleteRequest(postId, requestId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{requestId}/toggle-accept")
    public ResponseEntity<FindTeamRequestResponse> toggleAcceptance(
            @PathVariable Long postId,
            @PathVariable Long requestId
    ) {
        FindTeamRequestResponse response = findTeamRequestService.toggleAcceptance(postId, requestId);
        return ResponseEntity.ok(response);
    }
}
