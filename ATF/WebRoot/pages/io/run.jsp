<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="atf" tagdir="/WEB-INF/tags"%>
<style>
#runTestConfig {
	height: calc(100% - 35px);
}

#runTestConfig .form-item, #runTestConfig label {
	height: 100%;
}

#runTestConfig label {
	display: flex;
	flex-direction: column;
}

#runTestConfig small {
	font-size: 1.125rem;
}

#runTestConfig textarea {
	height: 100%;
}
</style>
<div id="runTestConfig" data-vm="io.run">
	<!-- ko if: isLoadingData() -->
	<atf:loading text="正在执行..."></atf:loading>
	<!-- /ko -->

	<!-- ko if: !isLoadingData() -->
	<atf:form_textarea text="接口实际返回结果" bind="value:responseData"></atf:form_textarea>
	<!-- /ko -->
</div>
<script>
	$.registVM();
</script>