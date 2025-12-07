package org.example.stcapstonebackend.findTeam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.stcapstonebackend.findTeam.dto.FindTeamPostRequest;
import org.example.stcapstonebackend.findTeam.dto.FindTeamPostResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/find-team/posts")
@RequiredArgsConstructor
public class FindTeamPostController {

    private final FindTeamPostService findTeamPostService;

    @PostMapping
    public ResponseEntity<FindTeamPostResponse> createPost(@Valid @RequestBody FindTeamPostRequest request) {
        FindTeamPostResponse response = findTeamPostService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FindTeamPostResponse> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody FindTeamPostRequest request
    ) {
        FindTeamPostResponse response = findTeamPostService.updatePost(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        findTeamPostService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindTeamPostResponse> getPost(@PathVariable Long id) {
        FindTeamPostResponse response = findTeamPostService.getPost(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FindTeamPostResponse>> getAllPosts() {
        List<FindTeamPostResponse> responses = findTeamPostService.getAllPosts();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/active")
    public ResponseEntity<List<FindTeamPostResponse>> getActivePosts() {
        List<FindTeamPostResponse> responses = findTeamPostService.getActivePosts();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search/title")
    public ResponseEntity<List<FindTeamPostResponse>> searchByTitle(@RequestParam String title) {
        List<FindTeamPostResponse> responses = findTeamPostService.searchByTitle(title);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/search/writer")
    public ResponseEntity<List<FindTeamPostResponse>> searchByWriter(@RequestParam String writer) {
        List<FindTeamPostResponse> responses = findTeamPostService.searchByWriter(writer);
        return ResponseEntity.ok(responses);
    }
}
