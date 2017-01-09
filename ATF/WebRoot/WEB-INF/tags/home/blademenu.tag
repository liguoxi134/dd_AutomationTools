<%@tag pageEncoding="UTF-8"%>
<div tabindex="0" class="fxs-commands-contextMenu fxs-contextMenu fxs-popup">
	<ul class="fxs-contextMenu-itemList">
		<!-- ko foreach: menuItems -->
		<li role="menuitem" class="fxs-contextMenu-item fxs-has-hover" tabindex="0" data-bind="click:click">
			<div class="fxs-contextMenu-text fxs-text-default msportalfx-text-ellipsis" data-bind="text:text"></div>
			<div class="fxs-contextMenu-icon" data-bind="html:icon"></div>
		</li>
		<!-- /ko -->
	</ul>
</div>