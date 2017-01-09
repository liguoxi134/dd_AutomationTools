<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<div id="editTestCase" data-vm="tc.edit">
	<!-- ko if:isSavingData() -->
	<atf:loading text="正在更新测试用例信息..."></atf:loading>
	<!-- /ko -->
	<!-- ko if:!isSavingData() -->
	<atf:form_text text="测试用例名称" bind="value: model.name, valueUpdate:'afterkeydown'" tip="给测试用例起一个简短的名称，不能超过255个字符，一个汉字算2个字符"></atf:form_text>
	<atf:form_textarea text="测试用例描述" bind="value: model.description, valueUpdate:'afterkeydown'" tip="描述一下这个用例是测什么的"></atf:form_textarea>
	<!-- /ko -->
</div>
<script>$.registVM();
</script>