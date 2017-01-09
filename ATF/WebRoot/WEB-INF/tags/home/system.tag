<%@tag pageEncoding="UTF-8"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>
<li class="fxs-sidebar-item fxs-has-hover fxs-sidebar-draggable fxs-sidebar-droppable fxs-has-border">
	<div role="button" data-bind="attr:{title:name}, click:$parent.systemClick" class="fxs-sidebar-item-link">
		<div class="fxs-sidebar-handle fxs-fill-secondary">
			<icons:icon_v_more></icons:icon_v_more>
		</div>
		<div class="fxs-sidebar-icon">
			<icons:icon_system></icons:icon_system>
		</div>
		<div class="fxs-sidebar-label fxs-sidebar-show-if-expanded" data-bind="text:name"></div>
		<div class="fxs-sidebar-external fxs-sidebar-show-if-expanded"></div>
	</div>
</li>