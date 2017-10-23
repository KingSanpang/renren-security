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
            title: '自主设置挂短',
            formatter:function (value, row, index) {
                if(value==0){
                    return "禁止";
                }else if(value == 1){
                    return "允许";
                }
            }
        }, {
            field: 'status',
            title: '状态',
            formatter:function (value, row, index) {
                if(value == 0){
                    return "停用";
                }else if(value == 1){
                    return "正常";
                }else if(value == 2){
                    return "审核中";
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
    console.log(index);
    console.log(row);
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
}