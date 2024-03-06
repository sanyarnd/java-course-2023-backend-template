create TABLE links (
    link_id serial PRIMARY KEY,
    url varchar(255) not null,
    updated timestamp not null
);
