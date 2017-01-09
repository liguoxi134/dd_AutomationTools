<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="importInterface">
	<small>
		<label>
			<input type="checkbox" id="matchName" name="matchName" />
			匹配名称
		</label>
		<label style="margin-left:20px">
			<input type="checkbox" id="firstRowAsHeader" name="firstRowAsHeader" />
			第一行作为表头
		</label>
	</small>
	<div class="form-item text">
		<label>
			<small>选择测试接口文件：</small>
			<input type="file" />
		</label>
	</div>
</div>

<script>
	var vm = new testInterfaceImportViewModel();
	vm.init();
	
	var blade = $("#importInterface");
	ko.applyBindings(vm, blade[0]);
</script>
