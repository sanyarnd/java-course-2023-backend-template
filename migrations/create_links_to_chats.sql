create table links_to_chats (
    chat_id bigint not null,
    link_id bigint not null,
    primary key (chat_id, link_id),
    foreign key (chat_id) references chats(chat_id),
    foreign key (link_id) references links(link_id)
);
