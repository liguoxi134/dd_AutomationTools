<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>

<!-- ko if: editLayoutId()=="inArgs"-->
<!-- ko with: inArgs-->
<section data-for="inArgs" style="color:gray">
	<div class="edit-layout-title">
		<span style="width:100%">编辑输入参数</span>
		<button title="关闭" data-bind="click:$root.closeEditLayout" class="edit-layout-close">
			<icons:icon_close></icons:icon_close>
		</button>
	</div>
	<div class="edit-layout-content">
		<atf:form_select text="选择输入参数类型" bind="options: requestTypes,optionsText: 'value', selectedOptions:selectedRequestType"></atf:form_select>
		<input style="margin-top:15px;font-size: 12px;" type="button" value="格式化参数信息" data-bind="click: analysis" />
		<atf:form_textarea text="输入参数信息" bind="value: requestBody, valueUpdate: 'afterkeydown'"></atf:form_textarea>
	</div>
</section>
<!-- /ko -->
<!-- /ko -->