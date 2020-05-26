INSERT INTO events (id, datetime, description, name, radius, business_user_id_id, location_id) VALUES
    (1, '2020-04.22 12:30:00', 'This is the description', 'Test Event', 5, 1, 1);

INSERT INTO business_users (id, name) VALUES
    (1, 'Test-User');

INSERT INTO locations (id, latitude, longitude) VALUES
    (1, 50.00, 50.00);

INSERT INTO preferences(id, preference) VALUES
    (1,"Music");

INSERT INTO event_preferences (event_id, preference_id) VALUES
    (1,1);

