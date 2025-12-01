SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS notice;
DROP TABLE IF EXISTS user_category;
DROP TABLE IF EXISTS user_info;
DROP TABLE IF EXISTS user_account;
DROP TABLE IF EXISTS news;
DROP TABLE IF EXISTS user_bookmark;
DROP TABLE IF EXISTS news_community;
DROP TABLE IF EXISTS admin_info;
DROP TABLE IF EXISTS admin_menu_info;
DROP TABLE IF EXISTS sms_send_list;
DROP TABLE IF EXISTS sms_send_history;
DROP TABLE IF EXISTS news_category;
DROP TABLE IF EXISTS news_summary;
DROP TABLE IF EXISTS menu_info;
DROP TABLE IF EXISTS news_comment;

CREATE TABLE notice (
    notice_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    view_count INT DEFAULT 0,
    is_visible BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO notice(title, content)
VALUES ('첫 공지', '환영합니다!');


CREATE TABLE user_account (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NULL,
    provider VARCHAR(50) NOT NULL,
    social_id VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id),
    UNIQUE (provider, social_id)
);

INSERT INTO user_account (email, password, provider, social_id, status, created_at)
VALUES
    ('testuser01@example.com',
     '$2a$10$JHDlx82...암호화된비밀번호...',
     'LOCAL',
     'LOCAL_0001',
     'ACTIVE',
     CURRENT_TIMESTAMP),

    ('hello.google@gmail.com',
     NULL,
     'GOOGLE',
     'google_1234567890',
     'ACTIVE',
     CURRENT_TIMESTAMP);


CREATE TABLE user_category (
    user_category_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_category_id)
);


CREATE TABLE user_info (
    user_info_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    profile_image VARCHAR(255) NULL,
    birth_date VARCHAR(255) NULL,
    gender VARCHAR(10) NULL,
    nickname VARCHAR(255) NULL,
    phoneNumber VARCHAR(20) NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_info_id),
    FOREIGN KEY (user_id) REFERENCES user_account (user_id)
);

INSERT INTO user_info (user_id, profile_image, birth_date, gender, nickname, created_at)
VALUES
    (1, 'https://cdn.example.com/user1.png', '1995-04-21', 'MALE', '개발자황', CURRENT_TIMESTAMP),
    (2, NULL, '1999-12-05', 'FEMALE', '코딩요정', CURRENT_TIMESTAMP);

