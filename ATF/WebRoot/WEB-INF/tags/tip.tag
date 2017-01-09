<%@tag pageEncoding="UTF-8"%>
<%@attribute name="title" required="true" type="java.lang.String"%>
<%@attribute name="text" required="true" type="java.lang.String"%>
<div class="tip">
	<span class="tip-title">${title}</span>
	<span class="tip-text">${text}</span>
</div>