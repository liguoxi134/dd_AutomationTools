<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>
<!-- ko with: resultVerify.returnConfig -->
<ul class="tile" style="width:calc(100%/3 - 30px)">
	<li class="tile-title">
		<span data-id="verifyAndReturn" data-bind="css:'has-hover-tip',event:{dblclick:showEditLayout},text:verifyType.value+'配置'"></span>
		<!-- ko if:config().length<=0 -->
		<button data-id="createExpectResult4Return" title="配置接口返回预期结果" class="tile-edit" data-bind="click: showCreateLayout">
			<icons:icon_settings></icons:icon_settings>
		</button>
		<!-- /ko -->
		<button data-id="verifyAndReturn" data-bind="click:showEditLayout" title="编辑" class="tile-edit">
			<icons:icon_edit></icons:icon_edit>
		</button>
	</li>
	<atf:tile-item tag="li" text="校验类型" bind="text:verifyType.value"></atf:tile-item>
	<atf:tile-item tag="li" text="返回值类型" bind="text:getResultType()"></atf:tile-item>
</ul>
<!-- /ko -->

<!-- ko with: resultVerify.databaseConfig -->
<ul class="tile" style="width:calc(100%/3 - 30px)">
	<li class="tile-title">
		<span data-bind="text:verifyType.value"></span>
		<button data-id="createExpectResult4DB" title="添加数据库查询预期结果" data-bind="click:showCreateLayout" class="tile-edit">
			<icons:icon_add></icons:icon_add>
		</button>
	</li>
	<atf:tile-item tag="li" text="校验类型" bind="text:verifyType.value"></atf:tile-item>
	<atf:tile-item tag="li" text="返回值类型" bind="text:getResultType()"></atf:tile-item>
</ul>
<!-- /ko -->

<!-- ko with: resultVerify.logConfig -->
<ul class="tile" style="width:calc(100%/3 - 30px)">
	<li class="tile-title">
		<span data-bind="text:verifyType.value"></span>
	</li>
	<atf:tile-item tag="li" text="校验类型" bind="text:verifyType.value"></atf:tile-item>
	<atf:tile-item tag="li" text="返回值类型" bind="text:getResultType()"></atf:tile-item>
</ul>
<!-- /ko -->
