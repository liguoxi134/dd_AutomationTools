<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>
<!-- ko with: resultVerify.returnConfig -->
<!-- ko if:config().length<=0 -->
<div class="tile" style="width:calc(100%/3 - 30px);align-self: center;">
	<div title="添加预期结果" data-id="createExpectResult4Return" data-bind="click:showCreateLayout" style="font-size: 12px;display: flex;height: 100%;color: gray;outline: 1px solid rgba(0,0,0,0.3);align-items: stretch;cursor: pointer;">
		<button class="edit-layout-create">
			<icons:icon_settings></icons:icon_settings>
		</button>
		<span style="width: 100%;font-size: 1.25rem;padding-left: 10px;align-self: center;">配置返回值预期结果</span>
	</div>
</div>
<!-- /ko -->
<!-- /ko -->