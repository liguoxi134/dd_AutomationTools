<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>

<!-- ko if: editLayoutId()=="editExpectResult4DB"-->
<section data-for="editExpectResult4DB" style="color:gray">
	<div class="edit-layout-title">
		<span style="width:100%">编辑预期结果</span>
		<button title="关闭" data-bind="click:closeEditLayout" class="edit-layout-close">
			<icons:icon_close></icons:icon_close>
		</button>
	</div>
	<!-- ko with: $root.resultVerify-->
	<div class="edit-layout-content">
		<!-- ko with:editConfig-->
		<atf:form_select text="选择服务器" bind="options: $parent.databaseConfig.verifyServers, optionsText: 'name', selectedOptions:server"></atf:form_select>
		<atf:form_text text="数据库名" bind="value: database, valueUpdate: 'afterkeydown'"></atf:form_text>
		<atf:form_textarea text="查询语句" bind="value: query, valueUpdate: 'afterkeydown'"></atf:form_textarea>
		<atf:form_textarea text="预期值" bind="value: text, valueUpdate: 'afterkeydown'"></atf:form_textarea>
		<!-- /ko -->
	</div>
	<!-- /ko -->
</section>
<!-- /ko -->