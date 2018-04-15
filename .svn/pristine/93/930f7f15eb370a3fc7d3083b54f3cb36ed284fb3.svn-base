--割接系统参数
create or replace procedure transparam
as 
cursor cur_param is select op."ID",op."NAME",op."VALUE",op."TYPE",op."COMMENT",ta.area_id from oldline.param op inner join temp_area ta on op.city=ta.area_name;
crow cur_param%ROWTYPE;
begin 
  for crow in cur_param loop
  insert into axx_t_param(param_id,param_name,param_value,param_type,memo,area_id) values(crow."ID",crow."NAME",crow."VALUE",crow."TYPE",crow."COMMENT",crow.area_id);
  end loop;  
  commit;  
end;

--割接组织关系
create or replace procedure transorg
as 
vpri_key varchar2(10);
vpri_num number(14);
varea_id number(14);
cursor cur_org is select distinct oo.orgid,oo.orgname,oo.shortname,oo.uporgid,ta.area_id,oo.centerx,oo.centery,oo.distance,oo.email 
from oldline.organization oo inner join temp_area ta on oo.citykey=ta.area_name;
crow cur_org%ROWTYPE;
begin 
  for crow in cur_org loop
      vpri_key :=crow.orgid;
      select SEQ_ORGANIZATION_ID.NEXTVAL into vpri_num from dual;   
       insert into AXX_T_ORGANIZATION values(vpri_num,crow.orgname,crow.shortname,crow.uporgid,
      crow.area_id,crow.centerx,crow.centery,crow.distance,crow.email);
      insert into temp_key(pri_key,pri_seq,area_id,tab_name) values(vpri_key,vpri_num,crow.area_id,'AXX_T_ORGANIZATION');
  end loop;  
  commit;  
end;

--割接用户，角色
create or replace procedure transuserinfo
as
vpri_key varchar2(40);
vpri_num number(14);
varea_id number(9);
vson_area_id number(9);
vorg_id number(9);
vrole number(9);
vrole_name varchar2(50);
cursor cur_userinfo is select "UID",username,pwd,orgid,"ROLE",email,sex,age,city from oldline.userinfo;
crow cur_userinfo%ROWTYPE;
begin
 for crow in cur_userinfo loop
   vpri_key :=crow."UID";
   select design_order_seq.NEXTVAL into vpri_num from dual;
   select count(1) into varea_id from temp_area where area_name=crow.city;
   if varea_id=0 then
     varea_id :='';
     vson_area_id :='';
    else
      select area_id,son_area_id into varea_id,vson_area_id from temp_area where area_name=crow.city;
    end if;
    select count(1) into vorg_id from temp_key where pri_key=crow.orgid and tab_name='AXX_T_ORGANIZATION';
    if vorg_id=0 then
      vorg_id :='';
      else
        select pri_seq into vorg_id from temp_key where pri_key=crow.orgid and tab_name='AXX_T_ORGANIZATION';
     end if;
     vrole_name :=crow."ROLE";
     if vrole_name='管理员' then
       select role_id into vrole from TB_BASE_ROLE where role_no='AXX_ADMIN';
       elsif vrole_name='管理人员' then
         select role_id into vrole from TB_BASE_ROLE where role_no='AXX_GLRY';
       elsif vrole_name='技术人员' then
         select role_id into vrole from TB_BASE_ROLE where role_no='AXX_JSY';
       elsif vrole_name='看护员' then
         select role_id into vrole from TB_BASE_ROLE where role_no='AXX_KHY';
       elsif vrole_name='段长' then
         select role_id into vrole from TB_BASE_ROLE where role_no='AXX_DZ';
       else 
         select role_id into vrole from TB_BASE_ROLE where role_no='AXX_XXY';
       end if;
   insert into tb_base_staff(STAFF_ID,STAFF_NO,PASSWORD,PASSWORD_,STAFF_NAME,ORG_ID,STATUS,EMAIL,SEX,AGE,area_id,son_area_id)values(vpri_num,crow."UID",crow.pwd,'A;:Jvaik:Zj5+88Zie''9eUc(;gYd)8kd',crow.username,vorg_id,0,crow.email,crow.sex,crow.age,varea_id,vson_area_id);
   insert into TB_BASE_STAFF_ROLE(staff_id,role_id) values(vpri_num,vrole);
   insert into TB_BASE_STAFF_SOFT(staff_id,Software_Id) values(vpri_num,13);
   insert into temp_key(pri_key,pri_seq,area_id,tab_name) values(vpri_key,vpri_num,varea_id,'TB_BASE_STAFF');
   end loop;
   commit;
