INSERT INTO roles IF EXISTS (id, name)
VALUES (1, 'ROLE_USER'),
    (2, 'ROLE_ADMIN')
ON CONFLICT DO NOTHING;

INSERT INTO permissions IF EXISTS (id, name, display_name)
VALUES (1, 'VIEW_USER_DATA', 'View all user information.'),
ON CONFLICT DO NOTHING;

INSERT INTO roles_permissions IF EXISTS (role_id, permission_id)
    VALUES (2, 1),
ON CONFLICT DO NOTHING;
