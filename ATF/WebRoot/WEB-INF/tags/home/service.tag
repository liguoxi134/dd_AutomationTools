<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>
<li tabindex="-1" class="fxs-sidebar-item fxs-has-hover">
	<div class="fxs-sidebar-item-content">
		<div role="button" data-bind="click:$parent.serviceClick" tabindex="-1" class="fxs-sidebar-item-link">
			<div class="fxs-sidebar-icon">
				<icons:icon_service></icons:icon_service>
			</div>
			<div class="fxs-sidebar-label">
				<span data-bind="attr:{title:name},text:name"></span>
			</div>
		</div>
	</div>
</li>