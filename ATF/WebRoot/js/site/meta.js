
testInterfaceListViewModel = function() {
	var vm = this;
	//test system id
	vm.referenceId = $.gs.ts.id();
	//to save all test interface
	vm.dataArray = ko.observableArray();
	//to save search text from user
	vm.searchText = ko.observable();
	vm.isLoadingData = ko.observable(false);
	testInterfaceListViewModel.prototype.vm = vm;
	//to save current balde
	testInterfaceListViewModel.prototype.currentBlade = ko.observable();

}

testInterfaceEditViewModel = function() {
	var vm = this;
	var editItem = $.gs.editItem();
	vm.isSavingData = ko.observable(false);
	vm.referenceId = $.gs.ts.id();
	vm.id = editItem.id;
	vm.typeArray = ko.observableArray(["GET", "POST"]);
	vm.model = {
		url : ko.observable(editItem.url),
		name : ko.observable(editItem.name),
		type : ko.observable(editItem.type),
		description : ko.observable(editItem.description),
	};
	vm.clearData = function() {
		vm.model.url("");
		vm.model.name("");
		vm.model.type("POST");
		vm.model.description("");
	};

	testInterfaceEditViewModel.prototype.vm = vm;
	//to save current balde
	testInterfaceEditViewModel.prototype.currentBlade = ko.observable();

}

testInterfaceCreateViewModel = function() {
	var vm = this;
	vm.isSavingData = ko.observable(false);
	vm.referenceId = $.gs.ts.id();
	vm.typeArray = ko.observableArray(["GET", "POST"]);
	vm.model = {
		url : ko.observable(),
		name : ko.observable(),
		type : ko.observable("GET"),
		description : ko.observable(),
	};
	vm.clearData = function() {
		vm.model.url("");
		vm.model.name("");
		vm.model.type("POST");
		vm.model.description("");
	};

	testInterfaceCreateViewModel.prototype.vm = vm;
	//to save current balde
	testInterfaceCreateViewModel.prototype.currentBlade = ko.observable();
}

testCaseListViewModel = function() {
	var vm = this;
	vm.referenceId = $.gs.ti.id();
	vm.isLoadingData = ko.observable(false);
	vm.dataArray = ko.observableArray();
	vm.searchText = ko.observable();

	testCaseListViewModel.prototype.vm = vm;
	//to save current balde
	testCaseListViewModel.prototype.currentBlade = ko.observable();
}

testCaseCreateViewModel = function() {
	var vm = this;
	vm.isSavingData = ko.observable(false);
	vm.referenceId = $.gs.ti.id();
	vm.typeArray = ko.observableArray(["GET", "POST"]);
	vm.model = {
		name : ko.observable(),
		description : ko.observable(),
	};
	vm.clearData = function() {
		vm.model.name("");
		vm.model.description("");
	};

	testCaseCreateViewModel.prototype.vm = vm;
	//to save current balde
	testCaseCreateViewModel.prototype.currentBlade = ko.observable();
}


testCaseEditViewModel = function() {
	var vm = this;
	var editItem = $.gs.editItem();
	vm.isSavingData = ko.observable(false);
	vm.id = editItem.id;
	vm.referenceId = $.gs.ti.id();
	vm.typeArray = ko.observableArray(["GET", "POST"]);
	vm.model = {
		name : ko.observable(editItem.name),
		description : ko.observable(editItem.description),
	};
	vm.clearData = function() {
		vm.model.name("");
		vm.model.description("");
	};

	testCaseEditViewModel.prototype.vm = vm;
	//to save current balde
	testCaseEditViewModel.prototype.currentBlade = ko.observable();
}

testCaseImportViewModel = function() {
	var vm = this;
	vm.referenceId = ko.observable($.gs.ti.id());
	vm.selectFile = ko.observable();
	vm.data = ko.observableArray();
	vm.isLoadData = ko.observable(false);

	testCaseImportViewModel.prototype.vm = vm;
	//to save current balde
	testCaseImportViewModel.prototype.currentBlade = ko.observable();
}