end;

--割接光缆数据
 create or replace procedure transline
  as
  vpri_key varchar2(10);
  vpri_num number(14);
  varea_id number(9);
  cursor cur_line is select lineid,linename,fibergrade from oldline.line;
  crow cur_line%ROWTYPE;
  begin
    for crow in cur_line loop
      vpri_key :=crow.lineid;
      select SEQ_CABLE_ID.Nextval into vpri_num from dual;
      insert into axx_t_cable(cable_id,cable_code,cable_name,fiber_grade)values(vpri_num,crow.lineid,crow.linename,crow.fibergrade);
      insert into temp_key(pri_key,pri_seq,area_id,tab_name) values(vpri_key,vpri_num,varea_id,'AXX_T_CABLE');
      end loop;
      commit;
    end;
    
--割接中继段数据    
create or replace procedure transrelay
  as
  vpri_key varchar2(10);
  vpri_num number(14);
  cursor cur_relay is  select ore.relayid,ore.relayname,ore.protectgrade,tk.pri_seq,ore.orderid from oldline.relay ore inner join temp_key tk on ore.lineid=tk.pri_key where tk.tab_name='AXX_T_CABLE';
  crow cur_relay%ROWTYPE;
  begin
    for crow in cur_relay loop
      vpri_key :=crow.relayid;
      select SEQ_RELAY_ID.Nextval into vpri_num from dual;
       insert into axx_t_relay(relay_id,relay_name,cable_id,protect_grade,relay_code,relay_order)values(vpri_num,crow.relayname,crow.pri_seq,crow.protectgrade,crow.relayid,crow.orderid);
       insert into temp_key(pri_key,pri_seq,tab_name) values(vpri_key,vpri_num,'AXX_T_RELAY');
      end loop;
      commit;
    end;
    
--割接巡线段子存储过程
create or replace procedure translineinfo(areaid in number,tabname in varchar2)
as
  vpri_key varchar2(10);
  vpri_num number(14);
  type refcur_type is ref cursor;  
  cur_lineinfo refcur_type;
  vlinename varchar2(100);
  vdistance number(18,3);
  vrelayid number(14);
  vuserid number(14);
  begin
    open cur_lineinfo for 'select ol.lineid,ol.linename,ol.distance,tk.pri_seq relayid,tk2.pri_seq userid from oldline.'||tabname||' ol inner join temp_key tk on ol.relayid=tk.pri_key and tk.tab_name=''AXX_T_RELAY'' inner join temp_key tk2 on ol."UID"=tk2.pri_key and tk2.tab_name=''TB_BASE_STAFF''';
    loop
    fetch cur_lineinfo into vpri_key,vlinename,vdistance,vrelayid,vuserid;
      select SEQ_AXX_T_LINE_INFO.Nextval into vpri_num from dual;
      insert into axx_t_line_info(LINE_ID,line_name,distance,relay_id,inspect_id,area_id)values(vpri_num,vlinename,vdistance,vrelayid,vuserid,areaid);
      insert into temp_key(pri_key,pri_seq,area_id,tab_name) values(vpri_key,vpri_num,areaid,'AXX_T_LINE_INFO');
    Exit when cur_lineinfo%NOTFOUND;
    end loop;
    commit; 
  end;
  
  --割接巡线段数据父存储过程
 create or replace procedure transalllineinfo
  as
  varea_id number(9);
  vtab_name varchar2(50);
  cursor cur_area is select area_name,area_id from temp_area;
  crow cur_area%ROWTYPE;
  begin
    for crow in cur_area loop
      varea_id :=crow.area_id;
      if varea_id=69 then
       vtab_name :='lineinfo';
       else
       vtab_name :='lineinfo_'||crow.area_name;
      end if;
      translineinfo(varea_id,vtab_name);  
    end loop;
    insert into axx_t_relay_area(RELAY_ID,AREA_ID) select distinct RELAY_ID,AREA_ID FROM AXX_T_LINE_INFO;
    insert into axx_t_cable_area(CABLE_ID,AREA_ID) select distinct tr.cable_id,tra.area_id from axx_t_relay tr inner join axx_t_relay_area tra on tr.relay_id=tra.relay_id;
  end;
  
    --割接巡线点子存储过程
