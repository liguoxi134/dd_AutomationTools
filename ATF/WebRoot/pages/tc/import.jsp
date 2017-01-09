<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
#importTestCase ul{
	padding: 0px;
	list-style: outside none none;
}
#importTestCase ul li {
	border: 1px solid #19507b;
	padding: 5px;
	margin: 5px 0;
	background-color: rgba(25, 80, 123,0.2);
}
</style>
<div id="importTestCase">

	<form id="postForm" action="./tc/importConfirm" method="post" enctype="multipart/form-data">
		<input type="hidden" name="rid" data-bind="value: referenceId" />
		<div class="form-item text">
			<label>
				<small>选择测试用例文件：</small>
				<input type="file" id="upTC" name="upload-file" data-bind="value: selectFile, valueUpdate:'afterkeydown'" style="display: none" />
			</label>
			<input type="text" id="upcTC" data-bind="value: selectFile, valueUpdate:'afterkeydown'" readonly placeholder="点击此处选择上传的文件" />
		</div>
	</form>
	<details data-bind="visible:!isLoadData()" style="font-size: 80%; margin-top: 20px;" open>
		<summary>导入注意事项：</summary>
		<ol style="padding: 5px 20px;">
			<li style="line-height: 1.5rem">测试用例文件必须包含两张表“测试用例”和“预期结果”</li>
			<li style="line-height: 1.5rem">每张表格都要从A1开始编写</li>
			<li style="line-height: 1.5rem">表格数据必须连续，表格中的数据最好都设置成文本</li>
			<li style="line-height: 1.5rem">测试用例表第A2单元格是存放测试用例名称，B2单元格存放测试用例描述，C1之后是参数列表名称，C2之后是输入参数列表值名称和值必须一一对应</li>
			<li style="line-height: 1.5rem">预期结果表第一行存放预期结果名称，第二行存放预期结果值</li>
		</ol>
	</details>
	<ul data-bind="visible:isLoadData()">
		<!-- ko foreach: data -->
		<li>
			<small style="color: gray; font-weight: 600">名称</small>
			<div style="margin-bottom: 10px;" data-bind="text:tcf.testCase.name"></div>
			<small style="color: gray; font-weight: 600">描述</small>
			<div style="margin-bottom: 10px;" data-bind="text:tcf.testCase.description"></div>
			<small style="color: gray; font-weight: 600">输入参数</small>
			<div style="margin-bottom: 10px;" data-bind="text:pl.testParameter.data"></div>
			<small style="color: gray; font-weight: 600">预期结果</small>
			<div style="margin-bottom: 10px;" data-bind="text:er.expectResult.data"></div>
		</li>
		<!-- /ko -->
	</ul>
</div>

<script>
	var vm = new testCaseImportViewModel();
	vm.init();

	var blade = $("#importTestCase");
	ko.applyBindings(vm, blade[0]);
	
	$("#upcTC").click(function() {
		$("#upTC").click();
		$(this).val($("#upTC").val());
		event.stopPropagation();
	});
</script>
