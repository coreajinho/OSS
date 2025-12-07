package org.example.stcapstonebackend.findTeam.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.stcapstonebackend.debate.model.BaseEntity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(exclude = "FindTeamPost")
@Entity(name="find_team_request")
@Builder(toBuilder = true)
public class FindTeamRequest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String writer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FindTeamTag desiredTag;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isAccepted = false;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "find_team_post_id")
    private FindTeamPost findTeamPost;

    public void setFindTeamPost(FindTeamPost findTeamPost) {
        this.findTeamPost = findTeamPost;
    }

    public void update(String content, String writer, FindTeamTag desiredTag) {
        this.content = content;
        this.writer = writer;
        this.desiredTag = desiredTag;
    }

    public void accept() {
        this.isAccepted = true;
    }

    public void cancelAccept() {
        this.isAccepted = false;
    }
}
