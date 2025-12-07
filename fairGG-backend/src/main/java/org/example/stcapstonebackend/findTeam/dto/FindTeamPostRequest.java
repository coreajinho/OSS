package org.example.stcapstonebackend.findTeam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.example.stcapstonebackend.findTeam.model.FindTeamTag;

import java.util.Set;

@Builder
public record FindTeamPostRequest(
        @Size(min = 1, max = 100) @NotBlank String title,
        @Size(min = 1) @NotBlank String content,
        @Size(min = 2, max = 15) @NotBlank String writer,
        @NotEmpty Set<FindTeamTag> tags
) {
}
