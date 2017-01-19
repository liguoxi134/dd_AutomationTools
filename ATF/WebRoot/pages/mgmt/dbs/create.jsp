<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>
<div id="databaseServerCreate" data-vm="dbs.create">
	<!-- ko if: isSavingData() -->
	<atf:loading text="正在保存数据库服务器配置..."></atf:loading>
	<!-- /ko -->
	<!-- ko if: !isSavingData() -->	
	<atf:form_text text="服务器名称" tip="给数据库校验服务器起个名" bind="value: model.name, valueUpdate: 'afterkeydown'"></atf:form_text>
	<atf:form_select text="服务器类型" tip="服务器类型只支持  MySql或者 SQLServer，请填写 : MySQL Server或者 Microsoft SQL Server" bind="options: $root.select_options, optionsText: 'option', optionsValue: 'value', value: model.type"></atf:form_select>
	<atf:form_text text="服务器IP" tip="服务器IP地址" bind="value: model.ip, valueUpdate: 'afterkeydown'"></atf:form_text>
	<atf:form_text text="接入端口" tip="数据库接入端口，如：MySql默认为：3306" bind="value: model.port, valueUpdate: 'afterkeydown'"></atf:form_text>
	<atf:form_text text="登录用户" tip="登录数据库的用户" bind="value: model.uid, valueUpdate: 'afterkeydown'"></atf:form_text>
	<atf:form_text text="登录密码" tip="登录数据库用户的密码" bind="value: model.pwd, valueUpdate: 'afterkeydown'"></atf:form_text>
	<atf:form_text text="其他信息" tip="支持中文：useUnicode=true&characterEncoding=UTF-8，支持多查询：allowMultiQueries=true" bind="value: model.more, valueUpdate: 'afterkeydown'"></atf:form_text>

	<!-- /ko -->
</div>
<script>
	$.registVM();
</script>