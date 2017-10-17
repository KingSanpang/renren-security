<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"  %>
<%
String basePath = request.getContextPath();
%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%//@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!-- Bootstrap-->
<link href="<%=basePath%>/statics/plugins/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="<%=basePath%>/statics/libs/html5shiv.min.js"></script>
  <script src="<%=basePath%>/statics/libs/respond.min.js"></script>
<![endif]-->
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="<%=basePath%>/statics/libs/jquery1.12.4.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<%=basePath%>/statics/plugins/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=basePath%>/statics/plugins/bootstrap-table/bootstrap-table.min.css">
<script src="<%=basePath%>/statics/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="<%=basePath%>/statics/plugins/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script src="<%=basePath%>/statics/libs/jquery.bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=basePath%>/statics/plugins/jquery-confirm-3.3/jquery-confirm.min.css">
<script src="<%=basePath%>/statics/plugins/jquery-confirm-3.3/jquery-confirm.min.js"></script>
<script src="<%=basePath%>/statics/js/common.js"></script>
<script src="<%=basePath%>/statics/plugins/serializeJson/jquery.serializejson.min.js"></script>
<link rel="stylesheet" href="<%=basePath%>/statics/css/common/common.css">
<script>
var basePath = '<%=basePath%>';
</script>