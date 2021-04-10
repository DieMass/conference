INSERT INTO Conference(id, title)
values (1, 'Java Day');
INSERT INTO Talk(id, title, description, conference_id)
VALUES (1, 'Spring-потрошитель', 'Евгений Борисов как всегда покажет класс', 1),
       (2, 'ForDeleting', 'asd', 1);
INSERT INTO Conference_Talks(conference_id, talks_id)
VALUES (1, 1);
INSERT INTO USERS(id, role, email, password)
VALUES (1, 'ADMIN', 'admin1@gmail.com', '$2y$12$IPCJpXSKXDEI.ZR0hoMbBewEc3tGH5ZTvRD4JNmfsZ4XttQ1CARmS'),
       (2, 'SPEAKER', 'speaker1@gmail.com', '$2y$12$IPCJpXSKXDEI.ZR0hoMbBewEc3tGH5ZTvRD4JNmfsZ4XttQ1CARmS'),
       (3, 'SPEAKER', 'speaker2@gmail.com', '$2y$12$IPCJpXSKXDEI.ZR0hoMbBewEc3tGH5ZTvRD4JNmfsZ4XttQ1CARmS'),
       (4, 'LISTENER', 'listener1@gmail.com', '$2y$12$IPCJpXSKXDEI.ZR0hoMbBewEc3tGH5ZTvRD4JNmfsZ4XttQ1CARmS'),
       (5, 'LISTENER', 'listener2@gmail.com', '$2y$12$IPCJpXSKXDEI.ZR0hoMbBewEc3tGH5ZTvRD4JNmfsZ4XttQ1CARmS');
INSERT INTO Room(id, name, size)
VALUES (1, '1301', 24),
       (2, '1302', 2),
       (3, '1303', 1);
INSERT INTO Schedule_Cell(begin_time, end_time, room_id, talk_id)
values ('2021-04-01T06:22:00Z', '2021-04-02T06:22:00Z', 1, 1)