<%@tag body-content="scriptless" pageEncoding="UTF-8"%>
<%@attribute name="text" required="true" type="java.lang.String"%>
<%@attribute name="tip" required="false" type="java.lang.String"%>
<%@attribute name="bind" required="true" type="java.lang.String"%>
<div class="form-item textarea">
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
		<textarea style="white-space: pre" rows="10" cols="" placeholder="${tip}" data-bind="${bind}"></textarea>
	</label>
	<jsp:doBody></jsp:doBody>
</div>