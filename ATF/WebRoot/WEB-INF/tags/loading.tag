<%@tag pageEncoding="UTF-8" display-name="Blade loading tag"%>
<%@attribute name="text" type="java.lang.String"%>
<div style="font-size: 13px;">
	<%
		if (text == null || text.isEmpty()) {
	%>
	正在加载数据...
	<%
		} else {
	%>
	${text }
	<%
		}
	%>
</div>