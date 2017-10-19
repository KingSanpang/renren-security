<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8"  %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file= "/WEB-INF/views/common/include.jsp" %>
    <title>修改挂短策略</title>
  </head>
  <script src="<%=basePath%>/statics/js/modules/sms/hangup/update.js"></script>

  <link href="<%=basePath%>/statics/css/modules/sms/hangup/update.css" rel="stylesheet" type="text/css" />
  <body>
  <div class="container">
      <div class="row">
        <div class="col-lg-12">
            <%@ include file= "/WEB-INF/views/common/menu.jsp" %>
        </div>
      </div>
      <div class="row">
        <div class="col-lg-12">
            <div style="position:relative;height:40px;background:#FFF8DC">
            	<div id="timeAxisDiv" style="position:absolute;top:0px;left:0px;width:100%;height:100%">
            		<div style='float:left;height:40px;width:10%;background:#FFD700'></div>
            		<div style='float:left;height:40px;width:10%;background:#FFC0CB'></div>
            		<div style='float:left;height:40px;width:10%;background:#FFA07A'></div>
            		<div style='float:left;height:40px;width:10%;background:#FF82AB'></div>
            	</div>
            	<div id="scaleDiv" style="position:absolute;top:28px;left:0px;width:100%;height:12px;">
            	</div>
            </div>
            </br>
        </div>
      </div>
      <div class="row">
        <div class="col-lg-12">
            <form id="updateForm" class="form-horizontal">
              <div class="form-group">
                <label for="beginTime" class="col-lg-2 control-label">起始时间：</label>
                <div class="col-lg-10">
                    <input id="id" type="hidden"/>
                    <select id="beginTime" name="beginTime" class="form-control">
                        <option value="540">09:00</option>
                        <option value="570">09:30</option>
                        <option value="600">10:00</option>
                        <option value="630">10:30</option>
                        <option value="660">11:00</option>
                        <option value="690">11:30</option>
                        <option value="720">12:00</option>
                        <option value="750">12:30</option>
                        <option value="780">13:00</option>
                        <option value="810">13:30</option>
                        <option value="840">14:00</option>
                        <option value="870">14:30</option>
                        <option value="900">15:00</option>
                        <option value="930">15:30</option>
                        <option value="960">16:00</option>
                        <option value="990">16:30</option>
                        <option value="1020">17:00</option>
                        <option value="1050">17:30</option>
                        <option value="1080">18:00</option>
                        <option value="1110">18:30</option>
                        <option value="1140">19:00</option>
                        <option value="1170">19:30</option>
                        <option value="1200">20:00</option>
                        <option value="1230">20:30</option>
                        <option value="1260">21:00</option>
                    </select>
                </div>
              </div>
              <div class="form-group">
                  <label for="endTime" class="col-lg-2 control-label">结束时间：</label>
                  <div class="col-lg-10">
                      <select id="endTime" name="endTime" class="form-control">
                          <option value="540">09:00</option>
                          <option value="570">09:30</option>
                          <option value="600">10:00</option>
                          <option value="630">10:30</option>
                          <option value="660">11:00</option>
                          <option value="690">11:30</option>
                          <option value="720">12:00</option>
                          <option value="750">12:30</option>
                          <option value="780">13:00</option>
                          <option value="810">13:30</option>
                          <option value="840">14:00</option>
                          <option value="870">14:30</option>
                          <option value="900">15:00</option>
                          <option value="930">15:30</option>
                          <option value="960">16:00</option>
                          <option value="990">16:30</option>
                          <option value="1020">17:00</option>
                          <option value="1050">17:30</option>
                          <option value="1080">18:00</option>
                          <option value="1110">18:30</option>
                          <option value="1140">19:00</option>
                          <option value="1170">19:30</option>
                          <option value="1200">20:00</option>
                          <option value="1230">20:30</option>
                          <option value="1260">21:00</option>
                      </select>
                  </div>
              </div>
              <div class="form-group">
                <label class="col-lg-2 control-label">发送频率：</label>
                <div class="col-lg-10">
                    <label class="radio-inline">
                      <input type="radio" name="rate" value="1" checked="checked"/> 1次/天
                    </label>
                    <label class="radio-inline">
                      <input type="radio" name="rate" value="2"/> 1次/周
                    </label>
                    <label class="radio-inline">
                      <input type="radio" name="rate" value="3"/> 1次/月
                    </label>
                    <label class="radio-inline">
                      <input type="radio" name="rate" value="0"/> 每次
                    </label>
                </div>
              </div>
              <div class="form-group">
                <label for="entDesc" class="col-lg-2 control-label">挂短内容：</label>
                <div class="col-lg-10">
                    <textarea id="content" name="content" class="form-control" rows="5" maxlength="1024"></textarea>
                    <div style="text-align:right;">
                        <span id="signatureLabel" onclick="changeSignature()" class="label label-primary">请点击此处配置短信签名</span>
                    </div>
                </div>
              </div>
              <div class="form-group">
                <div class="col-lg-12 text-center">
                    <a id="save" class="btn btn-primary" href="#" role="button">保存</a>
                    &ensp;&ensp;&ensp;&ensp;
                    <a id="del" class="btn btn-primary" style="display:none" href="#" role="button">删除</a>
                </div>
              </div>
            </form>
        </div>
      </div>
  </div>
  </body>
</html>