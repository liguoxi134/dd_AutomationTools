<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>

<!-- ko if: editLayoutId()=="editExpectResult4Return"-->
<section data-for="editExpectResult4DB" style="color:gray">
	<div class="edit-layout-title">
		<span style="width:100%">编辑预期结果</span>
		<button title="关闭" data-bind="click:closeEditLayout" class="edit-layout-close">
			<icons:icon_close></icons:icon_close>
		</button>
	</div>
	<!-- ko with: resultVerify -->
	<div class="edit-layout-content">
		<atf:tile-item tag="div" text="校验类型" bind="text:returnConfig.verifyType.value"></atf:tile-item>
		<atf:tile-item tag="div" text="返回值类型" bind="text:returnConfig.getResultType()"></atf:tile-item>
		<input style="margin-top:15px;font-size: 12px;" value="格式化参数信息" type="button" data-bind="click: returnConfig.analysis">
		<!-- ko with:editConfig -->
		<atf:form_textarea text="预期值" bind="value: text, valueUpdate: 'afterkeydown'"></atf:form_textarea>
		<!-- /ko -->
	</div>
	<!-- /ko -->
</section>
<!-- /ko -->