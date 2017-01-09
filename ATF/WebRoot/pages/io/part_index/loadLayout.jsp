<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="atf" tagdir="/WEB-INF/tags"%>

<!-- ko if: isSavingData() -->
<atf:loading text="正在保存输入输出配置信息..."></atf:loading>
<!-- /ko -->

<!-- ko if: isLoadingData() -->
<atf:loading text="正在收集输入输出配置信息..."></atf:loading>
<!-- /ko -->