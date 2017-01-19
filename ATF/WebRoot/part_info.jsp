<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/templates"%>
<%@taglib prefix="home" tagdir="/WEB-INF/tags/home"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>
<div class="info-center">
	<div class="info-title">
		<div class="user-title">
			<h4>
				<strong><%=System.getProperties().getProperty("user.name")%>，</strong>
			</h4>
			<p>欢迎使用接口测试工具！</p>
		</div>
		<div class="year-month">
			<p>星期日</p>
			<p>
				<span>8888</span>
				年88月88日
			</p>
		</div>
		<div class="hour-minute">
			<strong>88:88:88</strong>
		</div>
	</div>
	<div class="info-main">
		<section class="tile-group">
			<div class="tile flex-h" style="width:calc(100%/4 - 60px);margin: 5px 15px;background-color: white">
				<div class="tile-icon">
					<icons:icon_system></icons:icon_system>
				</div>
				<div class="tile-content">
					<p class="tile-header">测试系统</p>
					<h3 class="tile-count">11</h3>
					<button class="tile-action">查看报告[未开通]</button>
				</div>
			</div>
			<div class="tile flex-h" style="width:calc(100%/4 - 60px);margin: 5px 15px;background-color: white">
				<div class="tile-icon">
					<icons:icon_interface></icons:icon_interface>
				</div>
				<div class="tile-content">
					<p class="tile-header">测试接口</p>
					<h3 class="tile-count">110</h3>
					<button class="tile-action">查看报告[未开通]</button>
				</div>
			</div>
			<div class="tile flex-h" style="width:calc(100%/4 - 60px);margin: 5px 15px;background-color: white">
				<div class="tile-icon">
					<icons:icon_case></icons:icon_case>
				</div>
				<div class="tile-content">
					<p class="tile-header">测试用例</p>
					<h3 class="tile-count">1152</h3>
					<button class="tile-action">查看报告[未开通]</button>
				</div>
			</div>
			<div class="tile flex-h" style="width:calc(100%/4 - 60px);margin: 5px 15px;background-color: white">
				<div class="tile-icon">
					<icons:icon_timer></icons:icon_timer>
				</div>
				<div class="tile-content">
					<p class="tile-header">计划任务</p>
					<h3 class="tile-count">1100</h3>
					<button class="tile-action">查看报告[未开通]</button>
				</div>
			</div>
			<div class="tile flex-h" style="width:calc(100%/3 * 2 - 60px);margin: 5px 15px;background-color: white">
				<table style="width:100%">
					<caption>近期执行的任务计划(TOP10)</caption>
					<tbody>
						<%
							String[] sys = { "礼品卡系统", "退换货系统", "支付系统", "结算系统", "JIT系统", "礼券系统", "移仓系统", "预售系统", "订单系统", "交易系统", "发票系统" };
							for (int i = 1; i <= 10; i++) {
						%>
						<tr>
							<td style="text-align: left;width:calc(100% - 330px)">
								计划名称<%=i%>_2012_12_12_12_12_12</td>
							<td style="width:100px">
								<%=sys[(int) (1 + Math.random() * (10 - 1 + 1))]%></td>
							<td style="width:150px">2012-12-12 12:12:12</td>
							<td style="text-align: right;width:60px">成功</td>
						</tr>
						<%
							}
						%>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="4">
								<button class="tile-action">查看更多>>[未开通]</button>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
			<div class="tile flex-h" style="width:calc(100%/3 - 60px);margin: 5px 15px;background-color: white">
				<table style="width:100%">
					<caption>异常监控列表(TOP10)</caption>
					<tbody>
						<%
							for (int i = 1; i <= 10; i++) {
						%>
						<tr>
							<td style="width:calc(100% - 150px)">异常信息异常信息异常信息异常信息异常信息异常信息异常信息异常信息异常信息异常信息异常信息</td>
							<td style="text-align:right;width:150px">2012-12-12 12:12:12</td>
						</tr>
						<%
							}
						%>
					</tbody>
					<tfoot>
						<tr>
							<td colspan="2">
								<button class="tile-action">查看更多>>[未开通]</button>
							</td>
						</tr>
					</tfoot>
				</table>
			</div>
		</section>
	</div>
</div>
