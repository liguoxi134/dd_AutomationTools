testConfigDetailsViewModel.prototype.subscribe = function() {
	var vm = testConfigDetailsViewModel.prototype.vm;
	vm.summary.selectedDefaultRequestHeaders.subscribe(function(newValue) {
		vm.summary.model.header(newValue[0]);
	});
}