create or replace procedure transsiteinfo(areaid in number,tabname in varchar2)
as
  vpri_key varchar2(10);
  vpri_num number(14);
  type refcur_type is ref cursor;  
  cur_siteinfo refcur_type;
  vsite_name varchar2(500);
  vx number;
  vy number;
  vaddress varchar2(500);
  vlevel varchar2(50);
  vtype number;
  vsitetype varchar2(50);
  vdistance number;
  begin
    open cur_siteinfo for 'select os.siteid,os.x,os.y,os.address,os.sitename,os.protectlevel,os.sitetype from oldline.'||tabname||' os where os.sitename is not null';
    loop
    fetch cur_siteinfo into vpri_key,vx,vy,vaddress,vsite_name,vlevel,vsitetype;
      select SEQ_AXX_T_SITE.Nextval into vpri_num from dual;
      if vlevel='关键点' then
        vtype :=1;
       else
        vtype :=2;
       end if;
       select count(1) into vdistance from oldline.axx_sitematchinfo oas where oas.siteid=vpri_key and oas.area_id=areaid;
       if vdistance=0 then
        vdistance :='';
       else
        select oas.maxdistance into vdistance from oldline.axx_sitematchinfo oas where oas.siteid=vpri_key and oas.area_id=areaid;
       end if;
      insert into axx_t_site(SITE_ID,SITE_NAME,LONGITUDE,LATITUDE,SITE_TYPE,SITE_PROPERTY,ADDRESS,relay_id,site_match,area_id)values(vpri_num,vsite_name,vx,vy,vtype,vsitetype,vaddress,0,vdistance,areaid);
      insert into temp_key(pri_key,pri_seq,area_id,tab_name) values(vpri_key,vpri_num,areaid,'AXX_T_SITE');
    Exit when cur_siteinfo%NOTFOUND;
    end loop;
    commit; 
  end;
  
  --割接巡线点父存储过程
   create or replace procedure transallsiteinfo
  as
  varea_id number(9);
  vtab_name varchar2(50);
  cursor cur_area is select area_name,area_id from temp_area;
  crow cur_area%ROWTYPE;
  begin
    for crow in cur_area loop
      varea_id :=crow.area_id;
      if varea_id=69 then
       vtab_name :='siteinfo';
       else
       vtab_name :='siteinfo_'||crow.area_name;
      end if;
      transsiteinfo(varea_id,vtab_name);  
    end loop;
  end;
  
 --存储过程割接巡线段和巡线段关系子存储过程
 create or replace procedure transsegment(areaid in number,tabname in varchar2)
 as
  vline_id number(14);
  vsite_id number(14);
  type refcur_type is ref cursor;  
  cur_segment refcur_type;
  vrelay_id number(14);
  vseq number;
  begin
    open cur_segment for 'select tk.pri_seq line_id,tk2.pri_seq site_id,oli.sequence,tli.relay_id from oldline.'||tabname||' oli inner join temp_key tk on oli.lineid=tk.pri_key and tk.area_id='||areaid||' and tk.tab_name=''AXX_T_LINE_INFO'' inner join temp_key tk2 on oli.siteid=tk2.pri_key and tk2.area_id='||areaid||' and tk2.tab_name=''AXX_T_SITE'' inner join axx_t_line_info tli on tli.line_id=tk.pri_seq';
    loop
    fetch cur_segment into vline_id,vsite_id,vseq,vrelay_id;
    insert into AXX_T_LINE_SITE(SITE_ID,LINE_ID,SITE_ORDER)values(vsite_id,vline_id,vseq);
    update axx_t_site ats set ats.relay_id=vrelay_id where ats.site_id=vsite_id;
    Exit when cur_segment%NOTFOUND;
    end loop;
    commit; 
  end;
  
  --存储过程割接巡线点和巡线段关系父存储过程
 create or replace procedure transallsegment
  as
  varea_id number(9);
  vtab_name varchar2(50);
  cursor cur_area is select area_name,area_id from temp_area;
  crow cur_area%ROWTYPE;
  begin
    for crow in cur_area loop
      varea_id :=crow.area_id;
      if varea_id=69 then
       vtab_name :='linesegment'; 
       else
       vtab_name :='linesegment_'||crow.area_name;
      end if; 
       transsegment(varea_id,vtab_name);
    end loop;
  end;
  
  --存储过程割接外力点
 create or replace procedure transalloutsite
  as
  begin
    update temp_OUT_SITE tos set tos.status=1;
    update temp_OUT_SITE tos set tos.infosource=1 where tos.infosource='日常巡回时现场发现';
    update temp_OUT_SITE tos set tos.infosource=2 where tos.infosource='对外联系宣传时得知';
    update temp_OUT_SITE tos set tos.infosource=3 where tos.infosource='施工/建设方主动告知';
    update temp_OUT_SITE tos set tos.hiddengrade=1 where tos.hiddengrade='Ⅰ';
    update temp_OUT_SITE tos set tos.hiddengrade=2 where tos.hiddengrade='Ⅱ';
    update temp_OUT_SITE tos set tos.hiddengrade=3 where tos.hiddengrade='Ⅲ';
    update temp_OUT_SITE tos set tos.hiddengrade=4 where tos.hiddengrade='隐患';
    update temp_OUT_SITE tos set tos.linegrade=1 where tos.linegrade='一级';
    update temp_OUT_SITE tos set tos.linegrade=2 where tos.linegrade='二级';
    update temp_OUT_SITE tos set tos.existagreement=1 where tos.existagreement='是';
    update temp_OUT_SITE tos set tos.existagreement=0 where tos.existagreement='否';
    update temp_OUT_SITE tos set tos.affectedfiber=(case when instr(tos.affectedfiber,'、')>0
    then substr(tos.affectedfiber,0,instr(tos.affectedfiber,'、')-1) else tos.affectedfiber end);
    update temp_OUT_SITE tos set tos.affectedfiber=(case when instr(tos.affectedfiber,',')>0
    then substr(tos.affectedfiber,0,instr(tos.affectedfiber,',')-1) else tos.affectedfiber end);
    update temp_OUT_SITE tos set tos.affectedfiber=( select distinct tc.cable_id from axx_t_cable tc where tc.cable_name=tos.affectedfiber);
    update temp_OUT_SITE tos set tos.relaypart=(select distinct tr.relay_id from axx_t_relay tr 
    inner join axx_t_relay_area tra on tr.relay_id=tra.relay_id
    inner join axx_t_cable tc on tc.cable_id=tr.cable_id
    inner join axx_t_cable_area tca on tc.cable_id=tca.cable_id
    where tra.area_id=tos.area_id and tca.area_id=tos.area_id and tr.relay_name=tos.relaypart and tc.cable_id=tos.affectedfiber);
    update temp_OUT_SITE tos set tos.fiberuid=(select tbs.staff_id from tb_base_staff tbs where tbs.staff_no=tos.fiberuid);
    commit;
  end;
  
  --外力点正式转移
    create or replace procedure transoutsiteinfo
  as
  vpri_key varchar2(50);
  vpri_num number(14);
  cursor cur_outinfo is select tos.siteid,tos.sitename,tos.x,tos.y,tos.status,tos.infosource,tos.affectedfiber,tos.landmarkno,
  tos.linegrade,tos.relaypart,tos.fibergrade,tos.linepart,tos.hiddengrade,tos.constructioncompany,tos.constructionaddress,
  tos.constructioncontent,tos.startdate,tos.planenddate,tos.constructionprincipal,tos.constructionprincipaltel,tos.existagreement,
  tos.fiberuid,tos.area_id from temp_OUT_SITE tos;
  crow cur_outinfo%ROWTYPE;
  begin
    for crow in cur_outinfo loop
      select SEQ_AXX_MAIN_OUT_SITE.Nextval into vpri_num from dual;
      insert into AXX_MAIN_OUT_SITE(OUT_SITE_ID,SITE_NAME,X,Y,CON_STATUS,INFO_SOURCE,AFFECTED_FIBER,RELAY_PART,LANDMARKNO,IS_AGREEMENT,
      FIBER_LEVEL,LINE_PART,CON_COMPANY,CON_ADDRESS,CON_CONTENT,SITE_DANGER_LEVEL,CON_STARTDATE,PRE_ENDDATE,CON_REPONSIBLE_BY,
      CON_REPONSIBLE_BY_TEL,FIBER_EPONSIBLE_BY,AREA_ID,delete_flag)VALUES(vpri_num,crow.sitename,crow.x,crow.y,crow.status,crow.infosource,
      crow.affectedfiber,crow.relaypart,crow.landmarkno,crow.existagreement,crow.linegrade,crow.linepart,crow.constructioncompany,crow.constructionaddress,crow.constructioncontent,
      crow.hiddengrade,crow.startdate,crow.planenddate,crow.constructionprincipal,crow.constructionprincipaltel,crow.fiberuid,crow.area_id,1);
      insert into temp_key(pri_key,pri_seq,area_id,tab_name) values(vpri_key,vpri_num,crow.area_id,'AXX_MAIN_OUT_SITE');
    end loop;
    commit;
  end;
  
  --割接外力点维护方案  
