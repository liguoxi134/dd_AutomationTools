<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>

<!-- ko with: $root.resultVerify.databaseConfig-->

<!-- ko foreach: config -->
<ul class="tile" style="width:100%;">
	<li class="tile-title">
		<span class="has-hover-tip" data-id="editExpectResult4DB" data-bind="text:'数据库查询预期结果-'+($index()+1),event:{dblclick:$parent.showConfigLayout}"></span>
		<button data-id="editExpectResult4DB" data-bind="click:$parent.moveForword" title="向前移" class="tile-edit">
			<icons:icon_chevron_up></icons:icon_chevron_up>
		</button>
		<button data-id="editExpectResult4DB" data-bind="click:$parent.moveBackword" title="向后移" class="tile-edit">
			<icons:icon_chevron_down></icons:icon_chevron_down>
		</button>
		<button data-id="editExpectResult4DB" data-bind="click:$parent.showConfigLayout" title="编辑" class="tile-edit">
			<icons:icon_edit></icons:icon_edit>
		</button>
		<button title="关闭" data-bind="click:$parent.removeConfig" class="tile-close">
			<icons:icon_close></icons:icon_close>
		</button>
	</li>

	<!-- ko with:server()[0] -->
	<atf:tile-item tag="li" text="校验服务器" bind="text:type+':'+ip+':'+port"></atf:tile-item>
	<atf:tile-item tag="li" text="服务器账号" bind="text:uid+'/'+pwd"></atf:tile-item>
	<!-- /ko -->

	<atf:tile-item tag="li" text="数据库名" bind="text:database"></atf:tile-item>
	<atf:tile-item tag="li" text="查询语句" bind="text:query"></atf:tile-item>
</ul>
<!-- /ko -->
<%-- <jsp:include page="/pages/io/part_index/infoLayout/createExpectResult4DB.jsp"></jsp:include> --%>
<!-- /ko -->