--------------------------------------------------------
--  파일이 생성됨 - 수요일-1월-25-2023   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table USER_INFO
--------------------------------------------------------

  CREATE TABLE "WCADMIN"."USER_INFO" 
   (	"USER_INFO_ID" VARCHAR2(255 BYTE), 
	"CREATE_DATE" DATE, 
	"CREATE_USER_ID" VARCHAR2(255 BYTE), 
	"CREATE_USER_NM" VARCHAR2(255 BYTE), 
	"EMAIL" VARCHAR2(255 BYTE), 
	"FIRST_NAME" VARCHAR2(255 BYTE), 
	"IS_FROM_ERP" VARCHAR2(255 BYTE), 
	"LAST_NAME" VARCHAR2(255 BYTE), 
	"LOGIN" VARCHAR2(255 BYTE), 
	"PASSWORD" VARCHAR2(255 BYTE), 
	"SITE_CD" VARCHAR2(255 BYTE), 
	"UPDATE_DATE" DATE, 
	"UPDATE_USER_ID" VARCHAR2(255 BYTE), 
	"UPDATE_USER_NM" VARCHAR2(255 BYTE), 
	"USE_YN" VARCHAR2(255 BYTE), 
	"USER_NAME" VARCHAR2(255 BYTE), 
	"GROUP_CODE" VARCHAR2(32 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
REM INSERTING into WCADMIN.USER_INFO
SET DEFINE OFF;
Insert into WCADMIN.USER_INFO (USER_INFO_ID,CREATE_DATE,CREATE_USER_ID,CREATE_USER_NM,EMAIL,FIRST_NAME,IS_FROM_ERP,LAST_NAME,LOGIN,PASSWORD,SITE_CD,UPDATE_DATE,UPDATE_USER_ID,UPDATE_USER_NM,USE_YN,USER_NAME,GROUP_CODE) values ('dc1995',to_date('22/11/24','RR/MM/DD'),'UserInfoService','UserInfoService','dc1995@fursys.com','정연석','N',null,null,'i9zv3i07','SYSTEM',to_date('23/01/06','RR/MM/DD'),'UserInfoService','UserInfoService','Y','정연석','713679e89c124e3f8fca87442d9caf08');
Insert into WCADMIN.USER_INFO (USER_INFO_ID,CREATE_DATE,CREATE_USER_ID,CREATE_USER_NM,EMAIL,FIRST_NAME,IS_FROM_ERP,LAST_NAME,LOGIN,PASSWORD,SITE_CD,UPDATE_DATE,UPDATE_USER_ID,UPDATE_USER_NM,USE_YN,USER_NAME,GROUP_CODE) values ('youngsoo_shim',to_date('22/11/24','RR/MM/DD'),'UserInfoService','UserInfoService','youngsoo_shim@fursys.com','심영수','N',null,null,'dn7f0e2y','SYSTEM',to_date('23/01/06','RR/MM/DD'),'UserInfoService','UserInfoService','Y','심영수','713679e89c124e3f8fca87442d9caf08');
Insert into WCADMIN.USER_INFO (USER_INFO_ID,CREATE_DATE,CREATE_USER_ID,CREATE_USER_NM,EMAIL,FIRST_NAME,IS_FROM_ERP,LAST_NAME,LOGIN,PASSWORD,SITE_CD,UPDATE_DATE,UPDATE_USER_ID,UPDATE_USER_NM,USE_YN,USER_NAME,GROUP_CODE) values ('Administrator',to_date('22/12/01','RR/MM/DD'),'PdmGroupInfoSync','PdmGroupInfoSync','nomail@fursys.com','wcadmin','N',null,null,'s3eqe6qo','SYSTEM',to_date('23/01/06','RR/MM/DD'),'PdmGroupInfoSync','PdmGroupInfoSync','Y','wcadmin','713679e89c124e3f8fca87442d9caf08');
Insert into WCADMIN.USER_INFO (USER_INFO_ID,CREATE_DATE,CREATE_USER_ID,CREATE_USER_NM,EMAIL,FIRST_NAME,IS_FROM_ERP,LAST_NAME,LOGIN,PASSWORD,SITE_CD,UPDATE_DATE,UPDATE_USER_ID,UPDATE_USER_NM,USE_YN,USER_NAME,GROUP_CODE) values ('howon_han',to_date('22/12/01','RR/MM/DD'),'PdmGroupInfoSync','PdmGroupInfoSync','nomail@fursys.com','한호원','N',null,null,'0usz15mn','SYSTEM',to_date('23/01/06','RR/MM/DD'),'PdmGroupInfoSync','PdmGroupInfoSync','Y','한호원','713679e89c124e3f8fca87442d9caf08');
Insert into WCADMIN.USER_INFO (USER_INFO_ID,CREATE_DATE,CREATE_USER_ID,CREATE_USER_NM,EMAIL,FIRST_NAME,IS_FROM_ERP,LAST_NAME,LOGIN,PASSWORD,SITE_CD,UPDATE_DATE,UPDATE_USER_ID,UPDATE_USER_NM,USE_YN,USER_NAME,GROUP_CODE) values ('junyong_jeong',to_date('22/12/01','RR/MM/DD'),'PdmGroupInfoSync','PdmGroupInfoSync','nomail@fursys.com','정준용','N',null,null,'sw9m5xxv','SYSTEM',to_date('23/01/06','RR/MM/DD'),'PdmGroupInfoSync','PdmGroupInfoSync','Y','정준용','713679e89c124e3f8fca87442d9caf08');
Insert into WCADMIN.USER_INFO (USER_INFO_ID,CREATE_DATE,CREATE_USER_ID,CREATE_USER_NM,EMAIL,FIRST_NAME,IS_FROM_ERP,LAST_NAME,LOGIN,PASSWORD,SITE_CD,UPDATE_DATE,UPDATE_USER_ID,UPDATE_USER_NM,USE_YN,USER_NAME,GROUP_CODE) values ('loopy',to_date('22/12/01','RR/MM/DD'),'PdmGroupInfoSync','PdmGroupInfoSync','nomail@fursys.com','고태진','N',null,null,'vew4x9ws','SYSTEM',to_date('23/01/06','RR/MM/DD'),'PdmGroupInfoSync','PdmGroupInfoSync','Y','고태진','713679e89c124e3f8fca87442d9caf08');
Insert into WCADMIN.USER_INFO (USER_INFO_ID,CREATE_DATE,CREATE_USER_ID,CREATE_USER_NM,EMAIL,FIRST_NAME,IS_FROM_ERP,LAST_NAME,LOGIN,PASSWORD,SITE_CD,UPDATE_DATE,UPDATE_USER_ID,UPDATE_USER_NM,USE_YN,USER_NAME,GROUP_CODE) values ('null',to_date('22/12/02','RR/MM/DD'),'wcadmin','관리자','null',null,'N',null,null,'t4t9py07','DISTRIBUTOR-2022120201',to_date('22/12/02','RR/MM/DD'),'wcadmin','관리자','Y','null',null);
Insert into WCADMIN.USER_INFO (USER_INFO_ID,CREATE_DATE,CREATE_USER_ID,CREATE_USER_NM,EMAIL,FIRST_NAME,IS_FROM_ERP,LAST_NAME,LOGIN,PASSWORD,SITE_CD,UPDATE_DATE,UPDATE_USER_ID,UPDATE_USER_NM,USE_YN,USER_NAME,GROUP_CODE) values ('extuser1@gmail.com',to_date('22/05/13','RR/MM/DD'),'admin','admin','extuser1@gmail.com','업체1사용자1','N',null,null,'1234','DIST-2022-05-00001',to_date('22/05/13','RR/MM/DD'),'admin','admin','Y','업체1사용자1',null);
Insert into WCADMIN.USER_INFO (USER_INFO_ID,CREATE_DATE,CREATE_USER_ID,CREATE_USER_NM,EMAIL,FIRST_NAME,IS_FROM_ERP,LAST_NAME,LOGIN,PASSWORD,SITE_CD,UPDATE_DATE,UPDATE_USER_ID,UPDATE_USER_NM,USE_YN,USER_NAME,GROUP_CODE) values ('admin',to_date('22/03/10','RR/MM/DD'),'admin','admin','admin@fursys.com','SYSADMIN','N',null,null,'1234','SYSTEM',to_date('22/03/27','RR/MM/DD'),'admin','admin','Y','SYSADMIN','8cbd090eb4764f4c9781cb128c73013e');
Insert into WCADMIN.USER_INFO (USER_INFO_ID,CREATE_DATE,CREATE_USER_ID,CREATE_USER_NM,EMAIL,FIRST_NAME,IS_FROM_ERP,LAST_NAME,LOGIN,PASSWORD,SITE_CD,UPDATE_DATE,UPDATE_USER_ID,UPDATE_USER_NM,USE_YN,USER_NAME,GROUP_CODE) values ('test1',to_date('22/05/02','RR/MM/DD'),'admin','admin','test1@gmail.com','test1','N',null,null,'1234','X01A1',to_date('22/05/13','RR/MM/DD'),'admin','admin','Y','test1',null);
Insert into WCADMIN.USER_INFO (USER_INFO_ID,CREATE_DATE,CREATE_USER_ID,CREATE_USER_NM,EMAIL,FIRST_NAME,IS_FROM_ERP,LAST_NAME,LOGIN,PASSWORD,SITE_CD,UPDATE_DATE,UPDATE_USER_ID,UPDATE_USER_NM,USE_YN,USER_NAME,GROUP_CODE) values ('test2',to_date('22/05/02','RR/MM/DD'),'admin','admin','test2@gmail.com','test2','N',null,null,'1234','X01C1',to_date('22/05/02','RR/MM/DD'),'admin','admin','Y','test2','e98262bebcdf44b7b6b1e42ef96c1053');
Insert into WCADMIN.USER_INFO (USER_INFO_ID,CREATE_DATE,CREATE_USER_ID,CREATE_USER_NM,EMAIL,FIRST_NAME,IS_FROM_ERP,LAST_NAME,LOGIN,PASSWORD,SITE_CD,UPDATE_DATE,UPDATE_USER_ID,UPDATE_USER_NM,USE_YN,USER_NAME,GROUP_CODE) values ('test3',to_date('22/05/02','RR/MM/DD'),'admin','admin','test3@gmail.com','test3','N',null,null,'1234','X01A1',to_date('22/05/13','RR/MM/DD'),'admin','admin','Y','test3','eaa46253c60a473fb8241f813cda163b');
Insert into WCADMIN.USER_INFO (USER_INFO_ID,CREATE_DATE,CREATE_USER_ID,CREATE_USER_NM,EMAIL,FIRST_NAME,IS_FROM_ERP,LAST_NAME,LOGIN,PASSWORD,SITE_CD,UPDATE_DATE,UPDATE_USER_ID,UPDATE_USER_NM,USE_YN,USER_NAME,GROUP_CODE) values ('hy9509@3pchain.co.kr',to_date('22/12/01','RR/MM/DD'),'wcadmin','관리자','hy9509@3pchain.co.kr',null,'N',null,null,'lfvo362d','DISTRIBUTOR-2022120101',to_date('22/12/01','RR/MM/DD'),'wcadmin','관리자','Y','distTest',null);
Insert into WCADMIN.USER_INFO (USER_INFO_ID,CREATE_DATE,CREATE_USER_ID,CREATE_USER_NM,EMAIL,FIRST_NAME,IS_FROM_ERP,LAST_NAME,LOGIN,PASSWORD,SITE_CD,UPDATE_DATE,UPDATE_USER_ID,UPDATE_USER_NM,USE_YN,USER_NAME,GROUP_CODE) values ('wcadmin',to_date('22/12/01','RR/MM/DD'),'wcadmin','관리자','wcadmin',null,'N',null,null,'5pj2q5wk','DISTRIBUTOR-2022120102',to_date('22/12/01','RR/MM/DD'),'wcadmin','관리자','Y','wcadmin',null);
Insert into WCADMIN.USER_INFO (USER_INFO_ID,CREATE_DATE,CREATE_USER_ID,CREATE_USER_NM,EMAIL,FIRST_NAME,IS_FROM_ERP,LAST_NAME,LOGIN,PASSWORD,SITE_CD,UPDATE_DATE,UPDATE_USER_ID,UPDATE_USER_NM,USE_YN,USER_NAME,GROUP_CODE) values ('send',to_date('22/12/01','RR/MM/DD'),'wcadmin','관리자','send',null,'N',null,null,'89o984x7','DISTRIBUTOR-2022120103',to_date('22/12/01','RR/MM/DD'),'wcadmin','관리자','Y','1111',null);
