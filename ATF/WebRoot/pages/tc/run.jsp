<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div id="runTestCase" data-vm="tc.run">
	<!-- ko if: isLoadingData() -->
	<div style="font-size: 13px;">正在加载数据...</div>
	<!-- /ko -->
	<!-- ko if: !isLoadingData() -->
	<div class="run-summary">
		<div style="width: 100%;display: flex;align-items: center;">
			<label style="padding: 0 10px; white-space: nowrap;">
				<small>选择用例</small>
			</label>
			<select style="width:100%;" data-bind="options: executeArray,selectedOptions:selectedExecuteResult,optionsText:function(item){return item.testcase.name;}"></select>
		</div>
		<label>
			<input type="checkbox" data-bind="checked: visible.successItem" />
			<small>显示匹配成功的字段</small>
		</label>
		<label>
			<input type="checkbox" data-bind="checked: visible.failItem" />
			<small>显示匹配失败的字段</small>
		</label>
		<label>
			<input type="checkbox" data-bind="checked: visible.missItem" />
			<small>显示匹配缺失的字段</small>
		</label>
	</div>
	<div class="run-details">
		<div style="width: 20%; margin-right: 10px;" class="run" data-bind="visible:visible.expectResult()">
			<div class="run-header">预期结果</div>
			<div class="er run-content"></div>
		</div>
		<div style="width: 100%;" class="run">
			<div class="run-header">
				<span>比较结果</span>
				<small class="all">匹配总数：</small>
				<small class="succ">成功：</small>
				<small class="fail">失败：</small>
				<small class="miss">缺失：</small>
			</div>
			<div class="run-content">
				<table class="run-c-table">
					<thead>
						<tr>
							<th>字段</th>
							<th>预期值</th>
							<th>真实值</th>
							<th style="width: 21px;text-align: center;">#</th>
						</tr>
					</thead>
					<tbody>
						<!-- ko foreach: selectedCompare -->
						<!-- ko if: $parent.checkVisible($data)-->
						<tr data-bind="attr:{class:'c-row '+state.toLowerCase()},click:$parent.itemClick">
							<td class="token" data-bind="text:$parent.getTokenFieldName($data)"></td>
							<td class="expect" data-bind="text:$parent.getNodeValue($data)"></td>
							<td class="real" data-bind="text:$parent.getRealNodeValue($data)"></td>
							<td class="result" style="width: 40px;text-align: center;">
								<div></div>
							</td>
						</tr>
						<!-- /ko -->
						<!-- /ko -->
					</tbody>
				</table>
			</div>
		</div>
		<div style="width: 20%; margin-left: 10px;" class="run" data-bind="visible:visible.realResult()">
			<div class="run-header">实际结果</div>
			<!-- 树形列表 -->
			<div class="rr run-content"></div>
		</div>
	</div>
	<!-- /ko -->
</div><script>$.registVM();</script>