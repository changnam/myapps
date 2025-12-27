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

create table filecontents (file_path varchar(512)
, file_name varchar(256)
, line_num int
, line_text varchar(20000)
, work_timestamp timestamp);

create table elements (file_path varchar(512)
, file_name varchar(256)
, depth int
, element_name varchar(256)
, element_id int
, parent_name varchar(256)
,parent_id int
,attr_name varchar(256)
,attr_value text(30000));

