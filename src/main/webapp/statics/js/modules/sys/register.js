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
        $('#initForm').serializeJSON(),
        "post",
        function(result){
            alert("success");
            if (result.code != 0) {
                myAlert(result.msg);
            } else {//success
                window.location.href= basePath + "/";
            }
        },
        function(){
            if ($.trim($('#username').val()) == '') {
                $('#username').focus();
                return false;
            }
            if ($.trim($('#password').val()) == '') {
                $('#password').focus();
                return false;
            }
            if ($.trim($('#password2').val()) == '') {
                $('#password2').focus();
                return false;
            }
            var psd = $.trim($('#password').val());
            var psd2 = $.trim($('#password2').val());
            if(psd != psd2){
                $('#password2').val("");
                $('#password2').focus();
                return false;
            }
            loading("注册中...");
        }
    );
}