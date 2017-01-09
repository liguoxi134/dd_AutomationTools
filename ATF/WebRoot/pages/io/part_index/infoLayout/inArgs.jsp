<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>
<!-- ko with: inArgs-->
<div class="tile" style="width:calc(50% - 30px);">
	<div class="tile-title">
		<span data-id="inArgs" data-bind="css:'has-hover-tip',event:{dblclick:$root.showEditLayout}">输入参数</span>
		<button data-id="inArgs" data-bind="click:$root.showEditLayout" title="编辑" class="tile-edit">
			<icons:icon_edit></icons:icon_edit>
		</button>
	</div>
	<atf:tile-item tag="div" text="请求类型" bind="text: getRequestType()"></atf:tile-item>
	<atf:tile-item tag="div" text="参数值" bind="text:requestBody"></atf:tile-item>
</div>
<!-- /ko -->