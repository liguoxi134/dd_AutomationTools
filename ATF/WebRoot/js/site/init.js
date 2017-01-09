function init(vm, bladeId) {
	vm.currentBlade($(bladeId).findBlade());
	if (vm.refresh) {
		vm.refresh();
		vm.currentBlade().refresh(vm.refresh)
	}
	if(vm.subscribe){
		vm.subscribe();
	}
	vm.currentBlade().setBladeCommand(vm.getCommands())
};
testInterfaceListViewModel.prototype.init = function() {
	var vm = testInterfaceListViewModel.prototype.vm;
	init(vm, "#listInterface")
};
testInterfaceEditViewModel.prototype.init = function() {
	var vm = testInterfaceEditViewModel.prototype.vm;
	init(vm, "#editInterface")
};
testInterfaceCreateViewModel.prototype.init = function() {
	var vm = testInterfaceCreateViewModel.prototype.vm;
	init(vm, "#createInterface")
};
testCaseListViewModel.prototype.init = function() {
	var vm = testCaseListViewModel.prototype.vm;
	init(vm, "#listTestCase")
};
testCaseCreateViewModel.prototype.init = function() {
	var vm = testCaseCreateViewModel.prototype.vm;
	init(vm, "#createTestCase")
};
testCaseEditViewModel.prototype.init = function() {
	var vm = testCaseEditViewModel.prototype.vm;
	init(vm, "#editTestCase")
};
testCaseImportViewModel.prototype.init = function() {
	var vm = testCaseImportViewModel.prototype.vm;
	init(vm, "#importTestCase")
};
testCaseRunViewModel.prototype.init = function() {
	var vm = testCaseRunViewModel.prototype.vm;
	init(vm, "#runTestCase")
};
inputAndOutputViewModel.prototype.init = function() {
	var vm = inputAndOutputViewModel.prototype.vm;
	init(vm, "#ioParameter")
};
jobScheduleViewModel.prototype.init = function() {
	var vm = jobScheduleViewModel.prototype.vm;
	init(vm, "#jobSchedule")
};