<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="jobSchedule">
	<div class="form-item text" style="margin-bottom: 20px;">
		<label>
			<small style="font-size: 120%;" data-bind="click:testCaseShowOrHide">
				<span>本次计划执行的用例</span>
				<span style="font-size:65%;color:gray;">[ 点击可显示/隐藏下面的用例 ]</span>
			</small>
		</label>
		<ul data-id="schedule-case-list">
			<!-- ko foreach: testCaseArray -->
			<li>
				<div style="font-weight: 600" data-bind="text:'用例名称 —— ' + name"></div>
				<small style="color: gray;" data-bind="text:'用例描述 —— ' + description"></small>
			</li>
			<!-- /ko -->
		</ul>
	</div>
	<div class="form-item text" style="margin-bottom: 20px;">
		<label>
			<small style="font-size: 120%;">任务名称：</small>
			<input type="text" data-bind="value: jobName, valueUpdate:'afterkeydown'" />
		</label>
	</div>
	<div class="form-item text" style="margin-bottom: 20px;">
		<label>
			<small style="font-size: 120%;">周期设定：</small>
		</label>
	</div>
	<div class="tab-header" style="display: flex;">
		<div class="tab-item" data-bind="css:{active: oneTimeExecute.isActive()},click: changeTab">一次运行</div>
		<svg width="76" height="76" viewBox="0 0 76.00 76.00" enable-background="new 0 0 76.00 76.00" style="fill: rgba(0, 0, 0, 0.3);">
            <path fill="#000000" fill-opacity="1" stroke-width="0.2" stroke-linejoin="round" d="M 54,52.0001L 29.25,52.0001L 37.25,60L 26.75,60L 14.75,48.0001L 26.75,36L 37.25,36L 29.25,44.0001L 54,44.0001L 54,52.0001 Z M 22,23.9999L 46.75,23.9999L 38.75,16L 49.25,16L 61.25,27.9999L 49.25,40L 38.75,40L 46.75,31.9999L 22,31.9999L 22,23.9999 Z " />
        </svg>
		<div class="tab-item" data-bind="css:{active: cycleExecute.isActive()},click: changeTab">周期运行</div>
	</div>
	<div class="tab-content">
		<div class="tab-item">
			<div style="margin: 20px 0 10px 0; font-size: 80%; font-weight: 600">开始时间设定为:</div>
			<div class="flex-group">
				<div class="value-group">
					<div class="value-container">
						<select data-bind="options: startTime.enums.years, optionsText: 'name',optionsValue: 'id',value:startTime.select.selectYear"></select>
						<small>年</small>
					</div>
					<div class="value-container">
						<select data-bind="options: startTime.enums.months, optionsText: 'name',optionsValue: 'id',value:startTime.select.selectMonth"></select>
						<small>月</small>
					</div>
					<div class="value-container">
						<select data-bind="options: startTime.enums.dates, optionsText: 'name',optionsValue: 'id',value:startTime.select.selectDate"></select>
						<small>日</small>
					</div>
				</div>
				<div class="value-group" style="margin-left: 20px">
					<div class="value-container">
						<select data-bind="options: startTime.enums.hours, optionsText: 'name',optionsValue: 'id',value:startTime.select.selectHour"></select>
						<small>时</small>
					</div>
					<div class="value-container">
						<select data-bind="options: startTime.enums.minutes, optionsText: 'name',optionsValue: 'id',value:startTime.select.selectMinute"></select>
						<small>分</small>
					</div>
					<div class="value-container">
						<select data-bind="options: startTime.enums.seconds, optionsText: 'name',optionsValue: 'id',value:startTime.select.selectSecond"></select>
						<small>秒</small>
					</div>
				</div>
			</div>
		</div>
		<div class="tab-item" data-bind="visible: cycleExecute.isActive()">
			<div style="margin: 20px 0 10px 0; font-size: 80%; font-weight: 600">结束时间设定为:</div>
			<select data-bind="options: cycleExecute.cycleFinishType, optionsText: 'name',optionsValue: 'id',value:cycleExecute.selectCycleFinishType"></select>
			<div class="flex-group" data-bind="visible:cycleExecute.selectCycleFinishType()=='custom'">
				<div class="value-group">
					<div class="value-container">
						<select data-bind="options: finishTime.enums.years, optionsText: 'name',optionsValue: 'id',value:finishTime.select.selectYear"></select>
						<small>年</small>
					</div>
					<div class="value-container">
						<select data-bind="options: finishTime.enums.months, optionsText: 'name',optionsValue: 'id',value:finishTime.select.selectMonth"></select>
						<small>月</small>
					</div>
					<div class="value-container">
						<select data-bind="options: finishTime.enums.dates, optionsText: 'name',optionsValue: 'id',value:finishTime.select.selectDate"></select>
						<small>日</small>
					</div>
				</div>
				<div class="value-group" style="margin-left: 20px">
					<div class="value-container">
						<select data-bind="options: finishTime.enums.hours, optionsText: 'name',optionsValue: 'id',value:finishTime.select.selectHour"></select>
						<small>时</small>
					</div>
					<div class="value-container">
						<select data-bind="options: finishTime.enums.minutes, optionsText: 'name',optionsValue: 'id',value:finishTime.select.selectMinute"></select>
						<small>分</small>
					</div>
					<div class="value-container">
						<select data-bind="options: finishTime.enums.seconds, optionsText: 'name',optionsValue: 'id',value:finishTime.select.selectSecond"></select>
						<small>秒</small>
					</div>
				</div>
			</div>
			<div class="flex-group" data-bind="visible:cycleExecute.selectCycleFinishType()=='time'">
				<label>
					<span>设定重复次数为</span>
					<input type="text" data-bind="value:cycleExecute.cycleCount,valueUpdate:'afterkeydown'" />
					<span>次</span>
				</label>
			</div>
		</div>
		<div class="tab-item" data-bind="visible: cycleExecute.isActive(),with:cycleExecute">
			<div style="margin: 20px 0 10px 0; font-size: 80%; font-weight: 600">此计划将会在指定周期运行，运行周期设定为:</div>
			<div class="flex-group">
				<div style="width: 120px; padding-right: 10px; border-right: 1px solid gray;">
					<div style="font-size: 80%; margin: 5px 0; color: gray; font-weight: 600;">选择周期模式:</div>
					<ul class="v-tab">
						<!-- ko foreach: cycleTypes -->
						<li data-bind="text:name, click:$parent.typeSelector,css:{active:$parent.selectCycleType()==id}" class="v-tab-item"></li>
						<!-- /ko -->
					</ul>
				</div>
				<div style="width: 100%; padding-left: 10px">
					<div style="font-size: 80%; margin: 5px 0; color: gray; font-weight: 600;">周期设定：</div>
					<div>
						<!-- ko if: selectCycleType()=='byDay' -->
						<label>
							<input name="byDay" checked="checked" data-bind="checked:dayMethod" value="0" type="radio" />
							<span>每隔</span>
							<input type="text" data-bind="value:during,valueUpdate:'afterkeydown'" />
							<span>天</span>
						</label>
						<br />
						<br />
						<label>
							<input name="byDay" data-bind="checked:dayMethod" value="1" type="radio" />
							<span>每个工作日</span>
						</label>
						<!-- /ko -->
						<!-- ko if: selectCycleType()=='byWeek' -->
						<label>每周的:</label>
						<br />
						<br />
						<!-- ko foreach: weeks -->
						<label>
							<input name="byWeek" type="checkbox" data-bind="value:id,checked:$parent.selectWeek" />
							<span data-bind="text:name"></span>
						</label>
						<!-- /ko -->
						<!-- /ko -->
						<!-- ko if: selectCycleType()=='byMonth' -->
						<label>
							<input name="byMonth" checked="checked" data-bind="checked:monthMethod" value="0" type="radio" />
							<span>每</span>
							<input type="text" data-bind="value:during,valueUpdate:'afterkeydown'" />
							<span>个月的第</span>
							<input type="text" data-bind="value:theCount,valueUpdate:'afterkeydown'" />
							<span>天</span>
						</label>
						<br />
						<br />
						<label>
							<input name="byMonth" data-bind="checked:monthMethod" value="1" type="radio" />
							<span>每</span>
							<input type="text" data-bind="value:during,valueUpdate:'afterkeydown'" />
							<span>个月的</span>
							<select data-bind="options: weeksOfMonth, optionsText: 'name',optionsValue: 'id',value:selectWeekOfMonth"></select>
							<select data-bind="options: weekEx, optionsText: 'name',optionsValue: 'id',value: selectWeekEx"></select>
						</label>
						<!-- /ko -->
						<!-- ko if: selectCycleType()=='byYear' -->
						<label>
							<span>重复间隔为</span>
							<input type="text" data-bind="value:during,valueUpdate:'afterkeydown'" />
							<span>年后的:</span>
						</label>
						<br />
						<br />
						<label>
							<input name="byYear" checked="checked" data-bind="checked:yearMethod" value="0" type="radio" />
							<span>时间:</span>
							<select data-bind="options: months, optionsText: 'name',optionsValue: 'id',value:selectMonth"></select>
							<input type="text" data-bind="value:theCount,valueUpdate:'afterkeydown'" />
							<span>日</span>
						</label>
						<br />
						<br />
						<label>
							<input name="byYear" data-bind="checked:yearMethod" value="1" type="radio" />
							<span>时间:</span>
							<select data-bind="options: months, optionsText: 'name',optionsValue: 'id',value:selectMonth"></select>
							<span>的</span>
							<select data-bind="options: weeksOfMonth, optionsText: 'name',optionsValue: 'id',value:selectWeekOfMonth"></select>
							<select data-bind="options: weekEx, optionsText: 'name',optionsValue: 'id',value: selectWeekEx"></select>
						</label>
						<!-- /ko -->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var vm = new jobScheduleViewModel();
	vm.init();

	var blade = $("#jobSchedule");
	ko.applyBindings(vm, blade[0]);
</script>