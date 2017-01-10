jQuery.extend({
	ti : {
		create : function() {
			var vm = new testInterfaceCreateViewModel();
			vm.init();
			var blade = $("#createInterface");
			ko.applyBindings(vm, blade[0]);
		},
		edit : function() {
			var vm = new testInterfaceEditViewModel();
			vm.init();
			var blade = $("#editInterface");
			ko.applyBindings(vm, blade[0]);
		},
		list : function() {
			var vm = new testInterfaceListViewModel();
			vm.init();
			var content = $("#listInterface");
			ko.applyBindings(vm, content[0]);
		}
	},
	tc : {
		list : function() {
			var vm = new testCaseListViewModel();
			vm.init();
			var blade = $("#listTestCase");
			ko.applyBindings(vm, blade[0]);
		},
		create : function() {
			var vm = new testCaseCreateViewModel();
			vm.init();
			var blade = $("#createTestCase");
			ko.applyBindings(vm, blade[0]);
		},
		edit : function() {
			var vm = new testCaseEditViewModel();
			vm.init();
			var blade = $("#editTestCase");
			ko.applyBindings(vm, blade[0]);
		},
		run : function() {
			var vm = new testCaseRunViewModel();
			vm.init();
			var blade = $("#runTestCase");
			ko.applyBindings(vm, blade[0]);
		},
	},
	io : {
		details : function() {
			var vm = new testConfigDetailsViewModel();
			vm.init();
			var blade = $("#detailsTestConfig");
			ko.applyBindings(vm, blade[0]);
		},
		run:function(){
			var vm = new testConfigRunViewModel();
			vm.init();
			var blade = $("#runTestConfig");
			ko.applyBindings(vm, blade[0]);
		}
	},
	registVM : function() {
		$("[data-vm]:not([vm-reg])").each(function() {
			var vm = $(this).attr("data-vm");
			eval("$." + vm + "()");
			$(this).attr("vm-reg", true);
		})
	}
});