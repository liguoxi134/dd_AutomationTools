jQuery.extend({
	gs : {
		ts : {
			id : ko.observable(),
			text : ko.observable()
		},
		ti : {
			id : ko.observable(),
			text : ko.observable()
		},
		tc : {
			id : ko.observable(),
			text : ko.observable()
		},
		sti : ko.observableArray(),
		stc : ko.observableArray(),
		blades : {
			selectIndex : ko.observable(0),
			data : ko.observableArray(),
			navTo : function(data, evt) {
				$(data.blade).ensureElementIntoView();
			}
		},
		editItem : ko.observable(),
		io:{
			requestUrl: ko.observable(),
			requestMethod: ko.observable(),
			requestHeaders: ko.observableArray(),
			requestType: ko.observable(),
			requestBody: ko.observable(),
			verifyType: ko.observable(),
		},
	}
});
Date.prototype.getWeekOfMonth = function(weekStart) {
	weekStart = (weekStart || 0) - 0;
	if (isNaN(weekStart) || weekStart > 6)
		weekStart = 0;

	var dayOfWeek = this.getDay();
	var day = this.getDate();
	return Math.ceil((day - dayOfWeek - 1) / 7) + ((dayOfWeek >= weekStart) ? 1 : 0);
};

jQuery.registerRunResult = function() {
	var target = $(".c-result");
	var label = $("<label/>");
	var checkBox = $("<input type=\"checkbox\">");
	var text = $("<span/>").html("隐藏匹配成功的字段");
	label.append(checkBox).append(text);

	checkBox.change(function() {
		if (checkBox.is(':checked')) {
			target.find(".exact").parents("li").hide();
			text.html("显示匹配成功的字段");
		} else {
			target.find(".exact").parents("li").show();
			text.html("隐藏匹配成功的字段");
		}
	});
	target.prepend(label);

	$(".result-header", target).click(function() {
		$(this).next("ul").toggle();
	});
};
