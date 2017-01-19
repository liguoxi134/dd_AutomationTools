<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="template" tagdir="/WEB-INF/tags/templates"%>
<%@taglib prefix="home" tagdir="/WEB-INF/tags/home"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10,Edge" />
<title>ATF - Automation Testing Framework</title>
<link href="./css/site/site.css" rel="stylesheet" />
<link href="./css/site/home_layout.css" rel="stylesheet" />
<link href="./css/site/log.css" rel="stylesheet" />
<link href="./css/site/contextmenu.css" rel="stylesheet" />
</head>
<body oncontextmenu="return false;">
	<div id="web-container" class="fxs-portal">
		<div class="fxs-topbar">
			<home:appname></home:appname>
			<home:nav></home:nav>
			<home:userinfo></home:userinfo>
		</div>
		<jsp:include page="part_main.jsp"></jsp:include>
	</div>
	<home:blademenu></home:blademenu>
	<home:alertmsg></home:alertmsg>

	<template:check_icon_template></template:check_icon_template>
	<template:blade_template></template:blade_template>

	<jsp:include page="part_js.jsp"></jsp:include>
	<script type="text/javascript">
		window.onload = function() {
			var hm = $(".hour-minute strong");
			var ym = $(".year-month");
			var wk = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"];
			function padLeft(num) {
				return num >= 10 ? num : "0" + num;
			}
			setInterval(function() {
				if (hm.is(":visible")) {
					var date = new Date();
					hm.text(padLeft(date.getHours()) + ":" + padLeft(date.getMinutes()) + ":" + padLeft(date.getSeconds()));
					ym.html("<p>" + wk[date.getDay()] + "</p><p><span>" + date.getFullYear() + "</span>年" + padLeft(date.getMonth() + 1) + "月" + padLeft(date.getDate()) + "日</p>");
				}
			}, 1000);
		}
	</script>
</body>
</html>
