alter table comments
    add column user_id bigint not null references users(id)