package org.example.stcapstonebackend.findTeam.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.stcapstonebackend.findTeam.FindTeamPostRepository;
import org.example.stcapstonebackend.findTeam.model.FindTeamPost;
import org.example.stcapstonebackend.findTeam.model.PostStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PostExpirationScheduler {

    private final FindTeamPostRepository findTeamPostRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void expirePendingPosts() {
        LocalDateTime now = LocalDateTime.now();

        List<FindTeamPost> expiredPosts = findTeamPostRepository.findExpiredPosts(
                PostStatus.PENDING_EXPIRATION,
                now
        );

        if (!expiredPosts.isEmpty()) {
            log.info("Found {} posts to expire", expiredPosts.size());

            expiredPosts.forEach(post -> {
                post.expire();
                log.info("Post ID: {} expired successfully", post.getId());
            });

            log.info("Total {} posts have been expired", expiredPosts.size());
        }
    }
}

