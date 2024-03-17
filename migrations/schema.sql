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

--changeset ocelot335:4
alter table subscribes drop constraint subscribes_chatid_fkey;
alter table subscribes add constraint subscribes_chatid_fkey foreign key(chatId) references chats(telegramId) on delete cascade;
--rollback alter table subscribes drop constraint subscribes_chatid_fkey; alter table subscribes add constraint subscribes_chatid_fkey foreign key(chatId) references chats(id) on delete cascade;


--changeset ocelot335:5
alter table chats drop constraint chats_pkey;
alter table chats drop column id;

alter table chats add primary key (telegramId);
--rollback alter table chats drop constraint telegramId; alter table chats add column id bigint generated always as identity; alter table chats add primary key (id); alter table chats add unique (telegramId);

--changeset ocelot335:6
alter table links add column checked_at timestamp with time zone not null default NOW();
--rollback alter table links drop column checked_at;
