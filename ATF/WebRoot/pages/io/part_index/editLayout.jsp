<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>

<!-- ko if: editLayoutId() != undefined && editLayoutId() != "" -->
<div class="edit-layout">
	<jsp:include page="/pages/io/part_index/editLayout/summary.jsp"></jsp:include>
	<jsp:include page="/pages/io/part_index/editLayout/inArgs.jsp"></jsp:include>
	<jsp:include page="/pages/io/part_index/editLayout/verifyAndReturn.jsp"></jsp:include>
	<jsp:include page="/pages/io/part_index/editLayout/createExpectResult4Return.jsp"></jsp:include>
	<jsp:include page="/pages/io/part_index/editLayout/editExpectResult4Return.jsp"></jsp:include>
	<jsp:include page="/pages/io/part_index/editLayout/createExpectResult4DB.jsp"></jsp:include>
	<jsp:include page="/pages/io/part_index/editLayout/editExpectResult4DB.jsp"></jsp:include>
</div>
<!-- /ko -->