<%@tag body-content="scriptless" pageEncoding="UTF-8"%>
<%@attribute name="text" required="true" type="java.lang.String"%>
<%@attribute name="tip" required="false" type="java.lang.String"%>
<%@attribute name="bind" required="true" type="java.lang.String"%>
<div class="form-item text">
	<label>
		<%
			if (tip != null && !tip.isEmpty()) {
		%>
		<small>
			<span>${text}</span>
			<span class="i" data-tip="${tip}"></span>
		</small>
		<%
			} else {
		%>
		<small>${text}</small>
		<%
			}
		%>
		<input type="text" data-bind="${bind}" placeholder="${tip}" />
	</label>
	<jsp:doBody></jsp:doBody>
</div>