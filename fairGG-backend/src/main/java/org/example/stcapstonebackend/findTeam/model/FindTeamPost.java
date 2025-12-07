package org.example.stcapstonebackend.findTeam.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.stcapstonebackend.debate.model.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
@Entity(name="find_team_post")
@Builder(toBuilder = true)
public class FindTeamPost extends BaseEntity {

//    ???
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private PostStatus status = PostStatus.ACTIVE;

    @Column
    private LocalDateTime pendingExpirationAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "find_team_post_tags", joinColumns = @JoinColumn(name = "find_team_post_id"))
    @Column(name = "tag")
    @Builder.Default
    private Set<FindTeamTag> tags = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "accepted_tags", joinColumns = @JoinColumn(name = "find_team_post_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "tag", column = @Column(name = "tag")),
            @AttributeOverride(name = "requestId", column = @Column(name = "request_id"))
    })
    @Builder.Default
    private Set<AcceptedTag> acceptedTags = new HashSet<>();

    @OneToMany(mappedBy = "findTeamPost", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<FindTeamRequest> requests = new ArrayList<>();

    public void addRequest(FindTeamRequest request) {
        requests.add(request);
        request.setFindTeamPost(this);
    }

    public void update(String title, String content, String writer, Set<FindTeamTag> tags) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        if (tags != null) {
            this.tags = tags;
        }
    }

    public void acceptRequest(Long requestId, FindTeamTag tag) {
        boolean alreadyAccepted = acceptedTags.stream()
                .anyMatch(acceptedTag -> acceptedTag.getTag() == tag);

        if (alreadyAccepted) {
            throw new IllegalStateException("Already accepted for this tag");
        }

        acceptedTags.add(new AcceptedTag(tag, requestId));

        if (acceptedTags.size() == tags.size()) {
            this.status = PostStatus.PENDING_EXPIRATION;
            this.pendingExpirationAt = LocalDateTime.now().plusMinutes(10);
        }
    }

    public void cancelAcceptance(Long requestId) {
        acceptedTags.removeIf(acceptedTag -> acceptedTag.getRequestId().equals(requestId));

        if (this.status == PostStatus.PENDING_EXPIRATION) {
            this.status = PostStatus.ACTIVE;
            this.pendingExpirationAt = null;
        }
    }

    public void expire() {
        this.status = PostStatus.EXPIRED;
    }

    public boolean isTagAccepted(FindTeamTag tag) {
        return acceptedTags.stream()
                .anyMatch(acceptedTag -> acceptedTag.getTag() == tag);
    }

    public Set<FindTeamTag> getAvailableTags() {
        Set<FindTeamTag> available = new HashSet<>(tags);
        acceptedTags.forEach(acceptedTag -> available.remove(acceptedTag.getTag()));
        return available;
    }
}
