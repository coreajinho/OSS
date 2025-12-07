-- 사용자 테이블에 라이엇 정보 추가
ALTER TABLE users
    ADD COLUMN riot_name VARCHAR(50) NOT NULL DEFAULT '',
    ADD COLUMN riot_tag VARCHAR(10) NOT NULL DEFAULT '';

-- 기본값 제거 (이후 생성되는 사용자는 필수 입력)
ALTER TABLE users
    ALTER COLUMN riot_name DROP DEFAULT,
    ALTER COLUMN riot_tag DROP DEFAULT;

-- 라이엇 ID 조합에 대한 인덱스 생성 (검색 성능 향상)
CREATE INDEX idx_riot_id ON users(riot_name, riot_tag);

