<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>
<div id="ioParameter" data-vm="io.io">
	<jsp:include page="/pages/io/part_index/loadLayout.jsp"></jsp:include>
	<jsp:include page="/pages/io/part_index/infoLayout.jsp"></jsp:include>
	<jsp:include page="/pages/io/part_index/editLayout.jsp"></jsp:include>
</div> 
<script>
	$.registVM();
</script>