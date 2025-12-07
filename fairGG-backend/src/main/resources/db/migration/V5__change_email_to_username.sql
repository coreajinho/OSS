-- 기존 email 컬럼을 username으로 변경
ALTER TABLE users
    CHANGE COLUMN email username VARCHAR(50) NOT NULL UNIQUE;

-- 인덱스 재생성
DROP INDEX idx_email ON users;
CREATE INDEX idx_username ON users(username);