create or replace procedure transoutsitescheme
as
vpri_key number(14);
voutsiteid number(14);
vuser_id number(14);
vimp_id number(14);
vcount number;
cursor cur_scheme is select teo.schemeid,teo.schemename,teo.outsiteid,teo.content,teo."UID",teo.updatedate,teo.adddate,teo.committimeout,teo.fencerange,teo.implementuid,teo.area_id from TEMP_OUTSITEMAINTAINSCHEME teo;
crow cur_scheme%ROWTYPE;
begin
  for crow in cur_scheme loop
    select SEQ_AXX_OS_MAINTAIN_SCHEME.nextval into vpri_key from dual;
    select tk.pri_seq into voutsiteid from temp_key tk where tk.tab_name='AXX_MAIN_OUT_SITE' and tk.area_id=crow.area_id and tk.pri_key=crow.outsiteid;
    select count(1) into vuser_id from tb_base_staff tbs where tbs.staff_no=crow."UID";   
    if vuser_id=0 then
       vuser_id :='';
    else
      select tbs.staff_id into vuser_id from tb_base_staff tbs where tbs.staff_no=crow."UID";   
    end if;  
    select count(1) into vimp_id from tb_base_staff tbs where tbs.staff_no=crow.implementuid;   
    if vimp_id=0 then
       vimp_id :='';
    else
       select tbs.staff_id into vimp_id from tb_base_staff tbs where tbs.staff_no=crow.implementuid;  
    end if;
    insert into AXX_OUT_SITE_MAINTAIN_SCHEME(scheme_id,scheme_name,OUT_SITE_ID,MS_CONTENT,User_Id,Put_Uid,Status,Is_Timeout,Creation_Time,delete_flag,Ts_Res)
    values(vpri_key,crow.schemename,voutsiteid,crow.content,vuser_id,vimp_id,0,0,crow.adddate,1,'000');
  end loop;
  commit;
