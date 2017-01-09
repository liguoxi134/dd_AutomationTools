<%@tag pageEncoding="UTF-8"%>
<%@attribute name="text" required="true" type="java.lang.String"%>
<%@attribute name="bind" required="true" type="java.lang.String"%>
<%@attribute name="tag" required="true" type="java.lang.String"%>
<jsp:element name="${tag}">
	<jsp:attribute name="class">tile-item</jsp:attribute>
	<jsp:body>
		<span>${text}</span>
		<span data-bind="${bind }"></span>
	</jsp:body>
</jsp:element>