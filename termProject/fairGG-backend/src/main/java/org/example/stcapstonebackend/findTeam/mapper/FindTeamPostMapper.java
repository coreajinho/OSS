package org.example.stcapstonebackend.findTeam.mapper;

import lombok.RequiredArgsConstructor;
import org.example.stcapstonebackend.findTeam.dto.FindTeamPostRequest;
import org.example.stcapstonebackend.findTeam.dto.FindTeamPostResponse;
import org.example.stcapstonebackend.findTeam.model.FindTeamPost;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FindTeamPostMapper {

    private final FindTeamRequestMapper findTeamRequestMapper;

    public FindTeamPost toEntity(FindTeamPostRequest request) {
        return FindTeamPost.builder()
                .title(request.title())
                .content(request.content())
                .writer(request.writer())
                .tags(request.tags())
                .build();
    }

    public FindTeamPostResponse toDto(FindTeamPost post) {
        return new FindTeamPostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getWriter(),
                post.getTags(),
                post.getAcceptedTags(),
                post.getAvailableTags(),
                post.getStatus(),
                post.getPendingExpirationAt(),
                post.getRequests().size(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                post.getRequests().stream()
                        .map(findTeamRequestMapper::toDto)
                        .collect(Collectors.toList())
        );
    }
}
