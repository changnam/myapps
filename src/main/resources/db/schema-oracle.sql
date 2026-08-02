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

-- CDDBA1.ELEMENTS definition

CREATE TABLE ELEMENTS 
   (	FILE_PATH VARCHAR2(512), 
	FILE_NAME VARCHAR2(64), 
	EL_DEPTH NUMBER, 
	ELEMENT_NAME VARCHAR2(64), 
	ELEMENT_ID NUMBER, 
	PARENT_NAME VARCHAR2(64), 
	PARENT_ID NUMBER, 
	ATTR_NAME VARCHAR2(128), 
	ATTR_VALUE VARCHAR2(4000), 
	CONTROL_ID NUMBER,
	GRID_DEPTH NUMBER
   )  ;
  
CREATE TABLE ELEMENTS_MAPINFO 
   (	FILE_PATH VARCHAR2(512), 
	FILE_NAME VARCHAR2(64), 
	EL_DEPTH NUMBER, 
	ELEMENT_NAME VARCHAR2(64), 
	ELEMENT_ID NUMBER, 
	PARENT_NAME VARCHAR2(64), 
	PARENT_ID NUMBER, 
	ATTR_NAME VARCHAR2(128), 
	ATTR_VALUE CLOB, 
	CONTROL_ID NUMBER, 
	GRID_DEPTH NUMBER
   ) ;