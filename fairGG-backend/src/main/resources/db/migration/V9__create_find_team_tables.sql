-- 팀원찾기 게시글 테이블
CREATE TABLE find_team_post (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    writer VARCHAR(15) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    pending_expiration_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 팀원찾기 게시글 태그 테이블
CREATE TABLE find_team_post_tags (
    find_team_post_id BIGINT NOT NULL,
    tag VARCHAR(10) NOT NULL,
    PRIMARY KEY (find_team_post_id, tag),
    FOREIGN KEY (find_team_post_id) REFERENCES find_team_post(id) ON DELETE CASCADE
);

-- 수락된 태그 테이블
CREATE TABLE accepted_tags (
    find_team_post_id BIGINT NOT NULL,
    tag VARCHAR(10) NOT NULL,
    request_id BIGINT NOT NULL,
    PRIMARY KEY (find_team_post_id, tag),
    FOREIGN KEY (find_team_post_id) REFERENCES find_team_post(id) ON DELETE CASCADE
);

-- 팀원찾기 요청(댓글) 테이블
CREATE TABLE find_team_request (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    writer VARCHAR(255) NOT NULL,
    desired_tag VARCHAR(10) NOT NULL,
    is_accepted BOOLEAN NOT NULL DEFAULT FALSE,
    find_team_post_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (find_team_post_id) REFERENCES find_team_post(id) ON DELETE CASCADE
);

-- 인덱스 생성
CREATE INDEX idx_find_team_post_status ON find_team_post(status);
CREATE INDEX idx_find_team_post_created_at ON find_team_post(created_at DESC);
CREATE INDEX idx_find_team_request_post_id ON find_team_request(find_team_post_id);
CREATE INDEX idx_find_team_request_is_accepted ON find_team_request(is_accepted);

