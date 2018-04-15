--1、删除序列，后倒入正式库的序列
drop sequence SEQ_AXX_T_DEPTH_PROBE;
drop sequence SEQ_AXX_OS_MAINTAIN_SCHEME;
drop sequence SEQ_OS_PART_TIME_MODEL;
drop sequence SEQ_AXX_OUT_SITE_PLAN;
drop sequence SEQ_AXX_MAIN_OUT_SITE;
drop sequence SEQ_AXX_T_LINE_INFO;
drop sequence SEQ_AXX_T_SITE;
drop sequence SEQ_CABLE_ID;
drop sequence SEQ_RELAY_ID;

--2、干掉测试库表
 drop table axx_t_line_site;
 drop table axx_t_site;
 drop table axx_t_line_info;
 drop table axx_t_relay_area;
 drop table axx_t_relay;
 drop table axx_t_cable_area;
 drop table axx_t_cable;
 drop table AXX_OUT_SITE_PLAN_PART_TIME;
 drop table AXX_OUT_SITE_PART_TIME_MODEL;
 drop table AXX_OUT_SITE_INPUT_POINTS;
 drop table AXX_OUT_SITE_PLAN;
 drop table AXX_T_DEPTH_PROBE;
 drop table AXX_OUT_SITE_MAINTAIN_SCHEME;
 drop table AXX_MAIN_OUT_SITE;
 drop table TB_BASE_STAFF_SOFT;
 drop table TB_BASE_STAFF_ROLE;
 drop table tb_base_staff;
 drop table AXX_T_ORGANIZATION;
 delete from temp_key;
 commit;
--3、导入正式库表结构和序列

 --4、创建外力点相关临时表
declare
   tnum number;
  begin
  select count(1) into tnum from all_tables where TABLE_NAME = 'TEMP_OUT_SITE' and OWNER='LINE'; 
  if   tnum=1   then 
      execute immediate 'drop table TEMP_OUT_SITE'; 
  end   if;
  execute immediate 'create table TEMP_OUT_SITE as select * from oldline.axx_mainoutsite';
  execute immediate 'alter table temp_OUT_SITE modify fiberuid NVARCHAR2(14)';
 
  select count(1) into tnum from all_tables where TABLE_NAME = 'TEMP_OUTSITEMAINTAINSCHEME' and OWNER='LINE'; 
  if   tnum=1   then 
      execute immediate 'drop table TEMP_OUTSITEMAINTAINSCHEME'; 
  end   if;
  execute immediate 'create table temp_outsitemaintainscheme as select * from oldline.axx_outsitemaintainscheme ao where exists(select t.schemeid from (select max(schemeid) schemeid,area_id from oldline.axx_outsitemaintainscheme group by outsiteid,area_id) t where t.schemeid=ao.schemeid and t.area_id=ao.area_id)';

  select count(1) into tnum from all_tables where TABLE_NAME = 'TEMP_AXX_OUTSITEPLAN' and OWNER='LINE'; 
  if   tnum=1   then 
      execute immediate 'drop table TEMP_AXX_OUTSITEPLAN'; 
  end   if;
  execute immediate 'create table temp_axx_outsiteplan as select * from oldline.axx_outsiteplan ao where exists(select t.planid from (select max(planid) planid,area_id from oldline.axx_outsiteplan group by outsiteid,area_id) t where t.planid=ao.planid and t.area_id=ao.area_id)';
  
  select count(1) into tnum from all_tables where TABLE_NAME = 'TEMP_AXX_OUTSITEDEPTH' and OWNER='LINE'; 
  if   tnum=1   then 
      execute immediate 'drop table TEMP_AXX_OUTSITEDEPTH'; 
  end   if;
  execute immediate 'create table temp_axx_outsitedepth as select * from oldline.axx_outsitedepth';
  end;
  
  --5、执行存储过程
  --6、数据导入到正式库
  --7、更改正式库序列的值