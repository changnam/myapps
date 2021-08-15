drop table if exists files;

CREATE TABLE files (id bigint auto_increment
, file_path varchar(512)
, file_name varchar(512)
, file_size bigint
, file_ext varchar(512)
, last_mod_time timestamp
, last_access_time timestamp
, creation_time timestamp
, runjob_time timestamp
, runjob_id int
, primary key (id));
