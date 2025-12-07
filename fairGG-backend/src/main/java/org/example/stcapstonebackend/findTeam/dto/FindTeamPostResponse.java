package org.example.stcapstonebackend.findTeam.dto;

import lombok.Builder;
import org.example.stcapstonebackend.findTeam.model.AcceptedTag;
import org.example.stcapstonebackend.findTeam.model.FindTeamTag;
import org.example.stcapstonebackend.findTeam.model.PostStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
public record FindTeamPostResponse(
        Long id,
        String title,
        String content,
        String writer,
        Set<FindTeamTag> tags,
        Set<AcceptedTag> acceptedTags,
        Set<FindTeamTag> availableTags,
        PostStatus status,
        LocalDateTime pendingExpirationAt,
        int requestCount,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        List<FindTeamRequestResponse> requests
) {
}
