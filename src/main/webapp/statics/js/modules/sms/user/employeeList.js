var employeeTable;
$(function () {
    initEmployeeTable();
    $("#search").click(function () {
        var name = $("#employeeName").val();
        $('#employeeTable').bootstrapTable('refresh', {
            query:{
                employeeName : name
            }
        });
    });
});
function initEmployeeTable() {
    employeeTable = $('#employeeTable').bootstrapTable({
        url: basePath + "/sms/user/employeeList",
        method:"post",
        contentType:'application/json',
        pagination: true,
        sidePagination:'server',
        striped: true,
        queryParams:queryParams,
        uniqueId:'id',
        pageSize: 2,
        columns: [{
            field: 'employeeName',
            title: '员工手机号'
        }, {
            field: 'setHangup',
            title: '允许设置挂短',
            formatter:function (value, row, index) {
                if(value==0){
                    return "禁止&ensp;&ensp;<button type='button' onclick='changeSetHangup("+index+","+value+","+JSON.stringify(row)+")' class='btn btn-default'>允许</button>";
                }else if(value == 1){
                    return "允许&ensp;&ensp;<button type='button' onclick='changeSetHangup("+index+","+value+","+JSON.stringify(row)+")' class='btn btn-default'>禁止</button>";
                }
            }
        }, {
            field: 'status',
            title: '状态',
            formatter:function (value, row, index) {
                if(value == 0){
                    return "停用&ensp;&ensp;<button type='button' onclick='changeStatus("+index+","+value+","+JSON.stringify(row)+")' class='btn btn-default'>启用</button>";
                }else if(value == 1){
                    return "启用&ensp;&ensp;<button type='button' onclick='changeStatus("+index+","+value+","+JSON.stringify(row)+")' class='btn btn-default'>停用</button>";
                }else if(value == 2){
                    return "未审核&ensp;&ensp;<button type='button' onclick='changeStatus("+index+","+value+","+JSON.stringify(row)+")' class='btn btn-default'>审核通过</button>";
                }
            }
        }, {
            field: "oper",
            title: '操作',
            formatter:function (value, row, index) {
                return "<button type='button' onclick='deleteEmployee("+index+", "+JSON.stringify(row)+")' class='btn btn-default'>删除</button>";
            }
        } ]
    });
}
function queryParams(params) {
    var temp = {
        pageSize: params.limit,
        pageNum: params.offset/params.limit+1,
        employeeName:$("#employeeName").val()
    };
    return temp;
}
function deleteEmployee(index, row) {
    myConfirm("确定删除该员工吗？",function(){
        //myAjaxShort(url, data, type, success, beforeSend)
        myAjaxShort(
            basePath + "/sms/user/deleteEmployee",
            JSON.stringify({id: row.id}),
            "post",
            function(result){
                if (result.code != 0) {
                    myAlert(result.msg);
                } else {//success
                    $('#employeeTable').bootstrapTable('removeByUniqueId', row.id);
                }
            },
            function () {
                loading("删除中...");
            }
        );
    });
}
function changeSetHangup(index, value, row){
    var msg;
    var newValue;
    if(value==0){
        msg="确定允许该员工自定义挂短配置吗？";
        newValue = 1;
    }else if(value == 1){
        msg="确定禁止该员工自定义挂短配置吗？";
        newValue = 0;
    }
    myConfirm(msg, function(){
        //myAjaxShort(url, data, type, success, beforeSend)
        myAjaxShort(
            basePath + "/sms/user/changeSetHangup",
            JSON.stringify({id: row.id}),
            "post",
            function(result){
                if (result.code != 0) {
                    myAlert(result.msg);
                } else {//success
                    $('#employeeTable').bootstrapTable('updateCell', {'index':index,'field':'setHangup','value':newValue});
                }
            },
            function () {
                loading("修改中...");
            }
        );
    });
}
function changeStatus(index, value, row){
    var msg;
    var newValue;
    if(value == 0){
        msg="确定启用该员工吗？";
        newValue = 1;
    }else if(value == 1){
        msg="确定停用该员工吗？";
        newValue = 0;
    }else if(value == 2){
        msg="确定审核通过吗？";
        newValue = 1;
    }
    myConfirm(msg, function(){
        //myAjaxShort(url, data, type, success, beforeSend)
        myAjaxShort(
            basePath + "/sms/user/changeStatus",
            JSON.stringify({id: row.id, status: newValue}),
            "post",
            function(result){
                if (result.code != 0) {
                    myAlert(result.msg);
                } else {//success
                    $('#employeeTable').bootstrapTable('updateCell', {'index':index,'field':'status','value':newValue});
                }
            },
            function () {
                loading("修改中...");
            }
        );
    });
}
