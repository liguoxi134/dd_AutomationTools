<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>

<!-- ko if: editLayoutId()=="createExpectResult4Return"-->
<section data-for="editExpectResult4DB" style="color:gray">
	<!-- ko with: $root.resultVerify-->
	<div class="edit-layout-title">
		<span style="width:100%">编辑预期结果</span>
		<button title="保存" data-bind="click:returnConfig.saveResultLayout" class="edit-layout-save">
			<icons:icon_save></icons:icon_save>
		</button>
		<button title="关闭" data-bind="click:$root.closeEditLayout" class="edit-layout-close">
			<icons:icon_close></icons:icon_close>
		</button>
	</div>
	<div class="edit-layout-content">
		<!-- ko with: returnConfig -->
		<atf:tile-item tag="div" text="校验类型" bind="text:verifyType.value"></atf:tile-item>
		<atf:tile-item tag="div" text="返回值类型" bind="text:getResultType()"></atf:tile-item>
		<input style="margin-top:15px;font-size: 12px;" value="格式化参数信息" type="button" data-bind="click: analysis">
		<input style="margin-top:15px;font-size: 12px;" value="初始化预期结果" type="button" data-bind="click: send">
		<!-- /ko -->
		<!-- ko with:editConfig -->
		<atf:form_textarea text="预期值" bind="value: text, valueUpdate: 'afterkeydown'"></atf:form_textarea>
		<!-- /ko -->
	</div>
	<!-- /ko -->
</section>
<!-- /ko -->
