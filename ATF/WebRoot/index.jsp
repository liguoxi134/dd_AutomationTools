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
</body>
</html>
