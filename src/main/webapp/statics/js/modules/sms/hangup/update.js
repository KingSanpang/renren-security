var COLOR = ["#6699CC", "#663366", "#CCCC99", "#990033", "#CCFF66", "#FF9900","#666699", "#660033", "#99CC99"];
var START_TIME = 360;//早6点
var END_TIME = 1320;//晚10点
var INTERVAL = 960.0;//总共时间间隔是960分钟，共16个小时
var oldData;
var signature = "设置短信签名";//短信签名
$(function(){
    initScaleDiv();
    $("#save").click(function(){
        saveForm();
    });
    $("#del").click(function(){

    });
    loadHangupConfig();
});
function initScaleDiv(){
    var startTime = START_TIME + 60;
    var widthPercent = (100.0/(INTERVAL/60)) + "%";
    var str = "<div class='scale_left' style='width:"+widthPercent+"'>"+(startTime/60)+"</div>";
    while(startTime<END_TIME){
        startTime = startTime + 60;
        str += "<div class='scale' style='width:"+widthPercent+"'>"+(startTime/60)+"</div>";
    }
    $("#scaleDiv").empty().append(str);
}
function loadHangupConfig(){
    //myAjaxShort(url, data, type, success, beforeSend)
    myAjaxShort(
        //basePath + "/manage/hangup/listHangupConfig",
        basePath + "/statics/js/modules/sms/hangup/hangupList.json",//默认返回的数据是有序的
        {},
        "get",
        function(result){
            alert("success");
            if (result.code != 1) {
                myAlert(result.message);
            } else {//success
                console.log(result.data);
                oldData = result.data;
                rebuildTimeAxisDiv(result.data);
                myAlert('加载！');
            }
        }
    );
}
function rebuildTimeAxisDiv(data){
    $("#timeAxisDiv").empty();
    if(data == null){
        return;
    }
    var width = 0;
    var height = 20;
    var innerStr = "";
    var lastEnd = START_TIME;
    var i = 0;
    $.each(data, function(index, item){
        if(item.beginTime > lastEnd){
            innerStr += "<div onclick='clickTimeAxis(null,"+lastEnd+","+item.beginTime+")' style='float:left;height:20px;width:" + (item.beginTime-lastEnd)/INTERVAL * 100 + "%;background:"
                + "gray'></div>";//COLOR[i++]
        }
        innerStr += "<div onclick='clickTimeAxis("+index+")' style='float:left;height:20px;width:" + (item.endTime-item.beginTime)/INTERVAL * 100 + "%;background:"
            + COLOR[i++] + "'></div>";
        if(index == data.length-1){
            if(END_TIME > item.endTime){
                innerStr += "<div onclick='clickTimeAxis(null, "+item.endTime+", "+END_TIME+")' style='float:left;height:20px;width:" + (END_TIME - item.endTime)/INTERVAL * 100 + "%;background:"
                    + "gray'></div>";//COLOR[i++]
            }
        }
        lastEnd = item.endTime;
    });
    $("#timeAxisDiv").append(innerStr);
}
function clickTimeAxis(index, start, end){
    if(index != null){
        console.log(oldData[index]);
        $("#del").show();
        var curData = oldData[index];
        $("#id").val(curData.id);
        $("#beginTime").val(curData.beginTime);
        $("#endTime").val(curData.endTime);
        $("#rate").val(curData.rate);
        $("input[name=rate][value="+curData.rate+"]").attr("checked","checked");
        $("#content").val(curData.content);
    }else{
        $("#del").hide();
        $("#updateForm")[0].reset();
        $("#beginTime").val(start);
        $("#endTime").val(end);
    }
}
function getEntInfo(){
    myAjaxShort(
        basePath + "/manage/ent/getById",
        {},
        "post",
        function(result){
            if (result.code != 1) {
                myAlert(result.message);
                $("#save").remove();
            } else {//success
                myAlert('success');
                $("#entId").val(result.data.entId);
                $("#entName").val(result.data.entName);
                $("#entDesc").val(result.data.entDesc);
            }
        },
        function(){
            loading("加载中！");
        }
    );
}
function saveForm(){
    if(oldData != null && oldData.length >= 3){
        myAlert("最多只能配置三个挂机短信。");
        return;
    }
    var id = $("#id").val();
    var beginTime = $("#beginTime").val();
    var endTime = $("#endTime").val();
    var content = $.trim($("#content").val());
    if(content == ""){
        myAlert("挂机短信不能为空。");
        $('#content').focus();
        return;
    }
    if(beginTime >= endTime){
        myAlert("开始时间不能大于结束时间！");
        return ;
    }
    var ifIn = false;
    $.each(oldData, function(index, item){
        if(id!=null && id == item.id){//修改的时候不与原来的自己进行范围校验
            return true;
        }
        if(endTime <= item.beginTime || beginTime >= item.endTime){
            return true;
        }else{
            ifIn = true;
            return false;
        }
    });
    if(ifIn){
        myAlert("与已有挂机短信时间冲突。");
        return;
    }
    //myAjaxShort(url, data, type, success, beforeSend)
    myAjaxShort(
        basePath + "/manage/hangup/update",
        $('#updateForm').serializeJSON(),
        "post",
        function(result){
            if (result.code != 1) {
                myAlert(result.message);
            } else {//success
                myToast('保存成功！');
                loadHangupConfig();
            }
        }
    );
}
function delHangup(){
    var id = $("#id").val();
    if(id == null || id == ''){
        myAlert("请先选择一条记录！");
        return;
    }
    $.confirm({
        title: '提示',
        content: '确定删除该条记录？',
        buttons: {
            del: {
                text: '确定',
                action: function () {
                    myAjaxShort(
                        basePath + "/manage/hangup/delete",
                        $('#updateForm').serializeJSON(),
                        "post",
                        function(result){
                            if (result.code != 1) {
                                myAlert(result.message);
                            } else {//success
                                myToast('删除成功！');
                                loadHangupConfig();
                            }
                        }
                    );
                }
            }
        }
    });
}
function changeSignature(){
    $.confirm({
        title: '修改设置短信签名',
        content: '' +
        '<form action="" class="formName">' +
        '<div class="form-group">' +
        '<input id="signature" type="text" placeholder="短信签名" class="name form-control" required value="'+signature+'"/>' +
        '</div>' +
        '</form>',
        buttons: {
            formSubmit: {
                text: '确定',
                btnClass: 'btn-blue',
                action: function () {
                    var sigTemp = this.$content.find('#signature').val();
                    sigTemp = $.trim(sigTemp);
                    if(sigTemp == ''){
                        myAlert("短信签名不能为空！");
                        return false;
                    }else{
                        $.alert('Your name is ' + name);
                        myAjaxShort(
                            basePath + "/manage/signature/update",
                            {"signature":sigTemp},
                            "post",
                            function(result){
                                if (result.code != 1) {
                                    myAlert(result.message);
                                } else {//success
                                    signature = sigTemp;
                                    myToast('修改短信签名成功！');
                                }
                            }
                        );
                    }
                }
            },
            cancel: {
                text: '取消'
            }
        }
    });
}

/*
$.confirm({
    title: 'Logout?',
    content: 'Your time is out, you will be automatically logged out in 10 seconds.',
    autoClose: 'logoutUser|10000',
    buttons: {
        logoutUser: {
            text: 'logout myself',
            action: function () {
                $.alert('The user was logged out');
            }
        },
        cancel: function () {
            $.alert('canceled');
        }
    }
});
*/

