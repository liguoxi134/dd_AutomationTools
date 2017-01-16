<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>
<div id="databaseServerList" data-vm="dbs.list">
	<!-- ko if: isLoadingData() -->
	<atf:loading text="正在加载数据库服务器配置..."></atf:loading>
	<!-- /ko -->
	<!-- ko if: !isLoadingData() -->
	<section class="tile-group">
		<!-- ko foreach: verifyDatabases -->
		<div class="tile" style="width:calc(100%/3 - 30px);">
			<div class="tile-title">
				<span data-bind="text:'校验数据库-'+($index()+1)"></span>
				<button title="编辑" data-bind="click: $parent.itemEditClick" class="tile-edit">
					<icons:icon_edit></icons:icon_edit>
				</button>
				<button title="删除" data-bind="click: $parent.itemRemoveClick" class="tile-close">
					<icons:icon_close></icons:icon_close>
				</button>
			</div>
			<atf:tile-item tag="div" text="服务器名称" bind="text:name"></atf:tile-item>
			<atf:tile-item tag="div" text="服务器类型" bind="text:type"></atf:tile-item>
			<atf:tile-item tag="div" text="服务器IP" bind="text:ip"></atf:tile-item>
			<atf:tile-item tag="div" text="接入端口" bind="text:port"></atf:tile-item>
			<atf:tile-item tag="div" text="登录用户" bind="text:uid"></atf:tile-item>
			<atf:tile-item tag="div" text="登录密码" bind="text:pwd"></atf:tile-item>
			<atf:tile-item tag="div" text="其他信息" bind="text:more"></atf:tile-item>
		</div>
		<!-- /ko -->
	</section>
	<!-- /ko -->
</div>
<script>
	$.registVM();
</script>