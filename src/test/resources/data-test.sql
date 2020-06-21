INSERT INTO business_users VALUES
(1, 'Test-User Vorname', 'Test-User Nachname', 'mail@test.com', 'EUSER'),
(2, 'tom', 'mustermann', 'tom@fhms.de', 'EUSER'),
(3, 'moritz', 'mustermann', 'moritz@mustermann.de', 'EUSER');

INSERT INTO locations (id, latitude, longitude) VALUES
(1, 50.00, 50.00),
(2, 60.00, 60.00),
(3, 70.00, 70.00);

INSERT INTO preferences(id, value) VALUES
(1,'Music'),
(2, 'Party');

INSERT INTO events (id, datetime, description, name, radius, business_user_id, location_id) VALUES
(1, '2020-04-22T12:30:00', 'This is the description', 'Test Event', 5, 0, 1),
(2, '2020-06-13T11:00:00', 'Geburtstagsparty im Garten', 'Hans Geburtstag', 2, 1, 2),
(3, '2020-07-07T15:00:00', 'event to be deleted', 'Delete Test Event', 3, 2, 3);

INSERT INTO event_preferences (event_id, preference_id) VALUES
(1,1),
(2,1),
(2,2),
(3,2);