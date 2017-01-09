<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" tagdir="/WEB-INF/tags"%>
<div id="listTestCase" data-vm="tc.list">
	<!-- ko if: isLoadingData() -->
	<form:loading text="后台正在很努力的加载测试用例..."></form:loading>
	<!-- /ko -->
	<!-- ko if: !isLoadingData() -->
	<form:form_text text="搜索测试用例" bind="value: searchText, valueUpdate:'afterkeydown'" tip="根据测试用例名称，模糊搜索，不区分大小写"></form:form_text>
	<ul class="data-list">
		<!-- ko foreach: dataArray -->
		<!-- ko if: $parent.checkVisible($data) -->
		<li class="data-item">
			<div data-bind="click: $parent.itemCheck, template:{ name: 'check-icon' }" class="data-icon"></div>
			<div data-bind="click: $parent.itemClick" class="data-header">
				<div data-bind="text:($index()+1)+'. '"></div>
				<div data-bind="click: $parent.itemClick, text: name, attr:{title: name}"></div>
			</div>
			<div data-bind="click: $parent.itemClick, text: description, attr:{title: description}" class="data-content"></div>
		</li>
		<!-- /ko -->
		<!-- /ko -->
	</ul>
	<!-- /ko -->
</div>
<script>$.registVM();
</script>