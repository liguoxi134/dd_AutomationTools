
testInterfaceListViewModel.prototype.getCommands = function() {
	var vm = testInterfaceListViewModel.prototype.vm;

	function addClick() {
		// close next all blades
		vm.currentBlade().closeNextAllBlades();
		// show new add test interface blade
		var _tmpBlade = $.showBlade({
			title : "添加测试接口",
			url : "./ti/create?rid=" + vm.referenceId
		});
		//scroll new blade into viewport
		_tmpBlade.ensureElementIntoView();
	}
	function deleteClick() {
		if ($.gs.sti().length <= 0) {
			//alert for not test interface to delete
			alert("请选择要删除的测试接口！");
		}
		//confirm if delete selected test interface items or not
		else if (confirm("永久删除测试接口？") == true) {
			// close next all blades
			vm.currentBlade().closeNextAllBlades();
			var postData = {
				testSystemId : $.gs.ts.id(),
				ids : vm.packageItemIds()
			}
			$.post("./ti/delete", postData);
			//remove deleted test interface item from UI
			var item = $.gs.sti.pop();
			while (item != undefined) {
				vm.dataArray.remove(item);
				item = $.gs.sti.pop();
			}
		}
	}
	var addCommand = {
		icon : $.bladeIcons.addIcon,
		text : '添加',
		click : addClick
	};
	var deleteCommand = {
		icon : $.bladeIcons.deleteIcon,
		text : '删除',
		click : deleteClick
	}
	var refreshCommand = {
		icon : $.bladeIcons.refreshIcon,
		text : '刷新',
		click : function() {
			//refresh test interface list
			vm.refresh();
		}
	}
	return [addCommand, deleteCommand, refreshCommand];
}

testInterfaceEditViewModel.prototype.getCommands = function() {
	var vm = testInterfaceEditViewModel.prototype.vm;

	function verfiyIsEmpty(field, msg) {
		if (field == "" || field == undefined) {
			alert(msg);
			return false;
		}
		return true;
	}

	function verify() {
		if (!verfiyIsEmpty(vm.model.url(), "接口名称不能为空")) return false;
		if (!verfiyIsEmpty(vm.model.name(), "接口地址不能为空")) return false;
		if (!verfiyIsEmpty(vm.model.type(), "接口类型不能为空")) return false;
		if (!verfiyIsEmpty(vm.model.description(), "接口描述不能为空")) return false;
		return true;
	}
	function saveResponse(data) {
		vm.isSavingData(false);
		if (data) {
			alert(data.message);
			if (data.code) {
				var prev = vm.currentBlade().prev();
				$(prev).closeNextAllBlades();
				prev.refreshBlade();
				prev.ensureElementIntoView();
			}
		} else {
			alert("保存失败,失败原因请联系管理员!");
		}
	}
	var saveCommand = {
		icon : $.bladeIcons.saveIcon,
		text : "保存",
		click : function() {
			if (!verify()) return false;
			var postData = {
				"testSystemId" : $.gs.ts.id(),
				"id" : vm.id,
				"rid" : vm.referenceId,
				"name" : vm.model.name(),
				"url" : vm.model.url(),
				"type" : vm.model.type(),
				"description" : vm.model.description()
			};
			vm.isSavingData(true);
			$.post('./ti/edit', postData, saveResponse);
		}
	}
	var resetCommand = {
		icon : $.bladeIcons.refreshIcon,
		text : "重置",
		click : function() {
			vm.clearData();
		}
	}
	return [saveCommand, resetCommand];
}

