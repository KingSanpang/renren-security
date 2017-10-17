$(function(){
    $("#save").click(function(){
        saveForm();
    });
    $("#clear").click(function(){
        $("#initForm")[0].reset();
    });
});
function saveForm(){
    //myAjaxShort(url, data, type, success, beforeSend)
    myAjaxShort(
        basePath + "/sys/user/register",
        JSON.stringify($('#initForm').serializeJSON()),
        "post",
        function(result){
            if (result.code != 0) {
                myAlert(result.msg);
            } else {//success
                window.location.href= basePath + "/";
            }
        },
        function(){
            if ($.trim($('#username').val()) == '') {
                $('#username').focus();
                myToast("用户名不能为空！");
                return false;
            }
            if ($.trim($('#password').val()) == '') {
                $('#password').focus();
                myToast("密码不能为空！");
                return false;
            }
            if ($.trim($('#password2').val()) == '') {
                $('#password2').focus();
                myToast("确认密码不能为空！");
                return false;
            }
            var psd = $.trim($('#password').val());
            var psd2 = $.trim($('#password2').val());
            if(psd != psd2){
                $('#password2').val("");
                $('#password2').focus();
                myToast("密码不相同！");
                return false;
            }
            loading("注册中...");
        }
    );
}