end;

--外力点看护计划割接
create or replace procedure transoutsiteplan
  as 
  vpri_key number(14);
  vuser_id number(14);
  vstart_date date;
  vend_date date;
  voutsiteid number(14);
  cursor cur_oustieplan is select planid,"UID",outsiteid,area_id from TEMP_AXX_OUTSITEPLAN;
  crow cur_oustieplan%ROWTYPE;
  begin
    for crow in cur_oustieplan loop
      select SEQ_AXX_OUT_SITE_PLAN.Nextval into vpri_key from dual;
      select count(1) into vuser_id from tb_base_staff tbs where tbs.staff_no=crow."UID";   
      if vuser_id=0 then
        vuser_id :='';
      else
        select tbs.staff_id into vuser_id from tb_base_staff tbs where tbs.staff_no=crow."UID";  
      end if;
      select tk.pri_seq into voutsiteid from temp_key tk where tk.tab_name='AXX_MAIN_OUT_SITE' and tk.area_id=crow.area_id and tk.pri_key=crow.outsiteid;
      select con_startdate,pre_enddate into vstart_date,vend_date from axx_main_out_site where out_site_id=voutsiteid;
      insert into AXX_OUT_SITE_PLAN(plan_id,start_date,End_Date,user_id,Out_Site_Id,creation_time,Delete_Flag)values(vpri_key,vstart_date,vend_date,vuser_id,voutsiteid,vstart_date,'0');
      insert into temp_key(pri_key,pri_seq,area_id,tab_name) values(crow.planid,vpri_key,crow.area_id,'AXX_OUT_SITE_PLAN'); 
      end loop;
    commit;
  end;
  
 --外力点埋深探位存储过程 
