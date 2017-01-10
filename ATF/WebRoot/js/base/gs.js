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