testInterfaceCreateViewModel.prototype.getCommands = function() {
	var vm = testInterfaceCreateViewModel.prototype.vm;

	function verfiyIsEmpty(field, msg) {
		if (field == "" || field == undefined) {
			alert(msg);
			return false;
		}
		return true;
	}

	function verify() {
		if (!verfiyIsEmpty(vm.model.url(), "接口名称不能为空")) return false;
		if (!verfiyIsEmpty(vm.model.name(), "接口地址不能为空")) return false;
		if (!verfiyIsEmpty(vm.model.type(), "接口类型不能为空")) return false;
		if (!verfiyIsEmpty(vm.model.description(), "接口描述不能为空")) return false;
		return true;
	}
	function saveResponse(data) {
		vm.isSavingData(false);
		if (data) {
			alert(data.message);
			if (data.code) {
				var prev = vm.currentBlade().prev();
				$(prev).closeNextAllBlades();
				prev.refreshBlade();
				prev.ensureElementIntoView();
			}
		} else {
			alert("保存失败,失败原因请联系管理员!");
		}
	}
	var saveCommand = {
		icon : $.bladeIcons.saveIcon,
		text : "保存",
		click : function() {
			if (!verify()) return false;
			var postData = {
				"testSystemId" : $.gs.ts.id(),
				"rid" : vm.referenceId,
				"name" : vm.model.name(),
				"url" : vm.model.url(),
				"type" : vm.model.type(),
				"description" : vm.model.description()
			};
			vm.isSavingData(true);
			$.post('./ti/add', postData, saveResponse);

		}
	}
	var resetCommand = {
		icon : $.bladeIcons.refreshIcon,
		text : "重置",
		click : function() {
			vm.clearData();
		}
	}
	return [saveCommand, resetCommand];
}

testCaseListViewModel.prototype.getCommands = function() {
	var vm = testCaseListViewModel.prototype.vm;
	var addCommand = {
		icon : $.bladeIcons.addIcon,
		text : '添加',
		click : function() {
			vm.currentBlade().closeNextAllBlades();
			var _tmpBlade = $.showBlade({
				title : "添加测试用例",
				url : "./tc/create?rid=" + vm.referenceId
			});
			_tmpBlade.ensureElementIntoView();
		}
	};
	var cloneCommand = {
		icon : $.bladeIcons.duplicateIcon,
		text : '克隆',
		click : function() {
			if ($.gs.stc().length <= 0) {
				alert("请选择要克隆的测试用例！");
			} else {
				var ids = vm.packageItemIds();
				var postData = {
					testSystemId : $.gs.ts.id(),
					rid : vm.referenceId,
					ids : ids
				};
				$.post("./tc/copy", postData, function(data) {
					if (data) {
						if (data.code) {
							alert(data.message);
							vm.refresh();
						} else {
							alert(data.message);
						}
					} else {
						alert("如果看到此提示，请告知开发人员，谢谢");
					}
				});
			}
		}
	};
	var importCommand = {
		icon : $.bladeIcons.importIcon,
		text : '导入',
		click : function() {
			vm.currentBlade().closeNextAllBlades();
			var _tmpBlade = $.showBlade({
				title : "导入测试用例",
				url : "./tc/import?rid=" + vm.referenceId
			});
			_tmpBlade.ensureElementIntoView();
		}
	};
	var exportCommand = {
		icon : $.bladeIcons.exportIcon,
		text : '导出',
		click : function() {
			if ($.gs.stc().length <= 0) {
				alert("请选择要导出的测试用例！");
			} else {
				window.open("./tc/export?ids=" + vm.packageItemIds().join("&ids="), "_blank");
			}
		}
	}
	var deleteCommand = {
		icon : $.bladeIcons.deleteIcon,
		text : '删除',
		click : function() {
			if ($.gs.stc().length <= 0) {
				alert("请选择要删除的测试用例！");
			} else if (confirm("永久删除测试用例？") == true) {
				vm.currentBlade().closeNextAllBlades();
				var postData = {
					testSystemId : $.gs.ts.id(),
					"ids" : vm.packageItemIds()
				}
				$.post("./tc/delete", postData);
				var item = $.gs.stc.pop();
				while (item != undefined) {
					vm.dataArray.remove(item);
					item = $.gs.stc.pop();
				}
			}
		}
	};
	var refreshCommand = {
		icon : $.bladeIcons.refreshIcon,
		type : "refresh",
		text : '刷新',
		click : vm.refresh
	};
	var runCommand = {
		icon : $.bladeIcons.runIcon,
		text : '运行',
		click : function() {
			if ($.gs.stc().length > 0) {
				vm.currentBlade().closeNextAllBlades();
				var _tmpBlade = $.showBlade({
					title : "运行测试用例",
					url : "./tc/run?ids=" + vm.packageItemIds().join("&ids=") + "&rid=" + vm.referenceId
				});
				_tmpBlade.ensureElementIntoView();
			} else {
				alert("请选择需要运行的测试用例");
			}
		}
	};
	var scheduleCommand = {
		icon : $.bladeIcons.scheduleIcon,
		text : '制定运行计划',
		click : function() {
			if ($.gs.stc().length > 0) {
				vm.currentBlade().closeNextAllBlades();
				var _tmpBlade = $.showBlade({
					title : "制定运行计划",
					url : "./job/schedule?ids=" + vm.packageItemIds().join("&ids=") + "&rid=" + vm.referenceId
				});
				_tmpBlade.ensureElementIntoView();
			} else {
				alert("请选择需要定期运行的测试用例");
			}
		}
	};
	return [addCommand, cloneCommand, deleteCommand, refreshCommand, runCommand, scheduleCommand];
}

