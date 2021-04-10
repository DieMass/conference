create table conference
(
    id    bigint generated by default as identity,
    title varchar(255),
    primary key (id)
);
create table conference_listeners
(
    conference_id bigint not null,
    listeners_id  bigint not null
);
create table conference_talks
(
    conference_id bigint not null,
    talks_id      bigint not null
);
create table room
(
    id   bigint generated by default as identity,
    name varchar(255),
    size integer,
    primary key (id)
);
create table schedule_cell
(
    id         bigint generated by default as identity,
    begin_time timestamp,
    end_time   timestamp,
    room_id    bigint,
    talk_id    bigint,
    primary key (id)
);
create table talk
(
    id            bigint generated by default as identity,
    conference_id bigint,
    description   varchar(255),
    title         varchar(255),
    primary key (id)
);
create table talk_speakers
(
    talk_id     bigint not null,
    speakers_id bigint not null,
    primary key (talk_id, speakers_id)
);
create table users
(
    id       bigint generated by default as identity,
    email    varchar(255),
    password varchar(255),
    role     varchar(255),
    primary key (id)
);
alter table conference_talks
    drop constraint if exists UK_ptk4m3xu2lcg49rebkshi3i2g;
alter table conference_talks
    add constraint UK_ptk4m3xu2lcg49rebkshi3i2g unique (talks_id);
alter table conference_listeners
    add constraint FKk1rqe4jcey0ipvunmdeuqyou5 foreign key (listeners_id) references users;
alter table conference_listeners
    add constraint FKer6w5lpua6e67h4e1i32enmkt foreign key (conference_id) references conference;
alter table conference_talks
    add constraint FKi1uk6blx09ahe3c2qmwrac7aq foreign key (talks_id) references talk;
alter table conference_talks
    add constraint FKdc0bam6ur44bystdx4w391xwt foreign key (conference_id) references conference;
alter table schedule_cell
    add constraint FKru23d78pml1t4ir4qh37ye7c7 foreign key (room_id) references room;
alter table schedule_cell
    add constraint FK3ypq9tahvgmj99bi6169lih3y foreign key (talk_id) references talk;
alter table talk
    add constraint FKpite4ah2ymnhh04ymd15s7v50 foreign key (conference_id) references conference;
alter table talk_speakers
    add constraint FKfohl1x5wl5lt9vqsxy7p76269 foreign key (speakers_id) references users;
alter table talk_speakers
    add constraint FK86q91d30awwah0f3r9t8k6wjy foreign key (talk_id) references talk;