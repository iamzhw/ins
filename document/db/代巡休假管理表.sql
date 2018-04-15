alter   table   axx_t_replace_holiday
add(HOLIDAY_DESC   VARCHAR2(500),REST_TYPE  NUMBER(1)) ;


/**
 * 建立子增加序列号
 * RELAYTESTINFO表   该主键 RELAYINFOID 自增长
 */
CREATE SEQUENCE seq_relaytestinfo  --序列名
INCREMENT BY 1   -- 每次加几个  
START WITH 361       -- 从1开始计数  

/**
 * 表RELAYTESTINFOSUB I_INDEX 字段自增长
 * 
 */
CREATE SEQUENCE seq_i_index  --序列名
INCREMENT BY 1   -- 每次加几个  
START WITH 15303     -- 从1开始计数  

/**
 * FIBERLINEREPORT表  字段 ID 自增长
 * 
 */
CREATE SEQUENCE seq_FIBERLINEREPORT_id  --序列名
INCREMENT BY 1   -- 每次加几个  
START WITH 388       -- 从1开始计数  

/**
 * STEPS表 字段  STEPID  自增长
 */
CREATE SEQUENCE seq_STEPS_STEPID  --序列名
INCREMENT BY 1   -- 每次加几个  
START WITH 14154       -- 从1开始计数  


/**
 * report0204sub表  字段  R_ID  自增长
 */
CREATE SEQUENCE seq_report204sub_rid  --序列名
INCREMENT BY 1   -- 每次加几个  
START WITH 6501      -- 从1开始计数  


/**
 * AXX_STEP_TOUR_EQUIP表   主键EQUIP_ID  自增长
 */
CREATE SEQUENCE seq_STEP_TOUR_EQUIP  --序列名
INCREMENT BY 1   -- 每次加几个  
START WITH 11    -- 从1开始计数 


/**
 * AXX_STEP_TOUR  主键FAC_ID 自增长
 */
CREATE SEQUENCE seq_AXX_STEP_TOUR  --序列名
INCREMENT BY 1   -- 每次加几个  
START WITH 8    -- 从1开始计数 


/*路由设计检查记录表 AXX_EQUIP_CHECK 主键自增长*/
create sequence seq_AXX_EQUIP_CHECK_ID

/*步巡路由分配表 AXX_STEP_EQUIP_ALLOT 主键自增长*/
create sequence seq_asea_allotId

/*步巡任务表 AXX_STEP_TASK 主键自增长*/
create sequence seq_ast_taskId