testCaseCreateViewModel.prototype.getCommands = function() {
	var vm = testCaseCreateViewModel.prototype.vm;
	function verfiyIsEmpty(field, msg) {
		if (field == "" || field == undefined) {
			alert(msg);
			return false;
		}
		return true;
	}

	function verify() {
		if (!verfiyIsEmpty(vm.model.name(), "用例名称不能为空")) return false;
		if (!verfiyIsEmpty(vm.model.description(), "description")) return false;
		return true;
	}

	var saveCommand = {
		icon : $.bladeIcons.saveIcon,
		text : "保存",
		click : function() {

			if (!verify()) return false;

			var postData = {
				testSystemId : $.gs.ts.id(),
				"rid" : vm.referenceId,
				"name" : vm.model.name(),
				"description" : vm.model.description()
			}
			vm.isSavingData(true);
			$.post("./tc/add", postData, function(data) {
				vm.isSavingData(false);
				if (data) {
					alert(data.message);
					if (data.code) {
						var prev = vm.currentBlade().prev();
						$(prev).closeNextAllBlades();
						prev.refreshBlade();
						prev.ensureElementIntoView();
					}
				} else {
					alert("保存失败,失败原因请联系管理员!");
				}

			});
		}
	};
	var resetCommand = {
		icon : $.bladeIcons.refreshIcon,
		text : "重置",
		click : vm.clearData
	};
	return [saveCommand, resetCommand];
}

testCaseEditViewModel.prototype.getCommands = function() {
	var vm = testCaseEditViewModel.prototype.vm;
	function verfiyIsEmpty(field, msg) {
		if (field == "" || field == undefined) {
			alert(msg);
			return false;
		}
		return true;
	}

	function verify() {
		if (!verfiyIsEmpty(vm.model.name(), "用例名称不能为空")) return false;
		if (!verfiyIsEmpty(vm.model.description(), "description")) return false;
		return true;
	}

	var saveCommand = {
		icon : $.bladeIcons.saveIcon,
		text : "保存",
		click : function() {

			if (!verify()) return false;

			var postData = {
				testSystemId : $.gs.ts.id(),
				"id" : vm.id,
				"rid" : vm.referenceId,
				"name" : vm.model.name(),
				"description" : vm.model.description()
			}
			vm.isSavingData(true);
			$.post("./tc/edit", postData, function(data) {
				vm.isSavingData(false);
				if (data) {
					alert(data.message);
					if (data.code) {
						var prev = vm.currentBlade().prev();
						$(prev).closeNextAllBlades();
						prev.refreshBlade();
						prev.ensureElementIntoView();
					}
				} else {
					alert("保存失败,失败原因请联系管理员!");
				}
			});
		}
	};
	var resetCommand = {
		icon : $.bladeIcons.refreshIcon,
		text : "重置",
		click : vm.clearData
	};
	return [saveCommand, resetCommand];
}

testCaseImportViewModel.prototype.getCommands = function() {
	var vm = testCaseImportViewModel.prototype.vm;
	var importCommand = {
		icon : $.bladeIcons.importIcon,
		text : "导入用例",
		click : function() {
			var fd = new FormData();
			var file = $("#upTC", blade).get(0).files[0];
			fd.append("upload-file", file);
			fd.append("rid", vm.referenceId);
			var formData = new FormData($("#postForm")[0]);
			console.log(formData);
			$.ajax({
				url : './tc/importsave',
				datatype : 'json',
				data : formData,
				type : 'post',
				contentType : false,
				processData : false,
				success : function(data, status) {
					blade.prev().refreshBlade();
					alert(data.msg);
				}
			});
		}
	};
	return [importCommand];
}

