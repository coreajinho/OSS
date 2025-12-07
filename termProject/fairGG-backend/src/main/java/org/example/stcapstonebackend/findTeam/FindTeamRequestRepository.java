package org.example.stcapstonebackend.findTeam;

import org.example.stcapstonebackend.findTeam.model.FindTeamRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FindTeamRequestRepository extends JpaRepository<FindTeamRequest, Long> {

    List<FindTeamRequest> findByFindTeamPostId(Long postId);

    List<FindTeamRequest> findByFindTeamPostIdAndIsAccepted(Long postId, Boolean isAccepted);
}

