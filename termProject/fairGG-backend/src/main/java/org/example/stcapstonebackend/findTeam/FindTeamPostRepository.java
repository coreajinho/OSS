package org.example.stcapstonebackend.findTeam;

import org.example.stcapstonebackend.findTeam.model.FindTeamPost;
import org.example.stcapstonebackend.findTeam.model.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FindTeamPostRepository extends JpaRepository<FindTeamPost, Long> {

    List<FindTeamPost> findByStatus(PostStatus status);

    @Query("SELECT p FROM find_team_post p WHERE p.status = :status AND p.pendingExpirationAt <= :now")
    List<FindTeamPost> findExpiredPosts(PostStatus status, LocalDateTime now);

    List<FindTeamPost> findByTitleContainingIgnoreCase(String title);

    List<FindTeamPost> findByWriterContainingIgnoreCase(String writer);

    List<FindTeamPost> findByStatusOrderByCreatedAtDesc(PostStatus status);
}

