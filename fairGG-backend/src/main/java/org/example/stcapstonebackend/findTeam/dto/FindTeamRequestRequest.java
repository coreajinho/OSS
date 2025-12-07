package org.example.stcapstonebackend.findTeam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.example.stcapstonebackend.findTeam.model.FindTeamTag;

@Builder
public record FindTeamRequestRequest(
        @NotBlank String content,
        @NotBlank String writer,
        @NotNull FindTeamTag desiredTag
) {
}
