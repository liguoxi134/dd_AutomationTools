
//use base view model function: packageItemIds = function(list)
testInterfaceListViewModel.prototype.packageItemIds = function() {
	return $.packageItemIds($.gs.sti());
}

//use base view model function: packageItemIds = function(list)
testCaseListViewModel.prototype.packageItemIds = function() {
	return $.packageItemIds($.gs.stc());
}

testCaseRunViewModel.prototype.packageItemIds = function() {
	return $.packageItemIds($.gs.stc());
};

//use base view model function: itemCheck = function(evt, arr, data)
testInterfaceListViewModel.prototype.itemCheck = function(data, evt) {
	$.itemCheck(evt, $.gs.sti, data);
}

testCaseListViewModel.prototype.itemCheck = function(data, evt) {
	$.itemCheck(evt, $.gs.stc, data);
}

//execute when user click "Edit" menu
testInterfaceListViewModel.prototype.itemEditClick = function(data) {
	var vm = testInterfaceListViewModel.prototype.vm;
	vm.currentBlade().closeNextAllBlades();
	//get edit item from current mouse pointer
	var _data = $.gs.editItem();
	//show new blade to allow user edit
	var _tmpBlade = $.showBlade(
		{
			title : "修改测试接口信息",
			subtitle : _data.name,
			url : "./ti/edit"
		});
	//scroll new blade into viewport
	_tmpBlade.ensureElementIntoView();
}

//execute when user click "Edit" menu
testCaseListViewModel.prototype.itemEditClick = function(data) {
	var vm = testCaseListViewModel.prototype.vm;
	vm.currentBlade().closeNextAllBlades();
	//get edit item from current mouse pointer
	var _data = $.gs.editItem();
	//show new blade to allow user edit
	var _tmpBlade = $.showBlade(
		{
			title : "修改测试用例信息",
			subtitle : _data.name,
			url : "./tc/edit"
		});
	//scroll new blade into viewport
	_tmpBlade.ensureElementIntoView();
}


//execute when user click each test interface item from list
testInterfaceListViewModel.prototype.itemClick = function(data) { 
	var vm = testInterfaceListViewModel.prototype.vm;
	//close next all blades
	vm.currentBlade().closeNextAllBlades();
	//data.data is for menu click
	var _data = data.data || data;
	//save current test interface item to gs
	$.gs.ti.id(_data.id);
	$.gs.ti.text(_data.name);
	//show new blade
	var _tmpBlade = $.showBlade({
		title : "测试用例",
		subtitle : $.gs.ti.text(),
		url : "./tc/list?rid=" + $.gs.ti.id()
	});
	//scroll new blade into viewport
	_tmpBlade.ensureElementIntoView();
}

testCaseListViewModel.prototype.itemClick = function(data) {
	var vm = testCaseListViewModel.prototype.vm;
	vm.currentBlade($("#listTestCase").findBlade());
	vm.currentBlade().closeNextAllBlades();
	var _data = data.data ? data.data : data;
	$.gs.tc.id(_data.id);
	$.gs.tc.text(_data.name);
	var _tmpBlade = $.showBlade({
		title : "输入输出参数",
		subtitle : $.gs.tc.text(),
		url : "./io/index?rid=" + $.gs.tc.id() + "&srid=" + $.gs.ti.id()
	});
	_tmpBlade.ensureElementIntoView();
}

testCaseRunViewModel.prototype.itemClick = function(data, obj) {
	var vm = testCaseRunViewModel.prototype.vm;
	//_navExpectResultItem
	if (vm.visible.expectResult()) {
		var nodeName = vm.getTokenFieldName(data) || data.node;
		var group = "[data-group='" + data.group + "']";
		var value = "[data-value='" + (data.node == null ? "null" : data.node) + "']";
		var node = "[data-node='" + nodeName + "']";
		var ernode = $(".er " + group + node + value);
		if (ernode.length > 0) {
			ernode.parents(".node").removeClass("collapsed");
			ernode.click();
			ernode.get(0).scrollIntoView(true);
		} else {
			alert(ernode.length)
		}
	}
	//_navRealResultItem
	if (vm.visible.realResult()) {
		if (data.state != "Miss") {
			var nodeName = vm.getTokenFieldName(data)||data.realNode;
			var group = "[data-group='" + data.realGroup + "']";
			var value = "[data-value='" + (data.realNode == null ? "null" : data.realNode) + "']";
			var node = "[data-node='" + nodeName + "']";
			var rrnode = $(".rr " + group + node + value);
			if (rrnode.length > 0) {
				rrnode.parents(".node").removeClass("collapsed");
				rrnode.click();
				rrnode.get(0).scrollIntoView(true);
			}
		} else {
			$(".rr .active").removeClass("active");
		}
	}
}

