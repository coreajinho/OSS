package org.example.stcapstonebackend.findTeam.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AcceptedTag {

    @Enumerated(EnumType.STRING)
    private FindTeamTag tag;

    private Long requestId;
}