testCaseRunViewModel = function() {

	var vm = this;
	vm.executeArray = ko.observableArray();
	vm.isLoadingData = ko.observable(false);
	vm.selectedExecuteItem = ko.observableArray();
	vm.selectedVerifyType = ko.observableArray();
	vm.selectedExecuteResult = ko.observableArray();

	//packageItems(["returnConfig", "databaseConfig", "logConfig"], ["接口返回结果", "数据库查询结果", "Log记录文件"])
	vm.verifyTypes = ko.computed(function() {
		vm.selectedExecuteResult([]);
		var _selectedExecuteItem = (vm.selectedExecuteItem() != undefined && vm.selectedExecuteItem().length == 1) ? vm.selectedExecuteItem()[0] : undefined;
		if (_selectedExecuteItem == undefined) {
			return [];
		} else {
			var data = [];
			if (_selectedExecuteItem.returnConfig && _selectedExecuteItem.returnConfig.length > 0) {
				data.push({
					id : "returnConfig",
					value : "接口返回结果"
				});
			}
			if (_selectedExecuteItem.databaseConfig && _selectedExecuteItem.databaseConfig.length > 0) {
				data.push({
					id : "databaseConfig",
					value : "数据库查询结果"
				});
			}
			if (_selectedExecuteItem.logConfig && _selectedExecuteItem.logConfig.length > 0) {
				data.push({
					id : "logConfig",
					value : "Log记录文件"
				});
			}
			return data;
		}
	});

	vm.selectedExecuteResults = ko.computed(function() {
		vm.selectedExecuteResult([]);
		var _selectedExecuteItem = (vm.selectedExecuteItem() != undefined && vm.selectedExecuteItem().length == 1) ? vm.selectedExecuteItem()[0] : undefined;
		var _selectedVerifyType = vm.selectedVerifyType() != undefined && vm.selectedVerifyType().length == 1 ? vm.selectedVerifyType()[0] : undefined;
		if (_selectedExecuteItem == undefined || _selectedVerifyType == undefined) {
			return [];
		} else {
			return eval("_selectedExecuteItem." + _selectedVerifyType.id);
		}
	});

	vm.visible = {
		expectResult : ko.observable(true),
		realResult : ko.observable(true),
		compareResult : ko.observable(true),
		successItem : ko.observable(false),
		failItem : ko.observable(true),
		missItem : ko.observable(true),
	};
	vm.executeCaseIds = $.gs.stc();

	vm.selectedCompare = ko.computed(function() {
		var er = vm.selectedExecuteResult();
		if (er && er.length == 1) {
			buildJsonTree(er[0]);
			var cdata = $.parseJSON(er[0].compare);
			calcSummaryInfo(cdata);
			return cdata;
		} else {
			return [];
		}
	});

	function packageItems(ids, names) {
		var items = [];
		var length = Math.min(ids.length, names.length);
		for (var plIdx = 0; plIdx < length; plIdx++) {
			items.push({
				id : ids[plIdx],
				value : names[plIdx]
			});
		}
		return items;
	}
	function buildJsonTree(executeResult) {
		if (executeResult.source == "") {
			executeResult.source = "{}";
		}
		if (executeResult.target == "") {
			executeResult.target = "{}";
		}
		$(".er").treeview($.parseJSON(executeResult.source));
		$(".rr").treeview($.parseJSON(executeResult.target));
	}
	function calcSummaryInfo(cdata) {
		var all = cdata.length,
			succ = 0,
			fail = 0,
			miss = 0;
		for (var i = 0; i < all; i++) {
			var d = cdata[i];
			if (d.state === "Success") {
				succ++;
				continue;
			}
			if (d.state === "Fail") {
				fail++;
				continue;
			}
			if (d.state === "Miss") {
				miss++;
				continue;
			}
		}
		$(".all").html("匹配总数：" + all);
		$(".succ").html("成功：" + succ);
		$(".fail").html("失败：" + fail);
		$(".miss").html("缺失：" + miss);
	}
	testCaseRunViewModel.prototype.vm = vm;
	//to save current balde
	testCaseRunViewModel.prototype.currentBlade = ko.observable();
}

