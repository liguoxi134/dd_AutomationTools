<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/templates"%>
<%@taglib prefix="home" tagdir="/WEB-INF/tags/home"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>

<div class="fxs-portal-main">
	<div class="fxs-sidebar fxs-trim">
		<div class="fxs-sidebar-bar">
			<button data-bind="click:collapseOrEcllipsis" aria-expanded="true" title="隐藏菜单" class="fxs-sidebar-collapse-button fxs-has-hover" role="button">
				<icons:icon_collapse></icons:icon_collapse>
			</button>
			<ul class="fxs-sidebar-favorites">
				<!-- ko foreach: systemArray -->
				<home:system></home:system>
				<!-- /ko -->
				<!-- ko if: systemArray()==0&&loadingFlag()-->
				<li class="fxs-sidebar-item fxs-has-border"></li>
				<!-- /ko -->
				<li>
					<button data-bind="click:moreServices" title="更多服务" class="fxs-sidebar-browse fxs-has-hover fxs-menu-browse">
						<div class="fxs-sidebar-button-flex">
							<div class="fxs-sidebar-browse-label fxs-sidebar-show-if-expanded">更多服务</div>
							<div class="fxs-sidebar-browse-icon">
								<icons:icon_browse></icons:icon_browse>
							</div>
						</div>
					</button>
				</li>
			</ul>
		</div>
		<div class="fxs-sidebar-flyout fxs-popup fxs-sidebar-browse-shown fxs-sidebar-flyout-is-hidden">
			<div class="fxs-sidebar-browse-flyout">
				<div class="fxs-sidebar-flyout-header">
					<button data-bind="click:moreServices" title="关闭" class="fxs-sidebar-flyout-close fxs-fill-secondary">
						<icons:icon_arrow_right></icons:icon_arrow_right>
					</button>
				</div>
				<div class="fxs-sidebar-content">更多服务</div>
				<ul class="fxs-sidebar-possible-favorites fxs-sidebar-adjustment">
					<!-- ko foreach: serviceArray -->
					<home:service></home:service>
					<!-- /ko -->
				</ul>
			</div>
		</div>
	</div>

	<div class="fxs-portal-content fxs-panorama">
		<div class="fxs-startboard-target fxs-theme-context-start fxs-startboard fx-rightClick"></div>
		<div class="fxs-journey-target fxs-journey">
			<div class="fxs-journey-layout fxs-stacklayout-horizontal fxs-stacklayout"></div>
		</div>
	</div>
</div>
</body>
</html>
