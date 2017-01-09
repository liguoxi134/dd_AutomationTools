<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="log" class="log">
	<div class="log-charts">
		<div class="charts-pi"></div>
		<div class="charts-bar"></div>
	</div>
	<div class="log-list">
		<table>
			<thead>
				<tr>
					<th style="width: 80px;">级别</th>
					<th>消息</th>
					<th style="width: 180px;">进程</th>
					<th style="width: 210px;">时间</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>INFO</td>
					<td>Invoke method: List</td>
					<td>http-nio-8080-exec-2</td>
					<td>2016-12-21 14:27:28,271</td>
				</tr>
				<tr>
					<td>ERROR</td>
					<td>Test System
						List[{"id":"zhifu","externalId":"支付系统","name":"支付系统","state":true},{"id":"jiesuan","externalId":"结算系统","name":"结算系统","state":true},{"id":"kucun","externalId":"库存系统","name":"库存系统","state":true},{"id":"jiaoyi","externalId":"交易系统","name":"交易系统","state":true},{"id":"zhaoshang","externalId":"招商平台","name":"招商平台","state":true},{"id":"tms","externalId":"TMS","name":"TMS","state":true},{"id":"kefu","externalId":"客服系统","name":"客服系统","state":true},{"id":"wms","externalId":"WMS","name":"WMS","state":true}]</td>
					<td>http-nio-8080-exec-2</td>
					<td>2016-12-21 14:27:28,278</td>
				</tr>
				<tr>
					<td>WARN</td>
					<td>Add new DATABASE: {"id":"zhifu","externalId":"支付系统","name":"支付系统","state":true}</td>
					<td>http-nio-8080-exec-2</td>
					<td>2016-12-21 14:27:28,297</td>
				</tr>
				<tr>
					<td>DEBUG</td>
					<td>Loading config file: D:MyEclipse 2016 CI 7/ATF_v2/database.hibernate.cfg.xml</td>
					<td>http-nio-8080-exec-2</td>
					<td>2016-12-21 14:27:28,324</td>
				</tr>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="4">
						<div class="log-footer">
							<div class="pager">
								<a class="fxs-has-hover" href=#>首页</a>
								<a class="fxs-has-hover" href=#>上一页</a>
								<a class="fxs-has-hover" href=#>下一页</a>
								<a class="fxs-has-hover" href=#>最后一页</a>
							</div>
							<div class="jumper">
								<label>
									<span>跳转到</span>
									<input />
									<span>页</span>
								</label>
								<a class="fxs-has-hover" href=#>GO</a>
							</div>
						</div>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>
<script>
	$(".log-list tbody").on("click","tr:not(.active)",function() {
		$(".log-list tr.active").removeClass("active");
		$(this).addClass("active");
	})
	$(".log-list tbody").on("click","tr.active", function() {
		$(this).removeClass("active");
	})
</script>