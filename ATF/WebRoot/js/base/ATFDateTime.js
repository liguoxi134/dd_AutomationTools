ATFDateTime = function() {
	var _self = this;
	var theDate = new Date();
	_self.getDate = function() {
		var date = new Date(_self.select.selectYear(), _self.select.selectMonth(), _self.select.selectDate(), _self.select.selectHour(), _self.select.selectMinute(), _self.select.selectSecond());
		return date.getTime();
	};
	_self.select = {
		selectYear : ko.observable(theDate.getFullYear()),
		selectMonth : ko.observable(theDate.getMonth()),
		selectDate : ko.observable(theDate.getDate()),
		selectHour : ko.observable(theDate.getHours()),
		selectMinute : ko.observable(theDate.getMinutes()),
		selectSecond : ko.observable(theDate.getSeconds()),
	};
	_self.enums = {
		years : ko.observableArray(),
		months : ko.observableArray(),
		dates : ko.computed(function() {
			var ds = [];
			if (_self.select.selectYear() && _self.select.selectMonth()) {
				var myDate = new Date(_self.select.selectYear(), _self.select.selectMonth(), 0);
				for ( var i = 1; i <= myDate.getDate(); i++) {
					ds.push({
						id : i,
						name : i.toString().length == 1 ? "0" + i : i
					});
				}
			}
			return ds;
		}),
		hours : ko.observableArray(),
		minutes : ko.observableArray(),
		seconds : ko.observableArray(),

	};

	for ( var i = 1; i <= 12; i++) {
		_self.enums.months.push({
			id : i,
			name : i.toString().length == 1 ? "0" + i : i
		});
	}

	for ( var i = 0; i < 24; i++) {
		_self.enums.years.push({
			id : theDate.getFullYear() + i,
			name : theDate.getFullYear() + i
		});
		_self.enums.hours.push({
			id : i,
			name : i.toString().length == 1 ? "0" + i : i
		});
	}

	for ( var i = 0; i < 60; i++) {
		_self.enums.minutes.push({
			id : i,
			name : i.toString().length == 1 ? "0" + i : i
		});
		_self.enums.seconds.push({
			id : i,
			name : i.toString().length == 1 ? "0" + i : i
		});
	}
};
