<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>
<div id="databaseServerEdit" data-vm="dbs.edit">
	<!-- ko if: isSavingData() -->
	<atf:loading text="正在保存数据库服务器配置..."></atf:loading>
	<!-- /ko -->
	<!-- ko if: !isSavingData() -->
	<!-- ko with:  $.gs.editItem -->
 	<atf:form_text text="服务器名称" tip="给数据库校验服务器起个名" bind="value: name, valueUpdate: 'afterkeydown'"></atf:form_text>
	<atf:form_select text="服务器类型" tip="服务器类型只支持MySql或者SQLServer，请填写:MySQL Server或者 Microsoft SQL Server" bind="options: $root.select_options, optionsText: 'option', optionsValue: 'value', value: type"></atf:form_select>
	<atf:form_text text="服务器IP" tip="服务器IP地址" bind="value: ip, valueUpdate: 'afterkeydown'"></atf:form_text>
	<atf:form_text text="接入端口" tip="数据库接入端口，如：MySql默认为：3306" bind="value: port, valueUpdate: 'afterkeydown'"></atf:form_text>
	<atf:form_text text="登录用户" tip="登录数据库的用户" bind="value: uid, valueUpdate: 'afterkeydown'"></atf:form_text>
	<atf:form_text text="登录密码" tip="登录数据库用户的密码" bind="value: pwd, valueUpdate: 'afterkeydown'"></atf:form_text>
	<atf:form_text text="其他信息" tip="支持中文：useUnicod e=true&characterEncoding=UTF-8，支持多查询：allowMultiQueries=true" bind="value: more, valueUpdate: 'afterkeydown'"></atf:form_text>
	<!-- /ko -->
	<!-- /ko -->
</div>
<script>
	$.registVM();
</script>