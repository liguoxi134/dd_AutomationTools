<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags" %>
<div id="ioParameter">
	<!-- ko if: isSavingData() -->
	<atf:loading text="正在保存输入输出配置信息..."></atf:loading>
	<!-- /ko -->
	<!-- ko if: isLoadingData() -->
	<atf:loading text="正在收集输入输出配置信息..."></atf:loading>
	<!-- /ko -->
	<!-- ko if: !isLoadingData()&&!isSavingData() -->
	<div style="border: 1px solid rgba(0, 0, 0, 0.3); padding: 5px; margin-bottom: 10px; background-color: rgba(0, 0, 0, 0.1);">
		<div style="display: flex;white-space: nowrap;">
			<small style="border: 1px solid; padding: 2px 5px; border-radius: 3px; color: green; background-color: rgba(0, 255, 0, 0.3);" data-bind="text: testInterfaceType"></small>
			<span data-bind="text:$.gs.tc.text(),attr:{title:$.gs.tc.text()}" style="padding-left: 5px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;"></span>
		</div>
		<div data-bind="text:$.gs.ti.text()+'——'+testInterfaceURL(),attr:{title:testInterfaceURL}"></div>
	</div>
	<div class="tab-header" style="display: flex;">
		<div class="tab-item" data-bind="css:{active: tabPL.isActive()},click: changeTab">输入参数</div>
		<svg width="76" height="76" viewBox="0 0 76.00 76.00" enable-background="new 0 0 76.00 76.00" style="fill: rgba(0, 0, 0, 0.3);">
			<path fill="#000000" fill-opacity="1" stroke-width="0.2" stroke-linejoin="round" d="M 54,52.0001L 29.25,52.0001L 37.25,60L 26.75,60L 14.75,48.0001L 26.75,36L 37.25,36L 29.25,44.0001L 54,44.0001L 54,52.0001 Z M 22,23.9999L 46.75,23.9999L 38.75,16L 49.25,16L 61.25,27.9999L 49.25,40L 38.75,40L 46.75,31.9999L 22,31.9999L 22,23.9999 Z " />
		</svg>
		<div class="tab-item" data-bind="css:{active: tabER.isActive()},click: changeTab">预期结果</div>
	</div>
	<div class="tab-content">
		<div class="tab-item" data-bind="with:tabPL,visible: tabPL.isActive()">
			<div class="form-item select">
				<label>
					<small>选择输入参数类型</small>
					<select data-bind="options: typeArray,optionsText: 'value',optionsValue: 'id', value:selectedType"></select>
				</label>
			</div>
			<div class="form-item textarea">
				<small>
					<input type="button" value="格式化" data-bind="click: analysis" />
				</small>
				<label>
					<small>输入参数信息</small>
					<textarea style="white-space: pre" rows="10" cols="" data-bind="value: text, valueUpdate: 'afterkeydown'"></textarea>
				</label>
			</div>
		</div>
		<div class="tab-item" data-bind="with: tabER,visible: tabER.isActive()">
			<div class="form-item select">
				<label>
					<small>预期结果校验类型</small>
					<select data-bind="options: verifyArray, optionsText: 'value',optionsValue: 'id', value:selectedVerify"></select>
				</label>
			</div>
			<div data-bind="visible:['DATABASE'].indexOf(selectedVerify())>=0">
				<div class="form-item text">
					<label>
						<small>IP地址</small>
						<input type="text" data-bind="value: dbaddress, valueUpdate:'afterkeydown'" />
					</label>
				</div>
				<div class="form-item text">
					<label>
						<small>数据库名</small>
						<input type="text" data-bind="value: dbname, valueUpdate:'afterkeydown'" />
					</label>
				</div>
				<div class="form-item text">
					<label>
						<small>用户名</small>
						<input type="text" data-bind="value: dbuser, valueUpdate:'afterkeydown'" />
					</label>
				</div>
				<div class="form-item text">
					<label>
						<small>密码</small>
						<input type="password" data-bind="value: dbpwd, valueUpdate:'afterkeydown'" />
					</label>
				</div>
				<div class="form-item textarea">
					<label>
						<small>查询语句</small>
						<textarea rows=10 data-bind="value: query, valueUpdate:'afterkeydown'"></textarea>
					</label>
				</div>
			</div>
			<div class="form-item select">
				<label>
					<small>预期结果类型</small>
					<select data-bind="options: typeArray, optionsText: 'value',optionsValue: 'id', value:selectedType"></select>
				</label>
			</div>
			<div class="form-item textarea">
				<small>
					<input type="button" value="格式化" data-bind="click: analysis" />
				</small>
				<label>
					<small>预期结果信息</small>
					<textarea style="white-space: pre" rows="10" cols="" data-bind="value: text, valueUpdate:'afterkeydown'"></textarea>
				</label>
			</div>
		</div>
	</div>
	<!-- /ko -->
</div>
<script>
	var vm = new inputAndOutputViewModel();
	vm.init();

	var blade = $("#ioParameter");
	ko.applyBindings(vm, blade[0]);
</script>