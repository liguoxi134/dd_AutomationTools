jQuery.checkVisible = function(searchText, fields) {
	if (searchText) {
		if (fields instanceof Array) {
			for (var f = 0; f < fields.length; f++) {
				var isMatch = fields[f].toLowerCase().indexOf(searchText.toLowerCase()) > -1;
				if (isMatch) {
					return true;
				}
			}
		}
		return false;
	}
	return true;
}

jQuery.packageItemIds = function(list) {
	if (list.length <= 0)
		return false;
	var arr = [];
	var length = list.length;
	for (var i = 0; i < length; i++) {
		arr.push(list[i].id);
	}
	return arr;
}

jQuery.itemCheck = function(evt, arr, data) {
	var td = $(evt.currentTarget);
	var tr = td.parents("li");
	if (tr.hasClass("selected")) {
		arr.remove(data);
		td.find("path").attr("d", "M 23,23L 53,23L 53,53L 23,53L 23,23 Z M 28,28L 28,48L 48,48L 48,28L 28,28 Z");
	} else {
		arr.push(data);
		td.find("path").attr("d", "M 32.2209,33.4875L 39.1875,40.0582L 52.9627,24.5415L 56.2877,27.4707L 39.5834,47.5L 28.8959,36.8125L 32.2209,33.4875 Z M 22,25L 50,25L 45.5,30L 27,30L 27,49L 46,49L 46,42.5L 51,36.5L 51,54L 22,54L 22,25 Z");
	}
	tr.toggleClass("selected");
	return false;
}