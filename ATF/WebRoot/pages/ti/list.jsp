<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="form" tagdir="/WEB-INF/tags"%>
<div id="listInterface" data-vm="ti.list">
	<!-- ko if: isLoadingData() -->
	<form:loading text="后台正在很努力的加载接口..."></form:loading>
	<!-- /ko -->
	<!-- ko if: !isLoadingData() -->
	<form:form_text text="搜索测试接口" bind="value: searchText, valueUpdate:'afterkeydown'" tip="根据接口名称、接口地址进行模糊搜索，不区分大小写"></form:form_text>
	<ol class="data-list">
		<!-- ko foreach: dataArray -->
		<!-- ko if: $parent.checkVisible($data) -->
		<li class="data-item" data-bind="css:{active:id==$.gs.ti.id()}">
			<div data-bind="click: $parent.itemCheck, template:{ name: 'check-icon' }" class="data-icon"></div>
			<div data-bind="click: $parent.itemClick" class="data-header">
				<div data-bind="text:($index()+1)+'. '"></div>
				<small data-bind="text: type,attr:{class:type.toLowerCase()}"></small>
				<div data-bind="text: name, attr:{title: name}"></div>
			</div>
			<div style="font-weight: bold;" data-bind="click: $parent.itemClick, text: url, attr:{title: url}" class="data-content"></div>
			<div data-bind="click: $parent.itemClick, text: description, attr:{title: description}" class="data-content"></div>
		</li>
		<!-- /ko -->
		<!-- /ko -->
	</ol>
	<!-- /ko -->
</div>
<script>$.registVM();
</script>