testCaseRunViewModel.prototype.getCommands = function() {
	var vm = testCaseRunViewModel.prototype.vm;
	var shExpPanelCommand = {
		icon : $.bladeIcons.leftPanelIcon,
		text : "隐藏/显示预期结果面板",
		click : function() {
			vm.visible.expectResult(!vm.visible.expectResult());
		}
	}
	var shRealPanelCommand = {
		icon : $.bladeIcons.rightPanelIcon,
		text : "隐藏/显示实际结果面板",
		click : function() {
			vm.visible.realResult(!vm.visible.realResult());
		}
	}
	return [shExpPanelCommand, shRealPanelCommand];
}
testConfigRunViewModel.prototype.getCommands = function() {
	return [];
}
testConfigDetailsViewModel.prototype.getCommands = function() {

	var vm = testConfigDetailsViewModel.prototype.vm;

	function saveClick() {
		var postData = {
			testSystemId : $.gs.ts.id(),
			rid : vm.referenceId,
			summary : JSON.stringify(vm.summary.get()),
			inArgs : JSON.stringify(vm.inArgs.get()),
			resultVerify : JSON.stringify(vm.resultVerify.get())
		}
		vm.isSavingData(true);
		$.post('./io/save', postData, function(data, status) {
			vm.isSavingData(false);
			if (data) {
				alert(data.message);
			} else {
				alert("保存失败,失败原因请联系管理员!");
			}
		})
	}
	//	function runClick() {
	//		
	//		$.gs.io.requestBody(vm.inArgs.requestBody());
	//		$.gs.io.requestHeaders(vm.summary.requestHeaders());
	//		$.gs.io.requestMethod(vm.summary.requestMethod());
	//		$.gs.io.requestType(vm.inArgs.getRequestType());
	//		$.gs.io.requestUrl(vm.summary.requestUrl());
	//		
	//		vm.currentBlade().closeNextAllBlades();
	//		var newBlade = $.showBlade({
	//			title : "参数测试",
	//			url : "./io/run",
	//		});
	//		newBlade.ensureElementIntoView();
	//	}
	var saveCommand = {
		icon : $.bladeIcons.saveIcon,
		text : "保存",
		click : saveClick
	}
	var refreshCommand = {
		icon : $.bladeIcons.refreshIcon,
		text : "刷新",
		click : vm.refresh
	}

	//	var runCommand = {
	//		icon : $.bladeIcons.runIcon,
	//		text : "执行",
	//		click : runClick
	//	}
	return [saveCommand, refreshCommand];
}
jobScheduleViewModel.prototype.getCommands = function() {
	var vm = jobScheduleViewModel.prototype.vm;

	function collectBaseInfo(postData) {
		var ids = [];
		var length = vm.testCaseArray.length;
		for (var i = 0; i < length; i++) {
			ids.push(vm.testCaseArray[i].id);
		}
		postData.testSystemId = $.gs.ts.id();
		postData.ids = ids.join(";");
		postData.rid = vm.testInterfaceId;

		postData.jobName = vm.jobName();
		postData.jobDesc = vm.schedualeText();
		postData.startTime = vm.startTime.getDate();
	}
	function collectStartTimeAndEndTime(postData) {
		if (vm.cycleExecute.selectCycleFinishType() == 'custom' && vm.startTime.getDate() >= vm.finishTime.getDate()) {
			alert("开始时间不能大于或等于结束时间");
			return;
		}
		postData.occurType = "multiple";
		postData.finishType = vm.cycleExecute.selectCycleFinishType();
		switch (vm.cycleExecute.selectCycleFinishType()) {
			case "custom" :
				postData.endTime = vm.finishTime.getDate();
				break;
			case "time" :
				postData.endTime = vm.cycleExecute.cycleCount();
				break;
			case "forever" :
				postData.endTime = "";
				break;
		}

	}
	function collectCycleType(postData) {
		postData.occurMode = vm.cycleExecute.selectCycleType();

		switch (vm.cycleExecute.selectCycleType()) {
			case "byDay" :
				if (vm.cycleExecute.dayMethod() == 0) {
					postData.during = vm.cycleExecute.during();
				} else {
					postData.allWorkingDay = true;
				}
				break;
			case "byWeek" :
				var selectedWeeks = vm.cycleExecute.selectWeek();
				postData.weeks = selectedWeeks.join(",");
				break;
			case "byMonth" :
				postData.during = vm.cycleExecute.during();
				if (vm.cycleExecute.monthMethod() == 0) {
					postData.theCount = vm.cycleExecute.theCount();
				} else {
					postData.weekOfMonth = vm.cycleExecute.selectWeekOfMonth();
					postData.weekEx = vm.cycleExecute.selectWeekEx();
				}
				break;
			case "byYear" :
				postData.during = vm.cycleExecute.during();
				postData.month = vm.cycleExecute.selectMonth();
				if (vm.cycleExecute.yearMethod() == 0) {
					postData.theCount = vm.cycleExecute.theCount();
				} else {
					postData.weekOfMonth = vm.cycleExecute.selectWeekOfMonth();
					postData.weekEx = vm.cycleExecute.selectWeekEx();
				}
				break;
		}
	}
	var runCommand = {
		icon : $.bladeIcons.importIcon,
		text : "立即运行",
		click : function() {}
	};
	var scheduleCommand = {
		icon : $.bladeIcons.importIcon,
		text : vm.schedualeText,
		click : function() {
			var postData = {};

			collectBaseInfo(postData);
			if (vm.oneTimeExecute.isActive()) {
				postData.occurType = "one";
			} else {
				collectStartTimeAndEndTime(postData);
				collectCycleType(postData);
			}
			$.post("./job/create", postData, function(data, status) {})
		}
	}
	return [runCommand, scheduleCommand];
}
databaseServerListViewModel.prototype.getCommands = function() {
	var vm = databaseServerListViewModel.prototype.vm;
	var addServerCommand = {
		icon : $.bladeIcons.addIcon,
		text : "添加",
		click : function() {
			$.gs.editItem({
				id : "",
				name : "",
				ip : "",
				uid : "",
				pwd : "",
				port : "",
				more : "useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true",
				type : ""
			});
			vm.currentBlade().closeNextAllBlades();
			var blade = $.showBlade({
				title : "添加数据库校验服务器",
				url : "./dbs/create"
			});
			blade.ensureElementIntoView();
		}
	}
//	var copyServerCommand = {
//		icon : $.bladeIcons.duplicateIcon,
//		text : "克隆",
//		click : function() {}
//	}
	var refreshServerCommand = {
		icon : $.bladeIcons.refreshIcon,
		text : "刷新",
		click : vm.refresh
	}
	return [addServerCommand,  refreshServerCommand];
}

