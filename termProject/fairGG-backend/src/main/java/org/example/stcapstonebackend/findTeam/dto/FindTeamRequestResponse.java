package org.example.stcapstonebackend.findTeam.dto;

import lombok.Builder;
import org.example.stcapstonebackend.findTeam.model.FindTeamTag;

import java.time.LocalDateTime;

@Builder
public record FindTeamRequestResponse(
        Long id,
        String content,
        String writer,
        FindTeamTag desiredTag,
        Boolean isAccepted,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
}
