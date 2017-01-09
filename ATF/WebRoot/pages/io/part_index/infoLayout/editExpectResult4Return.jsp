<%@ page language="java"  pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>

<!-- ko with: $root.resultVerify.returnConfig-->

<!-- ko foreach: config -->
<ul class="tile" style="width:100%;">
	<li class="tile-title">
		<span data-id="editExpectResult4Return" data-bind="event:{dblclick:$parent.showConfigLayout}" class="has-hover-tip">接口返回预期结果</span>
		<button title="编辑" data-id="editExpectResult4Return" data-bind="click:$parent.showConfigLayout" class="tile-edit">
			<icons:icon_edit></icons:icon_edit>
		</button>
		<button title="关闭" data-bind="click:$parent.removeConfig" class="tile-close">
			<icons:icon_close></icons:icon_close>
		</button>
	</li>
	<atf:tile-item tag="li" text="预期值" bind="text:text"></atf:tile-item>
</ul>
<!-- /ko -->

<!-- /ko -->