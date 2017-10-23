//修改密码
function changePwd(){
    $.confirm({
        title: '修改密码',
        content: '' +
        '<form action="" class="formName">' +
        '<div class="form-group">' +
        '<input id="oldPwd" type="password" placeholder="老密码" class="name form-control" required/></br>' +
        '<input id="newPwd1" type="password" placeholder="新密码" class="name form-control" required/></br>' +
        '<input id="newPwd2" type="password" placeholder="确认新密码" class="name form-control" required/>' +
        '</div>' +
        '</form>',
        buttons: {
            formSubmit: {
                text: '确定',
                btnClass: 'btn-blue',
                action: function () {
                    var oldPwd = this.$content.find('#oldPwd').val();
                    var newPwd1 = this.$content.find('#newPwd1').val();
                    var newPwd2 = this.$content.find('#newPwd2').val();
                    oldPwd = $.trim(oldPwd);
                    newPwd1 = $.trim(newPwd1);
                    newPwd2 = $.trim(newPwd2);
                    if(oldPwd == ''){
                        myToast("老密码不能为空！");
                        return false;
                    }
                    if(newPwd1 == ''){
                        myToast("新密码不能为空！");
                        return false;
                    }
                    if(newPwd1 != newPwd2){
                        myToast("新密码不相同！");
                        return false;
                    }
                    myAjax(
                        basePath + "/sys/user/password",
                        {"password":oldPwd, "newPassword":newPwd1},
                        "post",
                        "application/x-www-form-urlencoded",
                        function(result){
                            if (result.code != 0) {
                                myAlert(result.msg);
                            } else {//success
                                myToast('修改密码成功！');
                            }
                        }, null, null, null
                    );
                }
            },
            cancel: {
                text: '取消'
            }
        }
    });
}
//修改老板号
function changeBossTel(){
    $.confirm({
        title: '设置老板号',
        content: '' +
        '<form action="" class="formName">' +
        '<div class="form-group">' +
        '<input id="bossTel" type="text" placeholder="请输入老板手机号" class="name form-control" required/>' +
        '</div>' +
        '</form>',
        buttons: {
            formSubmit: {
                text: '确定',
                btnClass: 'btn-blue',
                action: function () {
                    var bossTel = this.$content.find('#bossTel').val();
                    bossTel = $.trim(bossTel);
                    if(bossTel == ''){
                        myToast("老板手机号不能为空！");
                        return false;
                    }
                    myAjaxShort(
                        basePath + "/sms/user/updateBossTel",
                        JSON.stringify({"bossTel":bossTel}),
                        "post",
                        function(result){
                            if (result.code != 0) {
                                myAlert(result.msg);
                            } else {//success
                                myToast('设置老板号成功！');
                                $("#bossTelTd").empty().append(bossTel).append('&ensp;&ensp;').append('<span class="label label-primary">审核中</span>')
                                    .append('&ensp;&ensp;').append('<span onclick="changeBossTel()" class="label label-danger">更改老板号</span>');
                            }
                        }
                    );
                }
            },
            cancel: {
                text: '取消'
            }
        }
    });
}