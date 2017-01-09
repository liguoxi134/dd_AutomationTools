<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="icons" tagdir="/WEB-INF/tags/icons"%>

<!-- ko if: !isLoadingData()&&!isSavingData() -->
<section class="tile-group">
	<jsp:include page="/pages/io/part_index/infoLayout/summary.jsp"></jsp:include>
	<jsp:include page="/pages/io/part_index/infoLayout/inArgs.jsp"></jsp:include>
	<jsp:include page="/pages/io/part_index/infoLayout/verifyAndReturn.jsp"></jsp:include>
	<jsp:include page="/pages/io/part_index/infoLayout/editExpectResult4Return.jsp"></jsp:include>
	<jsp:include page="/pages/io/part_index/infoLayout/createExpectResult4Return.jsp"></jsp:include>
	<jsp:include page="/pages/io/part_index/infoLayout/editExpectResult4DB.jsp"></jsp:include>
	<jsp:include page="/pages/io/part_index/infoLayout/createExpectResult4DB.jsp"></jsp:include>
</section>
<!-- /ko -->