create or replace procedure transoutsitedepth
  as 
  vpri_key number(14);
  voutsiteid number(14);
  vuser_id number(14);
  cursor cur_depth is select "ID",outsiteid,stoneno,"DEPTH","DATE","UID",memo,area_id from oldline.axx_outsitedepth;
  crow cur_depth%ROWTYPE;
  begin
     for crow in cur_depth loop
       select SEQ_AXX_T_DEPTH_PROBE.Nextval into vpri_key from dual;
       select tk.pri_seq into voutsiteid from temp_key tk where tk.tab_name='AXX_MAIN_OUT_SITE' and tk.area_id=crow.area_id and tk.pri_key=crow.outsiteid;
       select count(1) into vuser_id from tb_base_staff tbs where tbs.staff_no=crow."UID";   
       if vuser_id=0 then
         vuser_id :='';
       else
        select tbs.staff_id into vuser_id from tb_base_staff tbs where tbs.staff_no=crow."UID";  
       end if;
       insert into AXX_T_DEPTH_PROBE(PROBE_ID,OUT_SITE_ID,MARKSTONE,DEPTH,UPLOAD_TIME,USER_ID,REMARK)values(vpri_key,voutsiteid,crow.stoneno,crow."DEPTH",crow."DATE",vuser_id,crow.memo);
     end loop;
     commit;
  end;
 
  --割接外力点电子围栏
 create or replace procedure transpolygon
  as 
  vpri_key number(14);
  vplanid number(14);
  cursor cur_polygon is select polygonid,planid,x,y,"SEQUENCE",plantime,area_id from oldline.axx_outsiteplanpolygon;
  crow cur_polygon%ROWTYPE;
  begin
     for crow in cur_polygon loop
       select SEQ_AXX_OUT_SITE_PLAN_POLYGN.Nextval into vpri_key from dual;
       select tk.pri_seq into vplanid from temp_key tk where tk.tab_name='AXX_OUT_SITE_PLAN' and tk.area_id=crow.area_id and tk.pri_key=crow.planid;
      insert into AXX_OUT_SITE_INPUT_POINTS(INPUT_POINTS_ID,PLAN_ID,X,Y,CREATION_TIME,PARENT_CITY,DELETE_FLAG,SITE_ORDER)values(vpri_key,vplanid,crow.x,crow.y,crow.plantime,crow.area_id,1,crow."SEQUENCE");
     end loop;
     commit;
  end;
  
    --割接看护计划时间段
create or replace procedure transplanparttime
  as 
  vplanid number(14);
  cursor cur_parttime is select planparttimeid,planid,starttime,endtime,area_id from oldline.axx_outsiteplanparttime;
  crow cur_parttime%ROWTYPE;
  begin
	  insert into AXX_OUT_SITE_PART_TIME_MODEL(PART_TIME_ID,START_TIME,End_Time,CREATED_BY,CREATION_TIME,UPDATE_TIME,IS_ENABLED,PARENT_CITY,Delete_Flag,IS_PRITERMISSION)
      select SEQ_OS_PART_TIME_MODEL.Nextval,oo.starttime,oo.endtime,tbs.staff_id,oo.adddate,oo.updatedate,1,tk.area_id,1,oo.enabled from oldline.outsiteparttimemodel oo 
      inner join temp_area tk on oo.city=tk.area_name inner join tb_base_staff tbs on oo."UID"=tbs.staff_no;
      
     for crow in cur_parttime loop
       select count(1) into vplanid from temp_key tk where tk.tab_name='AXX_OUT_SITE_PLAN' and tk.area_id=crow.area_id and tk.pri_key=crow.planid;
       if vplanid>0 then
          select tk.pri_seq into vplanid from temp_key tk where tk.tab_name='AXX_OUT_SITE_PLAN' and tk.area_id=crow.area_id and tk.pri_key=crow.planid;
          insert into AXX_OUT_SITE_PLAN_PART_TIME(PLAN_ID,START_TIME,END_TIME,AREA_ID,DELETE_FLAG)values(vplanid,crow.starttime,crow.endtime,crow.area_id,1);
       end if;
     end loop;
     commit;
  end;
  
--调用存储过程
create or replace procedure transAllaxx
as
begin
  transorg();--割接组织关系
  transuserinfo();--割接用户信息
  transline();--割接光缆
  transrelay();--割接中继段
  transalllineinfo();--割接巡线段
  transallsiteinfo();--割接巡线点
  transallsegment();--割接巡线段和巡线点关系表
  transalloutsite();--复制外力点，同时对外力点表字段进行转换
  transoutsiteinfo();--割接外力点
  transoutsitescheme();--割接维护方案
  transoutsiteplan();--割接外力点看护计划 
  transoutsitedepth();--割接外力点埋深探位
  transpolygon();--割接外力点电子围栏
end;

 
 

   
