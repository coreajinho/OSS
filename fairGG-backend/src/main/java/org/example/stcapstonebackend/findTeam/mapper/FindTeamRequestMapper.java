package org.example.stcapstonebackend.findTeam.mapper;

import org.example.stcapstonebackend.findTeam.dto.FindTeamRequestResponse;
import org.example.stcapstonebackend.findTeam.model.FindTeamRequest;
import org.springframework.stereotype.Component;

@Component
public class FindTeamRequestMapper {

    public FindTeamRequestResponse toDto(FindTeamRequest request) {
        return new FindTeamRequestResponse(
                request.getId(),
                request.getContent(),
                request.getWriter(),
                request.getDesiredTag(),
                request.getIsAccepted(),
                request.getCreatedAt(),
                request.getModifiedAt()
        );
    }
}