//execute when refresh test interface list
testInterfaceListViewModel.prototype.refresh = function() {
	var vm = testInterfaceListViewModel.prototype.vm;
	vm.isLoadingData(true);
	function refreshResponse(data) {
		if (data) {
			if (data.code) {
				var d = $.parseJSON(data.message);
				vm.dataArray(d);
			} else {
				alert(data.message);
			}
		} else {
			alert("如果看到此提示，请告知开发人员，谢谢");
		}
		vm.isLoadingData(false);
	}

	//reset search text
	vm.searchText("");
	//reset selected test interface items
	$.gs.sti.removeAll();
	var postData = {
		testSystemId : $.gs.ts.id(),
		"rid" : vm.referenceId
	};
	//get latest test interface list with post
	$.post("./ti/refresh", postData, refreshResponse);
}

testCaseListViewModel.prototype.refresh = function() {
	var vm = testCaseListViewModel.prototype.vm;
	vm.isLoadingData(true);
	function refreshResponse(data) {
		if (data) {
			if (data.code) {
				var d = $.parseJSON(data.message);
				vm.dataArray(d);
			} else {
				alert(data.message);
			}
		} else {
			alert("如果看到此提示，请告知开发人员，谢谢");
		}
		vm.isLoadingData(false);
	}
	//reset search text
	vm.searchText("");
	//reset selected test interface items
	$.gs.stc.removeAll();
	var postData = {
		testSystemId : $.gs.ts.id(),
		"rid" : vm.referenceId
	};

	$.post("./tc/refresh", postData, refreshResponse);
}

testCaseRunViewModel.prototype.refresh = function() {
	var vm = testCaseRunViewModel.prototype.vm;
	var postData = {
		testSystemId : $.gs.ts.id(),
		ids : vm.packageItemIds
	};
	vm.isLoadingData(true);
	function executeResponse(data) {
		if (data) {
			if (data.code) {
				var d = $.parseJSON(data.message);
				vm.executeArray(d);
			} else {
				alert(data.message);
			}
		} else {
			alert("如果看到此提示，请告知开发人员，谢谢");
		}
		vm.isLoadingData(false);
	}

	$.post("./tc/execute", postData, executeResponse);
}

testConfigDetailsViewModel.prototype.refresh = function() {
	var vm = testConfigDetailsViewModel.prototype.vm;
	vm.isLoadingData(true);
	function refreshResponse(data) {
		if (data) {
			if (data.code) {
				vm.summary.set({
					summary : {
						headers : JSON.parse(data.headers),
						requestUrl : data.requestUrl,
						requestMethod : data.requestMethod,
					}
				});
				vm.inArgs.set({
					inArgs : {
						requestType : data.requestType,
						requestBody : data.requestBody
					}
				});
				vm.resultVerify.set({
					resultVerify : JSON.parse(data.resultVerify),
					verifyDbs : data.verifyDbs
				});
			} else {
				alert(data.message);
			}
		} else {
			alert("如果看到此提示，请告知开发人员，谢谢");
		}
		vm.isLoadingData(false);
	}
	var postData = {
		testSystemId : $.gs.ts.id(),
		"rid" : vm.referenceId,
	};
	$.post("./io/refresh", postData, refreshResponse);
};

//use base view model function: function(searchText, fields)
testInterfaceListViewModel.prototype.checkVisible = function(data) {
	return $.checkVisible(this.searchText(), [data.url, data.name]);
}

testCaseListViewModel.prototype.checkVisible = function(data) {
	return $.checkVisible(this.searchText(), [data.name]);
}

testCaseRunViewModel.prototype.checkVisible = function(data) {
	var vm = testCaseRunViewModel.prototype.vm;
	if (data.state == "Success" && vm.visible.successItem()) {
		return true;
	}
	if (data.state == "Fail" && vm.visible.failItem()) {
		return true;
	}
	if (data.state == "Miss" && vm.visible.missItem()) {
		return true;
	}
	return false;
};