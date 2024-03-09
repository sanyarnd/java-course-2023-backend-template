--liquibase formatted sql


--changeset ocelot335:1
create table chats
(
    id              bigint generated always as identity,
    telegramId          bigint,

    primary key (id),
    unique (telegramId)
)
--rollback drop table chats;


--changeset ocelot335:2
create table links
(
    id              bigint generated always as identity,
    url             text                     not null,

    primary key (id),
    unique (url)
)
--rollback drop table links;

--changeset ocelot335:3
create table subscribes
(
    chatId              bigint                     not null,
    linkId              bigint                     not null,

    foreign key(chatId) references chats(id) on delete cascade,
    foreign key(linkId) references links(id)
)
--rollback drop table subscribes;
