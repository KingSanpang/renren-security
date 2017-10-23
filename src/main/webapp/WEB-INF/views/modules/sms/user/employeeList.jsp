<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"  %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file= "/WEB-INF/views/common/include.jsp" %>
    <title>员工管理</title>
  </head>
  <script src="<%=basePath%>/statics/js/modules/sms/user/employeeList.js"></script>
  <body>
  <div class="container">
      <div class="row">
        <div class="col-lg-12">
            <%@ include file= "/WEB-INF/views/common/menu.jsp" %>
        </div>
      </div>
      <div class="row">
          <div class="col-lg-12">
            <form class="form-inline">
              <div class="form-group">
                <label for="employeeName">员工手机号：</label>
                <input type="text" class="form-control" id="employeeName" placeholder="请输入员工手机号">
              </div>
              <button id="search" type="button" class="btn btn-default">确定</button>
            </form>
          </div>
      </div>
      <div class="row">
          <div class="col-lg-12">
            <br>
            <table id="employeeTable"></table>
          </div>
      </div>
  </div>
  </body>
</html>