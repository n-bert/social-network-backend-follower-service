TRUNCATE TABLE followers CASCADE;
INSERT INTO followers(id, user_id, follower_id)
VALUES (1, 1, 2);