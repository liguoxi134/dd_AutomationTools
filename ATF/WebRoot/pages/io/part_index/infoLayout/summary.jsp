<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>
<!-- ko with: summary-->
<ul class="tile" style="width:calc(50% - 30px);">
	<li class="tile-title">
		<span data-id="summary" data-bind="css:'has-hover-tip',event:{dblclick:$root.showEditLayout}">概要信息</span>
		<button data-id="summary" data-bind="click:$root.showEditLayout" title="编辑" class="tile-edit">
			<icons:icon_edit></icons:icon_edit>
		</button>
	</li>
	<atf:tile-item tag="li" text="请求方式" bind="text: requestMethod"></atf:tile-item>
	<atf:tile-item tag="li" text="测试用例" bind="text: requestCase"></atf:tile-item>
	<atf:tile-item tag="li" text="接口名称" bind="text: requestInterface"></atf:tile-item>
	<atf:tile-item tag="li" text="接口地址" bind="text: requestUrl"></atf:tile-item>

	<!-- ko foreach: requestHeaders -->
	<atf:tile-item tag="li" text="请求头" bind="text: $data"></atf:tile-item>
	<!-- /ko -->
</ul>
<!-- /ko -->