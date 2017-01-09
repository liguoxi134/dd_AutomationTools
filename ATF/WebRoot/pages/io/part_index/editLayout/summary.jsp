<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>
<!-- ko if: editLayoutId()=="summary"-->
<!-- ko with: summary-->
<section data-for="summary" style="color:gray">
	<div class="edit-layout-title">
		<span style="width:100%">编辑概要信息</span>
		<button title="关闭" data-bind="click:$root.closeEditLayout" class="edit-layout-close">
			<icons:icon_close></icons:icon_close>
		</button>
	</div>
	<div class="edit-layout-content">
		<atf:tile-item tag="div" text="请求方式" bind="text: requestMethod"></atf:tile-item>
		<atf:tile-item tag="div" text="测试用例" bind="text: requestCase"></atf:tile-item>
		<atf:tile-item tag="div" text="接口名称" bind="text: requestInterface"></atf:tile-item>
		<atf:tile-item tag="div" text="接口地址" bind="text: requestUrl"></atf:tile-item>
		<!-- ko foreach: requestHeaders -->
		<div class="tile-item" style="color:green">
			<span>请求头</span>
			<span class="editable" data-bind="text:$data"></span>
			<span class="edit-del" data-bind="click: $parent.removeRequestHeader">删除</span>
		</div>
		<!-- /ko -->
	</div>
	<div class="edit-layout-footer">
		<atf:form_select text="选择要添加的请求头" bind="options:defaultRequestHeaders,selectedOptions:selectedDefaultRequestHeaders"></atf:form_select>
		<atf:form_text text="或者自定义请求头" bind="value: model.header, valueUpdate: 'afterkeydown'"></atf:form_text>
		<input style="margin-top:15px;font-size: 80%;" type="button" value="确认添加" data-bind="click: addRequestHeader" />
	</div>
</section>
<!-- /ko -->
<!-- /ko -->