<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"  %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file= "/WEB-INF/jsp/common/include.jsp" %>
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>注册</title>
</head>
<script src="<%=basePath%>/statics/js/modules/sys/register.js"></script>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <h3>用户注册</h3>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-12">
            <form id="initForm" class="form-horizontal">
                <div class="form-group">
                    <label class="col-lg-2 control-label">您的身份：</label>
                    <div class="col-lg-10">
                        <label class="radio-inline">
                            <input type="radio" name="userType" value="1" checked="checked"> 老板
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="userType" value="2"> 员工
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="username" class="col-lg-2 control-label">手机号：</label>
                    <div class="col-lg-10">
                        <input id="username" type="text" class="form-control" name="username" maxlength="20"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-lg-2 control-label">密码：</label>
                    <div class="col-lg-10">
                        <input id="password" type="text" autocomplete="off" class="form-control" name="password" maxlength="20"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password2" class="col-lg-2 control-label">确认密码：</label>
                    <div class="col-lg-10">
                        <input id="password2" type="text" autocomplete="off" class="form-control" name="password2" maxlength="20"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="email" class="col-lg-2 control-label">邮箱：</label>
                    <div class="col-lg-10">
                        <input id="email" type="text" class="form-control" name="email" maxlength="100"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-lg-12 text-center">
                        <a id="save" class="btn btn-primary" href="#" role="button">保存</a>
                        &ensp;&ensp;&ensp;&ensp;
                        <a id="clear" class="btn btn-primary" href="#" role="button">重置</a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>