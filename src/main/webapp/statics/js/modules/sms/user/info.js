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
                    myAjaxShort(
                        basePath + "/sys/user/password",
                        {"password":oldPwd, "newPassword":newPwd1},
                        "post",
                        function(result){
                            if (result.code != 1) {
                                myAlert(result.message);
                            } else {//success
                                myToast('修改密码成功！');
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