
testCaseRunViewModel.prototype.getTokenFieldName = function(data) {
	var l1 = data.token.lastIndexOf("*");
	var l2 = data.token.lastIndexOf("$");
	var fromIndex = l1;
	if (l1 < l2) {
		fromIndex = l2;
	}
	return data.token.substr(fromIndex + 1);
};
testCaseRunViewModel.prototype.getNodeValue = function(data) {
	if (data.type === "NULL") {
		return "null";
	} else if (data.type === "STRING") {
		return "\"" + data.node + "\"";
	} else {
		return data.node;
	}
};
testCaseRunViewModel.prototype.getRealNodeValue = function(data) {
	if (data.realType) {
		if (data.realType === "NULL") {
			return "null";
		} else if (data.realType === "STRING") {
			return "\"" + data.realNode + "\"";
		} else {
			return data.realNode;
		}
	} else {
		return "-"
	}
};