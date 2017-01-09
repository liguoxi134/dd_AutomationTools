<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript" src="./js/framework/jquery-3.1.1.js"></script>
<script type="text/javascript" src="./js/framework/jquery.validate.js"></script>
<script type="text/javascript" src="./js/framework/knockout-3.4.0.js"></script>

<script type="text/javascript" src="./js/base/ATFDateTime.js"></script>
<script type="text/javascript" src="./js/base/blade.js"></script>
<script type="text/javascript" src="./js/base/bladeEx.js"></script>
<script type="text/javascript" src="./js/base/gs.js"></script>
<script type="text/javascript" src="./js/base/mo.js"></script>
<script type="text/javascript" src="./js/base/treeview.js"></script>

<script type="text/javascript" src="./js/site/baseViewModel.js"></script>
<script type="text/javascript" src="./js/site/sideBar.js"></script>
<script type="text/javascript" src="./js/site/meta.js"></script>
<script type="text/javascript" src="./js/site/subscribe.js"></script>
<script type="text/javascript" src="./js/site/commands.js"></script>
<script type="text/javascript" src="./js/site/itemOperations.js"></script>
<script type="text/javascript" src="./js/site/assistant.js"></script>
<script type="text/javascript" src="./js/site/init.js"></script>
<script type="text/javascript" src="./js/site/allinone.js"></script>

<script>
	var vm = new sideBarViewModel();
	vm.init();
	ko.applyBindings(vm, $(".fxs-sidebar")[0]);
	ko.applyBindings($.gs.blades, $(".fxs-topbar")[0]);

	$(".fxs-portal-content").scroll(function(e) {
		var index = Math.round(this.scrollLeft / this.clientWidth);
		$.gs.blades.selectIndex(index);
	});

	setInterval(function() {
		$.post("./admin/alert", function(data) {
			if (data) {
				$(".msg-layout").show();
				$(".msg-content").text(data);
			} else {
				$(".msg-layout").hide();
			}
		});
	}, 3 * 1000)
</script>
