create table clan
(
    id   serial
        constraint clan_pk
            primary key,
    name varchar(255) not null,
    gold integer
);

create table task
(
    id   serial
        constraint task_pk
            primary key,
    task varchar(255) not null,
    gold integer not null
);

create table users
(
    id   serial
        constraint users_pk
            primary key,
    username varchar(255) not null
);

create table gold_history
  (
      id            serial
          constraint gold_history_pk
              primary key,
      clan_id       integer      not null
          constraint gold_history_clan_id_fk
              references clan,
      task_id       integer
          constraint gold_history_task_id_fk
              references task,
      user_id       integer
          constraint gold_history_users_id_fk
              references users,
      gold_add_type varchar(255) not null,
      created_time  timestamp    not null,
      gold          integer      not null
  );

insert into task(task, gold) values ('ARRANGE_A_FAIR', 10), ('CAPTURE_THE_VILLAGE', 15), ('ROB_MERCHANTS', 35);

insert into users(username) values ('player1'), ('player2'), ('player3');