testConfigRunViewModel = function() {
	var vm = this;
	vm.isLoadingData = ko.observable(false);
	vm.responseData = ko.observable();
	var postData = {
		requestBody : $.gs.io.requestBody(),
		requestHeaders : $.gs.io.requestHeaders(),
		requestMethod : $.gs.io.requestMethod(),
		requestUrl : $.gs.io.requestUrl(),
	};
	vm.isLoadingData(true);
	$.post("./io/run", postData, function(data) {
		if (data) {
			vm.responseData(data.message);
		} else {
			alert("如果看到此提示，请告知开发人员，谢谢");
		}
		vm.isLoadingData(false)
	});

	testConfigRunViewModel.prototype.vm = vm;
	//to save current balde
	testConfigRunViewModel.prototype.currentBlade = ko.observable();
}
testConfigDetailsViewModel = function() {
	var vm = this;
	vm.isLoadingData = ko.observable(false);
	vm.isSavingData = ko.observable(false);

	vm.referenceId = $.gs.tc.id();
	vm.parentReferenceId = $.gs.ti.id();
	vm.summary = {
		requestMethod : ko.observable(),
		requestCase : ko.observable($.gs.tc.text()),
		requestInterface : ko.observable($.gs.ti.text()),
		requestUrl : ko.observable(),
		requestHeaders : ko.observableArray(),
		defaultRequestHeaders : ko.observableArray(["content-type=application/json;charset=UTF-8", "content-type=application/x-www-form-urlencoded;charset=UTF-8"]),
		selectedDefaultRequestHeaders : ko.observableArray(),

		model : {
			header : ko.observable()
		},

		get : function() {
			var header = [],
				data = this.requestHeaders();
			if (data) {
				for (var i = 0; i < data.length; i++) {
					header.push(data[i]);
				}
			}
			return {
				headers : header
			};
		},
		set : function(cnf) {
			if (cnf) {
				if (cnf.summary.headers != null) {
					this.requestHeaders(cnf.summary.headers);
				}
				this.requestUrl(cnf.summary.requestUrl);
				this.requestMethod(cnf.summary.requestMethod);
			}
		},
		addRequestHeader : function() {
			var add = this.model.header();
			if (add != "") {
				var addidx = add.indexOf("=");
				if (addidx == -1) {
					alert("请求头不符合规范：如content-type=application/json;charset=UTF-8！");
					return false;
				}
				var addname = add.substring(0, addidx).toLowerCase();
				var rhs = this.requestHeaders();
				for (var i = 0; i < rhs.length; i++) {
					var idx = rhs[i].indexOf("=");
					var name = rhs[i].substring(0, idx).toLowerCase();
					if (addname == name) {
						alert("重复的请求头");
						return false;
					}
				}
				this.requestHeaders.push(add);
				this.model.header("");
			}
		},
		removeRequestHeader : function(data, ele) {
			vm.summary.requestHeaders.remove(data);
		},
	};

	vm.inArgs = {
		requestBody : ko.observable(),
		requestTypes : ko.observableArray(packageItems(["XML", "JSON", "TEXT"], ["XML格式[未开通]", "JSON格式", "Url附加参数格式"])),
		selectedRequestType : ko.observableArray(),
		findRequestType : function(name) {
			var sws = name;
			var ws = this.requestTypes();
			for (var w = 0; w < ws.length; w++) {
				if (ws[w].id == sws) {
					return ws[w];
				}
			}
			return "";
		},
		analysis : function() {
			var type = this.selectedRequestType()[0].id,
				text = this.requestBody;
			if (!text()) return;
			var postData = {
				"type" : type,
				"text" : text()
			};
			$.post('./io/format', postData, function(data, status) {
				if (data) {
					if (data.code) {
						text(data.message);
					} else {
						alert(data.message);
					}
				} else {
					alert("如果看到此提示，请告知开发人员，谢谢");
				}
			});
		},
		getRequestType : function() {
			if (vm.isLoadingData()) {
				return '正在加载数据...';
			}
			if (this.selectedRequestType() != undefined && this.selectedRequestType().length > 0) {
				return this.selectedRequestType()[0].value;
			}
			return "未设置请求类型。";
		},
		get : function() {
			var d = {};
			if (this.selectedRequestType() != undefined && this.selectedRequestType().length > 0) {
				d.requestType = this.selectedRequestType()[0].id;
			} else {
				d.requestType = null;
			}
			d.requestBody = this.requestBody();
			return d;
		},
		set : function(cnf) {
			if (cnf) {
				var vt = this.findRequestType(cnf.inArgs.requestType);
				this.selectedRequestType([vt]);
				this.requestBody(cnf.inArgs.requestBody);
			}
		}
	};
	vm.resultVerify = {
		editConfig : ko.observable(),
		resultTypes : ko.observableArray(packageItems(["JSON", "XML", "TEXT"], ["JSON格式文本", "XML格式文本[未开通]", "其他类型文本[未开通]"])),
		findResultType : function(id) {
			var length = this.resultTypes().length;
			for (var i = 0; i < length; i++) {
				if (this.resultTypes()[i].id == id) {
					return this.resultTypes()[i];
				}
			}
			return undefined;
		},
		returnConfig : {
			isConfig : false,
			verifyType : {
				id : "RETURN",
				value : "接口返回结果校验"
			},
			selectedResultType : ko.observableArray(),
			getResultType : function() {
				if (this.selectedResultType() == undefined || this.selectedResultType().length <= 0) {
					return "未设置";
				}
				return this.selectedResultType()[0].value;
			},

			send : function() {
				var postData = {
					requestBody : vm.inArgs.requestBody(),
					requestHeaders : vm.summary.requestHeaders(),
					requestMethod : vm.summary.requestMethod(),
					requestUrl : vm.summary.requestUrl(),
				};
				$.post("./io/send", postData, function(data) {
					if (data) {
						vm.resultVerify.editConfig().text(data.message);
					} else {
						alert("如果看到此提示，请告知开发人员，谢谢");
					}
				});
			},
			model : function() {
				this.text = ko.observable();
				this.get = function() {
					return {
						text : this.text()
					}
				}
			},
			config : ko.observableArray(),
			showEditLayout : function(data, e) {
				vm.resultVerify.editConfig(this);
				vm.showEditLayout(data, e);
			},
			showConfigLayout : function(data, e) {
				vm.resultVerify.editConfig(data);
				vm.showEditLayout(data, e);
			},
			showCreateLayout : function(data, e) {
				var _data = new data.model();
				_data.text("");
				vm.resultVerify.editConfig(_data);
				vm.showEditLayout(_data, e);
			},
			saveResultLayout : function() {
				this.returnConfig.config.push(this.editConfig());
				vm.closeEditLayout();
			},
			analysis : function() {
				var type = (this.returnConfig || this).selectedResultType()[0].id,
					text = (this.editConfig || vm.resultVerify.editConfig)().text;
				if (!text()) return;
				var postData = {
					"type" : type,
					"text" : text()
				};
				$.post('./io/format', postData, function(data, status) {
					if (data) {
						if (data.code) {
							text(data.message);
						} else {
							alert(data.message);
						}
					} else {
						alert("如果看到此提示，请告知开发人员，谢谢");
					}
				});
			},
			removeConfig : function(data, e) {
				//this===data
				if (confirm("清除返回值预期结果？")) {
					vm.resultVerify.returnConfig.config.remove(data);
				}
			},
			get : function() {
				var d = {};
				if (this.config() != undefined && this.config().length > 0) {
					d.returnResult = [this.config()[0].get()];
					if (this.selectedResultType() != undefined && this.selectedResultType().length > 0) {
						d.returnType = this.selectedResultType()[0].id;
					}
				}
				return d;
			},
			set : function(cnf) {
				this.config([]);
				this.selectedResultType([]);
				if (cnf) {
					//Set default Return Type
					this.selectedResultType.removeAll();
					this.selectedResultType.push(vm.resultVerify.resultTypes()[0]);

					if (cnf.resultVerify && cnf.resultVerify.returnConfig && cnf.resultVerify.returnConfig.returnResult) {
						var result = cnf.resultVerify.returnConfig.returnResult;
						if (result && result.length >= 1) {
							var mdl = new this.model();
							mdl.text(result[0].text);
							this.config([mdl]);
						}
						this.selectedResultType([vm.resultVerify.findResultType(cnf.resultVerify.returnConfig.returnType)]);
					}
				}
			},
		},
		databaseConfig : {
			isConfig : true,
			verifyType : {
				id : "DATABASE",
				value : "数据库查询结果校验"
			},
			verifyServers : ko.observableArray(),
			findVerifyServer : function(id) {
				var length = this.verifyServers().length;
				for (var i = 0; i < length; i++) {
					if (this.verifyServers()[i].id == id) {
						return this.verifyServers()[i];
					}
				}
				return undefined;
			},
			selectedResultType : ko.observableArray(),
			getResultType : function() {
				if (this.selectedResultType() == undefined || this.selectedResultType().length <= 0) {
					return "未设置";
				}
				return this.selectedResultType()[0].value;
			},
			model : function() {
				this.server = ko.observableArray();
				this.database = ko.observable();
				this.query = ko.observable();
				this.text = ko.observable();
				this.get = function() {
					return {
						server : this.server()[0].id,
						database : this.database(),
						query : this.query(),
						text : this.text()
					}
				}
			},
			config : ko.observableArray(),
			query : function() {
				var postData = {
					server : JSON.stringify(this.server()[0]),
					query : this.query(),
					database : this.database(),
				};
				$.post("./io/query", postData, function(data) {
					if (data) {
						vm.resultVerify.editConfig().text(data.message);
					} else {
						alert("如果看到此提示，请告知开发人员，谢谢");
					}
				});
			},
			showConfigLayout : function(data, e) {
				vm.resultVerify.editConfig(data);
				vm.showEditLayout(data, e);
			},
			showCreateLayout : function(data, e) {
				var model = new this.model();
				model.database("");
				model.server([this.verifyServers()[0]]);
				model.query("");
				model.text("");
				vm.resultVerify.editConfig(model);
				vm.showEditLayout(data, e);
			},
			saveResultLayout : function() {
				var model = this.editConfig();
				this.databaseConfig.config.push(model);
				vm.closeEditLayout();
			},
			removeConfig : function(data, e) {
				//this===data
				if (confirm("删除此数据库查询预期结果？")) {
					vm.resultVerify.databaseConfig.config.remove(data);
				}
			},
			moveForword : function(data, e) {
				//this===data
				var arr = vm.resultVerify.databaseConfig.config();
				var index = arr.indexOf(data);
				if (index == 0) return false;
				var item = arr[index];
				arr[index] = arr.splice(index - 1, 1, item)[0];
				vm.resultVerify.databaseConfig.config(arr);
			},
			moveBackword : function(data, e) {
				//this===data
				var arr = vm.resultVerify.databaseConfig.config();
				var index = arr.indexOf(data);
				if (index == arr.length - 1) return false;
				var item = arr[index];
				arr[index] = arr.splice(index + 1, 1, item)[0];
				vm.resultVerify.databaseConfig.config(arr);
			},
			get : function() {
				var d = {};
				if (this.config() != undefined && this.config().length > 0) {
					var a = [];
					for (var i = 0; i < this.config().length; i++) {
						a.push(this.config()[i].get());
					}
					d.returnResult = a;
					if (this.selectedResultType() != undefined && this.selectedResultType().length > 0) {
						d.returnType = this.selectedResultType()[0].id;
					}
				}
				return d;
			},
			set : function(cnf) {
				this.verifyServers([]);
				this.config([]);
				this.selectedResultType([]);
				if (cnf) {
					//Set default Return Type
					this.selectedResultType.removeAll();
					this.selectedResultType.push(vm.resultVerify.resultTypes()[0]);

					if (cnf.verifyDbs) {
						this.verifyServers(cnf.verifyDbs);
					}

					if (cnf.resultVerify && cnf.resultVerify.databaseConfig && cnf.resultVerify.databaseConfig.returnResult) {
						var result = cnf.resultVerify.databaseConfig.returnResult,
							length = result ? result.length : 0,
							vdbs = [];
						for (var i = 0; i < length; i++) {
							var vdb = new this.model();
							var s = this.findVerifyServer(result[i].server);
							if (s != undefined) {
								vdb.server([s]);
							}
							vdb.database(result[i].database);
							vdb.query(result[i].query);
							vdb.text(result[i].text);
							vdbs.push(vdb);
						}
						this.config(vdbs);
						this.selectedResultType([vm.resultVerify.findResultType(cnf.resultVerify.databaseConfig.returnType)]);
					}
				}
			},
		},
		logConfig : {
			isConfig : undefined,
			verifyType : {
				id : "LOG",
				value : "Log记录文件校验[未开通]",
			},
			selectedResultType : ko.observableArray(),
			getResultType : function() {
				return "未开通";
			},
			showEditLayout : function(data, e) {
				alert("未开通");
			},
			get : function() {
				return {};
			},
			set : function(cnf) {
				//Set default Return Type
				this.selectedResultType.removeAll();
				this.selectedResultType.push(vm.resultVerify.resultTypes()[2]);
			},
		},
		set : function(cnf) {
			if (cnf) {
				this.returnConfig.set(cnf);
				this.databaseConfig.set(cnf);
				this.logConfig.set(cnf);
			}
		},
		get : function() {
			return {
				returnConfig : this.returnConfig.get(),
				databaseConfig : this.databaseConfig.get(),
				logConfig : this.logConfig.get()
			}
		},
	};

	function packageItems(ids, names) {
		var items = [];
		var length = Math.min(ids.length, names.length);
		for (var plIdx = 0; plIdx < length; plIdx++) {
			items.push({
				id : ids[plIdx],
				value : names[plIdx]
			});
		}
		return items;
	}
	vm.editLayoutId = ko.observable("");
	vm.showEditLayout = function(data, e) {
		vm.editLayoutId($(e.currentTarget).attr("data-id"));
	}
	vm.closeEditLayout = function() {
		vm.editLayoutId("");
	}
	testConfigDetailsViewModel.prototype.vm = vm;
	//to save current balde
	testConfigDetailsViewModel.prototype.currentBlade = ko.observable();
}

