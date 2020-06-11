INSERT INTO events (id, datetime, description, name, radius, business_user_id, location_id) VALUES
(01, 01, 'Event1', 'the first event of user1', '2020-06-01 18:00:00', 5, 1),
(02, 01, 'Event2', 'the second event of user1', '2020-07-01 19:00:00', 3, 2),
(03, 02, 'Event3', 'the first event of user2', '2020-07-02 20:15:00', 4, 3),
(04, 02, 'Event4', 'the second event of user2', '2020-06-02 19:30:00', 2, 4);

INSERT INTO business_users VALUES
(1, 'Test-User Vorname', 'Test-User Nachname', 'alex@fhms.de', 'EUSER');

INSERT INTO locations (id, latitude, longitude) VALUES
(1, 50.00, 50.00)
(2, 60.00, 70.00),
(3, 70.00, 60.00),
(4, 80.00, 80.00);

INSERT INTO preferences(id, value) VALUES
(1,'Music'),
(2, 'Sport'),
(3, 'Cars'),
(4, 'Books');

INSERT INTO event_preferences (event_id, preference_id) VALUES
(1,1),
(2,1),
(2,2),
(3,1),
(3,3),
(4,1),
(4,2),
(4,3),
(4,4);
