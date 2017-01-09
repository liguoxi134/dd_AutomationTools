function sideBarViewModel() {
	var _self = this;
	_self.collapseOrEcllipsis = function(vm, evt) {
		$(evt.target).parents(".fxs-sidebar").toggleClass("fxs-sidebar-is-collapsed");
	};

	var moreServiceButton = undefined;
	_self.moreServices = function(vm, evt) {
		moreServiceButton = evt.target;
		var ele = $(".fxs-sidebar-flyout");
		if (ele.hasClass("fxs-sidebar-flyout-is-hidden")) {
			ele.removeClass("fxs-sidebar-flyout-is-hidden");
			ele.addClass("fxs-sidebar-browse-shown fxs-sidebar-flyout-is-open");
		} else {
			ele.addClass("fxs-sidebar-flyout-is-hidden");
			ele.removeClass("fxs-sidebar-browse-shown");
			ele.removeClass("fxs-sidebar-flyout-is-open");
		}
	};
	_self.systemArray = ko.observableArray();
	_self.loadingFlag = ko.observable(true);
	_self.serviceArray = ko.observableArray([{
		name : "管理您的用例执行计划[未开通]",
		url : ""
	}, {
		name : "查看用例执行情况[未开通]",
		url : ""
	}, {
		name : "日志查看器",
		url : "./log/index"
	}]);
	_self.systemClick = function(data, evt) {
		$.closeAllBlades();
		$.gs.ts.id(data.id);
		$.gs.ts.text(data.name);
		$.gs.sti.removeAll();
		$.showBlade({
			title : data.name + " API接口",
			url : "./ti/list?rid=" + data.id,
		});
	};
	_self.serviceClick = function(data, evt) {
		if (moreServiceButton != undefined) {
			$(moreServiceButton).click();
		}
		$.closeAllBlades();
		$.showBlade({
			title : data.name,
			url : data.url,
		});
	};
	_self.init = function() {
		$.post("./ts/list", function(data) {
			if (data) {
				if (data.code) {
					var d=$.parseJSON(data.message)
					_self.systemArray(d);
					if (!(d instanceof Array) || d.length <= 0) {
						alert("找不到任何测试系统!");
						_self.loadingFlag(false);
					}
				}else{
					alert(data.message);
				}
			}else{
				alert("如果看到此提示，请告知开发人员，谢谢");
			}
		});
	};
}