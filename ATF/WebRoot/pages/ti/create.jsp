<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<div id="createInterface" data-vm="ti.create">
	<!-- ko if:isSavingData() -->
	<atf:loading text="正在保存测试接口信息..."></atf:loading>
	<!-- /ko -->
	<!-- ko if:!isSavingData() -->
	<atf:form_text text="名称" bind="value: model.name, valueUpdate:'afterkeydown'" tip="给测试接口起一个简短的名称，不能超过255个字符，一个汉字算2个字符"></atf:form_text>
	<atf:form_text text="URL" bind="value: model.url, valueUpdate:'afterkeydown'" tip="测试接口的URL地址，以'http://'、'https://'开头，不需要加入参数部分，即：只需要'?'前面的URL（不含'?'）即可，请不要超过2048字符"></atf:form_text>
	<atf:form_select text="类型" bind="options: typeArray, value: model.type, optionsCaption: ''" tip="测试接口类型，包括GET，POST，默认为GET"></atf:form_select>
	<atf:form_textarea text="描述" bind="value: model.description, valueUpdate:'afterkeydown'" tip="给测试的接口做一个整体的描述，如果不填写，则默认与名称一样"></atf:form_textarea>
	<!-- /ko -->
</div>
<script>$.registVM();
</script>