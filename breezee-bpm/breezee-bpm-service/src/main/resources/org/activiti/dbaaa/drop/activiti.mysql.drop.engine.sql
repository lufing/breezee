drop index ACT_IDX_EXEC_BUSKEY on BPM_ACT_RU_EXECUTION;
drop index ACT_IDX_TASK_CREATE on BPM_ACT_RU_TASK;
drop index ACT_IDX_IDENT_LNK_USER on BPM_ACT_RU_IDENTITYLINK;
drop index ACT_IDX_IDENT_LNK_GROUP on BPM_ACT_RU_IDENTITYLINK;
drop index ACT_IDX_VARIABLE_TASK_ID on BPM_ACT_RU_VARIABLE;
drop index ACT_IDX_INFO_PROCDEF on BPM_ACT_PROCDEF_INFO;

alter table BPM_ACT_GE_BYTEARRAY
    drop FOREIGN KEY ACT_FK_BYTEARR_DEPL;

alter table BPM_ACT_RU_EXECUTION
    drop FOREIGN KEY ACT_FK_EXE_PROCINST;

alter table BPM_ACT_RU_EXECUTION
    drop FOREIGN KEY ACT_FK_EXE_PARENT;

alter table BPM_ACT_RU_EXECUTION
    drop FOREIGN KEY ACT_FK_EXE_SUPER;

alter table BPM_ACT_RU_EXECUTION
    drop FOREIGN KEY ACT_FK_EXE_PROCDEF;

alter table BPM_ACT_RU_IDENTITYLINK
    drop FOREIGN KEY ACT_FK_TSKASS_TASK;

alter table BPM_ACT_RU_IDENTITYLINK
    drop FOREIGN KEY ACT_FK_ATHRZ_PROCEDEF;

alter table BPM_ACT_RU_TASK
	drop FOREIGN KEY ACT_FK_TASK_EXE;

alter table BPM_ACT_RU_TASK
	drop FOREIGN KEY ACT_FK_TASK_PROCINST;

alter table BPM_ACT_RU_TASK
	drop FOREIGN KEY ACT_FK_TASK_PROCDEF;

alter table BPM_ACT_RU_VARIABLE
    drop FOREIGN KEY ACT_FK_VAR_EXE;

alter table BPM_ACT_RU_VARIABLE
	drop FOREIGN KEY ACT_FK_VAR_PROCINST;

alter table BPM_ACT_RU_VARIABLE
    drop FOREIGN KEY ACT_FK_VAR_BYTEARRAY;

alter table BPM_ACT_RU_JOB
    drop FOREIGN KEY ACT_FK_JOB_EXCEPTION;

alter table BPM_ACT_RU_EVENT_SUBSCR
    drop FOREIGN KEY ACT_FK_EVENT_EXEC;

alter table BPM_ACT_RE_MODEL
    drop FOREIGN KEY ACT_FK_MODEL_SOURCE;

alter table BPM_ACT_RE_MODEL
    drop FOREIGN KEY ACT_FK_MODEL_SOURCE_EXTRA;

alter table BPM_ACT_RE_MODEL
    drop FOREIGN KEY ACT_FK_MODEL_DEPLOYMENT;

alter table BPM_ACT_PROCDEF_INFO
    drop FOREIGN KEY ACT_FK_INFO_JSON_BA;

alter table BPM_ACT_PROCDEF_INFO
    drop FOREIGN KEY ACT_FK_INFO_PROCDEF;

drop index ACT_IDX_ATHRZ_PROCEDEF on BPM_ACT_RU_IDENTITYLINK;
drop index ACT_IDX_EVENT_SUBSCR_CONFIG_ on BPM_ACT_RU_EVENT_SUBSCR;

drop table if exists BPM_ACT_GE_PROPERTY;
drop table if exists BPM_ACT_RU_VARIABLE;
drop table if exists BPM_ACT_GE_BYTEARRAY;
drop table if exists BPM_ACT_RE_DEPLOYMENT;
drop table if exists BPM_ACT_RE_MODEL;
drop table if exists BPM_ACT_RU_IDENTITYLINK;
drop table if exists BPM_ACT_RU_TASK;
drop table if exists BPM_ACT_RE_PROCDEF;
drop table if exists BPM_ACT_RU_EXECUTION;
drop table if exists BPM_ACT_RU_JOB;
drop table if exists BPM_ACT_RU_EVENT_SUBSCR;
drop table if exists BPM_ACT_EVT_LOG;
drop table if exists BPM_ACT_PROCDEF_INFO;