databaseServerCreateViewModel.prototype.getCommands = function() {
	var vm = databaseServerCreateViewModel.prototype.vm;
	var saveServerCommand = {
		icon : $.bladeIcons.saveIcon,
		text : "保存",
		click : function() {
			var model = $.gs.editItem();
			$.post("./dbs/add", model, function(data) {
				alert(data.message);
				var prev = vm.currentBlade().prev();
				$(prev).closeNextAllBlades();
				prev.refreshBlade();
				prev.ensureElementIntoView();
			});
		}
	}
	var resetServerCommand = {
		icon : $.bladeIcons.refreshIcon,
		text : "重置",
		click : function() {}
	}
	return [saveServerCommand, resetServerCommand];
}

databaseServerEditViewModel.prototype.getCommands = function() {
	var vm = databaseServerEditViewModel.prototype.vm;
	var saveServerCommand = {
		icon : $.bladeIcons.saveIcon,
		text : "保存",
		click : function() {
			var model = $.gs.editItem();
			$.post("./dbs/edit", model, function(data) {
				alert(data.message);
				var prev = vm.currentBlade().prev();
				$(prev).closeNextAllBlades();
				prev.refreshBlade();
				prev.ensureElementIntoView();
			});
		}
	}
	var resetServerCommand = {
		icon : $.bladeIcons.refreshIcon,
		text : "重置",
		click : function() {
			$.gs.editItem();
		}
	}
	return [saveServerCommand, resetServerCommand];
}