jobScheduleViewModel = function() {

	var vm = this;
	var theDate = new Date();
	vm.testCaseArray = $.gs.stc();
	vm.testInterfaceId = $.gs.ti.id();
	vm.testCaseShowOrHide = function(vm, e) {
		$('[data-id="schedule-case-list"]').slideToggle();
	};

	vm.jobName = ko.observable();

	vm.startTime = new ATFDateTime();
	vm.finishTime = new ATFDateTime();

	vm.oneTimeExecute = {
		isActive : ko.observable(true),
	};
	vm.cycleExecute = {
		isActive : ko.observable(false),

		selectCycleType : ko.observable("byDay"),
		selectCycleFinishType : ko.observable("custom"),
		selectWeek : ko.observableArray([theDate.getDay()]),
		selectWeekEx : ko.observable(theDate.getDay()),
		selectWeekOfMonth : ko.observable(theDate.getWeekOfMonth(0)),
		selectMonth : ko.observable(theDate.getMonth()),

		dayMethod : ko.observable("0"),
		monthMethod : ko.observable("0"),
		yearMethod : ko.observable("0"),

		cycleTypes : ko.observableArray(),
		cycleFinishType : ko.observableArray(),
		weeks : ko.observableArray(),
		weekEx : ko.observableArray(),
		weeksOfMonth : ko.observableArray(),
		months : ko.observableArray(),

		during : ko.observable(1),
		theCount : ko.observable(theDate.getDate()),
		cycleCount : ko.observable(1),

		typeSelector : function(data, obj) {
			vm.cycleExecute.selectCycleType(data.id);
		}
	};
	vm.cycleExecute.cycleCount.subscribe(function(newValue) {
		if (isNaN(newValue)) {
			alert("重复次数只能是数字");
			vm.cycleExecute.cycleCount(1);
			return true;
		} else if (parseInt(newValue) <= 0) {
			alert("重复次数必须大于等于一次");
			vm.cycleExecute.cycleCount(1);
			return true;
		}
	});
	vm.cycleExecute.during.subscribe(function(newValue) {
		if (isNaN(newValue)) {
			alert("时间间隔只能是数字");
			vm.cycleExecute.during(1);
			return true;
		} else if (parseInt(newValue) <= 0) {
			alert("时间间隔必须大于等于一次");
			vm.cycleExecute.during(1);
			return true;
		}
	});
	vm.cycleExecute.theCount.subscribe(function(newValue) {
		if (isNaN(newValue)) {
			alert("只能是数字");
			vm.cycleExecute.theCount(1);
			return true;
		} else if (parseInt(newValue) <= 0) {
			alert("必须大于等于1");
			vm.cycleExecute.theCount(1);
			return true;
		}
	});

	vm.changeTab = function(data, evt) {
		if ($(evt.currentTarget).hasClass("active")) {
			return false;
		} else {
			vm.oneTimeExecute.isActive(!vm.oneTimeExecute.isActive());
			vm.cycleExecute.isActive(!vm.cycleExecute.isActive());
		}
	};

	var monthCHSArray = ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"];
	for (var i = 1; i <= 12; i++) {
		vm.cycleExecute.months.push({
			id : i,
			name : monthCHSArray[i - 1],
		});
	}

	var cycleTypeENUArray = ["byDay", "byWeek", "byMonth", "byYear"];
	var cycleTypeCHSArray = ["按天", "按周", "按月", "按年"];
	for (var i = 0; i < 4; i++) {
		vm.cycleExecute.cycleTypes.push({
			id : cycleTypeENUArray[i],
			name : cycleTypeCHSArray[i]
		});
	}

	var cycleFinishTypeENUArray = ["forever", "time", "custom"];
	var cycleFinishTypeCHSArray = ["无结束日期", "设置重复次数", "自定义结束日期"];
	for (var i = 0; i < 3; i++) {
		vm.cycleExecute.cycleFinishType.push({
			id : cycleFinishTypeENUArray[i],
			name : cycleFinishTypeCHSArray[i]
		});
	}

	var weeksCHSArray = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
	for (var i = 1; i <= 7; i++) {
		vm.cycleExecute.weeks.push({
			id : i,
			name : weeksCHSArray[i - 1],
		});
	}

	var weekExENUArray = ['SUN-SAT', 'MON-FRI', '1', 'SAT,SUN', '2', '3', '4', '5', '6', '7'];
	var weekExCHSArray = ["日子", "工作日", "星期日", "周末", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
	for (var i = 0; i < 10; i++) {
		vm.cycleExecute.weekEx.push({
			id : weekExENUArray[i],
			name : weekExCHSArray[i]
		});
	}

	var weeksOfMonthENUArray = ['1', '2', '3', '4', 'L'];
	var weeksOfMonthCHSArray = ["第一个", "第二个", "第三个", "第四个", "最后一个"];
	for (var i = 0; i < 5; i++) {
		vm.cycleExecute.weeksOfMonth.push({
			id : weeksOfMonthENUArray[i],
			name : weeksOfMonthCHSArray[i]
		});
	}
	vm.findWeeks = function() {
		var r = [];
		var sws = vm.cycleExecute.selectWeek();
		var ws = vm.cycleExecute.weeks();
		for (var w = 0; w < ws.length; w++) {
			for (var sw = 0; sw < sws.length; sw++) {
				if (ws[w].id == sws[sw]) {
					r.push(ws[w].name);
				}
			}
		}
		return r.join("、");
	};
	vm.findWeeksOfMonth = function() {
		var sws = vm.cycleExecute.selectWeekOfMonth();
		var ws = vm.cycleExecute.weeksOfMonth();
		for (var w = 0; w < ws.length; w++) {
			if (ws[w].id == sws) {
				return ws[w].name;
			}
		}
		return "未知";
	};
	vm.findWeekEx = function() {
		var sws = vm.cycleExecute.selectWeekEx();
		var ws = vm.cycleExecute.weekEx();
		for (var w = 0; w < ws.length; w++) {
			if (ws[w].id == sws) {
				return ws[w].name;
			}
		}
		return "未知";
	};
	vm.findMonth = function() {
		var sws = vm.cycleExecute.selectMonth();
		var ws = vm.cycleExecute.months();
		for (var w = 0; w < ws.length; w++) {
			if (ws[w].id == sws) {
				return ws[w].name;
			}
		}
		return "未知";
	};
	vm.schedualeText = ko.computed(function() {
		var desc = "";
		var ce = vm.cycleExecute;
		if (vm.oneTimeExecute.isActive()) {
			desc += "只运行一次";
			return desc;
		} else {
			desc += "重复运行";
		}
		if (ce.selectCycleType() == "byDay") {
			if (ce.dayMethod() == "0") {
				desc += "，规则为：每隔 " + ce.during() + " 天运行一次";
			} else {
				desc += "，规则为：每个工作日运行一次";
			}
		} else if (ce.selectCycleType() == "byWeek") {
			desc += "，规则为：每隔 1 周的 " + vm.findWeeks() + " 各运行一次";
		} else if (ce.selectCycleType() == "byMonth") {
			if (ce.monthMethod() == "0") {
				desc += "，规则为：每隔 " + ce.during() + " 个月的第 " + ce.theCount() + " 天运行一次";
			} else {
				desc += "，规则为：每隔 " + ce.during() + " 个月的 " + vm.findWeeksOfMonth() + vm.findWeekEx() + " 运行一次";
			}
		} else if (ce.selectCycleType() == "byYear") {
			if (ce.yearMethod() == "0") {
				desc += "，规则为：每隔 " + ce.during() + " 年的 " + vm.findMonth() + " 的第 " + ce.theCount() + " 日运行一次";
			} else {
				desc += "，规则为：每隔 " + ce.during() + " 年的 " + vm.findMonth() + " 的 " + vm.findWeeksOfMonth() + vm.findWeekEx() + " 运行一次";
			}
		}
		return desc;
	});

	jobScheduleViewModel.prototype.vm = vm;
	//to save current balde
	jobScheduleViewModel.prototype.currentBlade = ko.observable();
}

databaseServerListViewModel = function() {
	var vm = this;

	vm.isLoadingData = ko.observable(true);

	vm.verifyDatabases = ko.observableArray();

	databaseServerListViewModel.prototype.vm = vm;
	databaseServerListViewModel.prototype.currentBlade = ko.observable();
}

databaseServerCreateViewModel = function() {
	var vm = this;
	
	vm.select_options = ko.observableArray([{option: "MySQL Server", value: "mysql"},{option: "Microsoft SQL Server", value: "sqlserver"}]);

	//var editItem = $.gs.editItem();
	
	vm.isSavingData = ko.observable(false);
	vm.typeArray = ko.observableArray(["Mysql", "MSSQL"]);
/*	vm.model = {
			name : ko.observable(editItem.name),
			type: ko.observable(editItem.type),
			ip: ko.observable(editItem.ip),
			port: ko.observable(editItem.port),
			uid: ko.observable(editItem.uid),
			pwd: ko.observable(editItem.pwd),
			more: ko.observable(editItem.mo),
		};*/
	vm.model = {
		name : ko.observable(),
		type: ko.observable("mysql"),
		ip: ko.observable(),
		port: ko.observable("3306"),
		uid: ko.observable(),
		pwd: ko.observable(),
		more: ko.observable(),
	};
	vm.clearData = function() {
		vm.model.name("");
		vm.model.type("mysql");
		vm.model.ip("");
		vm.model.port("");
		vm.model.uid("");
		vm.model.pwd("");
		vm.model.more("");
	};

	databaseServerCreateViewModel.prototype.vm = vm;
	databaseServerCreateViewModel.prototype.currentBlade = ko.observable();
}

databaseServerEditViewModel = function() {
	var vm = this;
	
	vm.select_options = ko.observableArray([{option: "MySQL Server", value: "mysql"},{option: "Microsoft SQL Server", value: "sqlserver"}]);

	var editItem = $.gs.editItem();
/*	vm.full_type = ko.computed(function(){
		var ret_option = "";
			ko.utils.arrayForEach(vm.select_options, function(item){
				if(item.value == editItem.type){
					ret_option = item.option;
				}
			});
			retrun ret_option;
		});*/

	vm.isSavingData = ko.observable(false);
	vm.id = editItem.id;
	vm.model = {
		name : ko.observable(editItem.name),
		type: ko.observable(editItem.type),
		ip: ko.observable(editItem.ip),
		port: ko.observable(editItem.port),
		uid: ko.observable(editItem.uid),
		pwd: ko.observable(editItem.pwd),
		more: ko.observable(editItem.mo),
	};
	vm.clearData = function() {
		vm.model.name("");
		vm.model.type("mysql");
		vm.model.ip("");
		vm.model.port("");
		vm.model.uid("");
		vm.model.pwd("");
		vm.model.more("");
	};

	databaseServerEditViewModel.prototype.vm = vm;
	databaseServerEditViewModel.prototype.currentBlade = ko.observable();
}