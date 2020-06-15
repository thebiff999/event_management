INSERT INTO events (id, datetime, description, name, radius, business_user_id, location_id) VALUES
    (1, '2020-04-22T12:30:00', 'This is the description', 'Test Event', 5, 0, 1),
    (2, '2020-06-13T11:00:00', 'Geburtstagsparty im Garten', 'Hans Geburtstag', 2, 1, 2);

INSERT INTO business_users VALUES
    (0, 'Test-User Vorname', 'Test-User Nachname', 'mail@test.com', 'EUSER'),
    (1, 'tom', 'mustermann', 'tom@fhms.de', 'EUSER');

INSERT INTO locations (id, latitude, longitude) VALUES
    (1, 50.00, 50.00),
    (2, 60.00, 60.00);

INSERT INTO preferences(id, value) VALUES
    (1,'Music'),
    (2, 'Party');

INSERT INTO event_preferences (event_id, preference_id) VALUES
    (1,1),
    (2,1),
    (2,2);