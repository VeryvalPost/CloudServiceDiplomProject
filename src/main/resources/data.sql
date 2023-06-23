INSERT INTO cloudusers.users (id, login, password)
SELECT 1, 'test', '123'
WHERE NOT EXISTS (SELECT 1 FROM cloudusers.users WHERE id = 1);

INSERT INTO cloudusers.users (id, login, password)
SELECT 2, 'test2', '456'
WHERE NOT EXISTS (SELECT 1 FROM cloudusers.users WHERE id = 2);