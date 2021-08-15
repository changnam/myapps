drop table if exists files;

create sequence files_seq start with 1;

CREATE TABLE files (id NUMBER
, file_path varchar2(512)
, file_name varchar2(512)
, file_size number
, file_ext varchar2(512)
, last_mod_time timestamp
, last_access_time timestamp
, creation_time timestamp
, runjob_time timestamp
, runjob_id number
, primary key (id));