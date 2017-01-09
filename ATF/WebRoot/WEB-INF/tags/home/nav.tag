<%@tag pageEncoding="UTF-8"%>
<ul>
	<!-- ko foreach: data -->
	<li class="fxs-has-hover" data-bind="text:name,click:$parent.navTo, css:{'highlight':$index()==$.gs.blades.selectIndex()}"></li>
	<!-- /ko -->
</ul>