CREATE TABLE news (
    news_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '뉴스 PK',
    title VARCHAR(500) NOT NULL COMMENT '뉴스 제목',
    content TEXT NOT NULL COMMENT '뉴스 원문 본문',
    url VARCHAR(500) NOT NULL COMMENT '원본 뉴스 URL',
    category VARCHAR(20) NOT NULL COMMENT '카테고리',
    thumbnail VARCHAR(500) NULL COMMENT '썸네일 이미지 URL',
    is_write TINYINT(1) NOT NULL DEFAULT 0 COMMENT '기사화 등록 여부 (1=등록,0=미등록)',
    summary VARCHAR(4000) NULL COMMENT '뉴스 요약',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    modified_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    view_count INT NOT NULL DEFAULT 0 COMMENT '조회수',
    published_at VARCHAR(20) NULL COMMENT '기사 작성 시간',
    PRIMARY KEY (news_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO news (title, content, url, category, thumbnail, is_write, published_at, view_count)
VALUES
('첫 번째 뉴스 제목', '첫 번째 뉴스 본문 내용입니다.', 'https://news.example.com/1', '정치', NULL, 0, '11-21 10:23', 0),
('두 번째 뉴스 제목', '두 번째 뉴스 본문 내용입니다.', 'https://news.example.com/2', '정치', NULL, 0, '11-21 11:01', 0),
('세 번째 뉴스 제목', '세 번째 뉴스 본문 내용입니다.', 'https://news.example.com/3', '정치', NULL, 0, '11-21 08:55', 0);

CREATE TABLE user_bookmark (
    bookmark_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '북마크 ID (PK)',
    user_id BIGINT NOT NULL COMMENT '사용자 ID (FK)',
    news_id BIGINT NOT NULL COMMENT '뉴스 ID (FK)',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',

    PRIMARY KEY (bookmark_id),

    FOREIGN KEY (user_id) REFERENCES user_account(user_id),
    FOREIGN KEY (news_id) REFERENCES news(news_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO user_bookmark (user_id, news_id, created_at)
VALUES
    (1, 1, CURRENT_TIMESTAMP),
    (2, 2, CURRENT_TIMESTAMP);


CREATE TABLE news_community (
    community_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '게시글 ID (PK)',
    user_id BIGINT NOT NULL COMMENT '유저 ID (FK)',
    title VARCHAR(255) NOT NULL COMMENT '게시글 제목',
    content TEXT NOT NULL COMMENT '게시글 내용',
    tag VARCHAR(50) NOT NULL COMMENT '주제별 분류',
    img_url VARCHAR(255) NULL COMMENT '이미지 URL',
    view_count INT NOT NULL DEFAULT 0 COMMENT '조회수',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '게시글 생성 날짜',
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '게시글 수정 시간',

    PRIMARY KEY (community_id),
    FOREIGN KEY (user_id) REFERENCES user_info(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO news_community (user_id, title, content, tag, img_url, view_count, created_at)
VALUES
    (1, '첫 번째 게시글', '안녕하세요! 첫 글입니다.', '공지', NULL, 0, CURRENT_TIMESTAMP),
    (2, 'AI 뉴스 요약', '오늘의 AI 관련 뉴스 요약입니다.', 'AI', 'https://cdn.example.com/news1.png', 10, CURRENT_TIMESTAMP);


CREATE TABLE admin_info (
    admin_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '관리자 ID (PK)',
    admin_name VARCHAR(255) NULL COMMENT '관리자명',
    email VARCHAR(255) NULL COMMENT '관리자 이메일',
    image_url VARCHAR(255) NULL COMMENT '프로필 이미지 URL',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    password VARCHAR(255) NULL COMMENT '비밀번호',

    PRIMARY KEY (admin_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO admin_info (admin_name, email, image_url, password, created_at)
VALUES
    ('시스템 관리자', 'admin@example.com', NULL, '$2a$10$ENCRYPTEDPWD...', CURRENT_TIMESTAMP),
    ('운영자A', 'manager@example.com', 'https://cdn.example.com/profileA.png', '$2a$10$HASHEDPWD...', CURRENT_TIMESTAMP);


CREATE TABLE admin_menu_info (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '메뉴 고유 ID (PK)',
    menu_name VARCHAR(100) NULL COMMENT '메뉴명',
    menu_code VARCHAR(50) NULL COMMENT '내부 코드명',
    parent_id BIGINT NULL COMMENT '상위 메뉴 ID (없으면 NULL)',
    menu_order INT NOT NULL COMMENT '정렬 순서',
    url_path VARCHAR(255) NULL COMMENT '연결되는 경로',
    icon VARCHAR(50) NULL COMMENT '아이콘 코드 (선택사항)',
    visible TINYINT(1) NOT NULL DEFAULT 1 COMMENT '노출 여부 (1=노출,0=비노출)',
    description VARCHAR(255) NULL COMMENT '메뉴 설명',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시각',
    modified_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 시각',

    PRIMARY KEY (id),

    FOREIGN KEY (parent_id) REFERENCES admin_menu_info(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO admin_menu_info
(menu_name, menu_code, parent_id, menu_order, url_path, icon, visible, description, created_at)
VALUES
    ('대시보드', 'DASHBOARD', NULL, 1, '/admin/dashboard', 'dashboard', 1, '관리자 홈 화면', CURRENT_TIMESTAMP),
    ('사용자 관리', 'USER_MANAGE', NULL, 2, '/admin/users', 'users', 1, '회원 관리 메뉴', CURRENT_TIMESTAMP);

CREATE TABLE sms_send_list (
    send_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '발송 아이디 (PK)',
    user_id BIGINT NOT NULL COMMENT '수신 회원 아이디 (FK)',
    sms_category VARCHAR(100) NOT NULL COMMENT '발송 요청 종류',
    content TEXT NOT NULL COMMENT '발송 내용',
    is_send TINYINT(1) NOT NULL DEFAULT 0 COMMENT '발송 처리 여부 (1=발송,0=미발송)',
    req_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '발송 요청일',
    author_name VARCHAR(255) NOT NULL COMMENT '요청자 이름',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',

    PRIMARY KEY (send_id),

    FOREIGN KEY (user_id) REFERENCES user_info(user_info_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sms_send_list
(user_id, sms_category, content, is_send, req_date, author_name, created_at)
VALUES
    (1, '회원가입', '회원가입 인증번호: 123456', 1, CURRENT_TIMESTAMP, '관리자A', CURRENT_TIMESTAMP),
    (2, '비밀번호_재설정', '비밀번호 재설정 링크가 발송되었습니다.', 0, CURRENT_TIMESTAMP, '관리자B', CURRENT_TIMESTAMP);


CREATE TABLE sms_send_history (
    send_his_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '발송 이력 아이디 (PK)',
    send_id BIGINT NOT NULL COMMENT '발송 아이디 (FK)',
    user_id BIGINT NOT NULL COMMENT '수신 회원 아이디 (FK)',
    sms_category VARCHAR(100) NOT NULL COMMENT '발송 요청 종류',
    content TEXT NOT NULL COMMENT '발송 내용',
    is_send TINYINT(1) NOT NULL DEFAULT 0 COMMENT '발송 처리 여부(1=발송,0=미발송)',
    req_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '발송 요청일',
    author_name VARCHAR(255) NOT NULL COMMENT '요청자 이름',
    created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일자',
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',

    PRIMARY KEY (send_his_id),

    FOREIGN KEY (send_id) REFERENCES sms_send_list(send_id),
    FOREIGN KEY (user_id) REFERENCES user_info(user_info_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO sms_send_history
(send_id, user_id, sms_category, content, is_send, req_date, author_name, created_at)
VALUES
    (1, 1, '회원가입', '회원가입 인증번호: 123456', 1, CURRENT_TIMESTAMP, '관리자A', CURRENT_TIMESTAMP),
    (2, 2, '비밀번호_재설정', '비밀번호 재설정 안내 메시지.', 1, CURRENT_TIMESTAMP, '관리자B', CURRENT_TIMESTAMP);


CREATE TABLE news_category (
    category_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '카테고리 ID (PK)',
    category_name VARCHAR(100) NOT NULL COMMENT '카테고리 이름',

    PRIMARY KEY (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO news_category (category_name)
VALUES
    ('AI 기술'),
    ('정책/사회');


CREATE TABLE news_summary (
    summary_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '요약문 PK',
    news_id BIGINT NOT NULL COMMENT '원본 뉴스 PK (FK, UNIQUE)',
    summary_text TEXT NOT NULL COMMENT 'AI가 생성한 요약문',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시간',
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 시간',
    category_id BIGINT NOT NULL COMMENT '뉴스 카테고리 ID (FK)',

    PRIMARY KEY (summary_id),

    UNIQUE KEY uk_news_summary_news_id (news_id),

    FOREIGN KEY (news_id) REFERENCES news(news_id),
    FOREIGN KEY (category_id) REFERENCES news_category(category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO news_summary
(news_id, summary_text, category_id, created_at)
VALUES
    (1, 'AI 기술 동향을 요약한 기사입니다.', 1, CURRENT_TIMESTAMP),
    (2, '정부 정책 변경 사항을 정리한 내용입니다.', 2, CURRENT_TIMESTAMP);


CREATE TABLE menu_info (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '고유 ID (PK)',
    menu_name VARCHAR(255) NULL COMMENT '메뉴명',
    menu_code VARCHAR(255) NULL COMMENT '구분용 메뉴 코드',
    parent BIGINT NULL COMMENT '상위 메뉴 ID (없으면 NULL)',
    menu_order INT NOT NULL COMMENT '사이드바에 표시할 순서',
    url_path VARCHAR(500) NOT NULL COMMENT '연결되는 URL 경로',
    visible TINYINT(1) NOT NULL DEFAULT 1 COMMENT '메뉴 활성화 여부 (1=활성,0=비활성)',
    description VARCHAR(500) NULL COMMENT '메뉴 설명',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 시각',
    updated_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 시각',

    PRIMARY KEY (id),
    FOREIGN KEY (parent) REFERENCES menu_info(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO menu_info
(menu_name, menu_code, parent, menu_order, url_path, visible, description, created_at)
VALUES
    ('홈', 'HOME', NULL, 1, '/home', 1, '메인 페이지', CURRENT_TIMESTAMP),
    ('내 정보', 'PROFILE', NULL, 2, '/profile', 1, '사용자 프로필 페이지', CURRENT_TIMESTAMP);

CREATE TABLE news_comment (
                              comment_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '댓글 ID',
                              news_id BIGINT NOT NULL COMMENT '뉴스 ID (FK)',
                              user_id BIGINT NOT NULL COMMENT '작성자 ID (FK, user_info 기준)',
                              content VARCHAR(500) NOT NULL COMMENT '댓글 내용',
                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
                              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',

                              FOREIGN KEY (news_id) REFERENCES news(news_id) ON DELETE CASCADE,
                              FOREIGN KEY (user_id) REFERENCES user_info(user_info_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='뉴스 댓글 테이블';

INSERT INTO news_comment (news_id, user_id, content)
VALUES
    (1, 1, '첫 번째 뉴스에 대한 첫 댓글입니다.'),
    (1, 2, '좋은 기사 감사합니다!'),
    (2, 1, '두 번째 뉴스 내용도 흥미롭네요.');

SET FOREIGN_KEY_CHECKS = 1;