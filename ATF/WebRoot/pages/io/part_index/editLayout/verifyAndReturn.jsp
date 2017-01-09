<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>

<!-- ko if: editLayoutId()=="verifyAndReturn"-->
<!-- ko with: $root.resultVerify.editConfig -->
<section data-for="verifyAndReturn" style="color:gray">
	<div class="edit-layout-title">
		<span style="width:100%" data-bind="text: verifyType.value+'配置'"></span>
		<button title="关闭" data-bind="click:$root.closeEditLayout" class="edit-layout-close">
			<icons:icon_close></icons:icon_close>
		</button>
	</div>
	<div class="edit-layout-content">
		<atf:tile-item tag="div" text="校验类型" bind="text:verifyType.value"></atf:tile-item>
		<atf:form_select text="返回值类型" bind="options: $root.resultVerify.resultTypes, optionsText: 'value', selectedOptions: selectedResultType"></atf:form_select>
	</div>
</section>
<!-- /ko -->
<!-- /ko -->