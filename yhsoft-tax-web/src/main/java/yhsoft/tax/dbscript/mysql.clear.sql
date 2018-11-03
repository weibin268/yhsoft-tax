delete from sys_user where status=-1;
delete from sys_role where status=-1;
delete from sys_user_role where status=-1;
delete from sys_role_permission where status=-1;
delete from sys_organization where status=-1;
delete from sys_menu where status=-1;
delete from sys_permission where status=-1;
delete from sys_dictionary WHERE status=-1;
delete from sys_dictionary_item WHERE status=-1;

delete from sys_login_log;
delete from sys_operation_log;
