//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';

var baseURL = "../../";

//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return  unescape(r[2]); return null;
};
T.p = url;

//全局配置
$.ajaxSetup({
	dataType: "json",
	cache: false
});

//重写alert
window.alert = function(msg, callback){
	parent.layer.alert(msg, function(index){
		parent.layer.close(index);
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//重写confirm式样框
window.confirm = function(msg, callback){
	parent.layer.confirm(msg, {btn: ['确定','取消']},
	function(){//确定事件
		if(typeof(callback) === "function"){
			callback("ok");
		}
	});
}

//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    var selectedIDs = grid.getGridParam("selarrrow");
    if(selectedIDs.length > 1){
    	alert("只能选择一条记录");
    	return ;
    }
    
    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if(!rowKey){
    	alert("请选择一条记录");
    	return ;
    }
    
    return grid.getGridParam("selarrrow");
}

//判断是否为空
function isBlank(value) {
    return !value || !/\S/.test(value)
}

/**
 * Created by Administrator on 2017-9-7.
 */
function myAjaxShort(url, data, type, success, beforeSend) {
	myAjax(url, data, type, null, success, beforeSend, null, null);
}
function myAjax(url, data, type, contentType, success, beforeSend, complete, loadingName){
	$.ajax({
		type: type || 'post',
		url: url || 'http://www.baidu.com',
		data: data || {},
		contentType : contentType || 'application/json',
		beforeSend: beforeSend || function() {
			alert('default beforeSend');
		},
		success: success || function(result) {
			alert('default success');
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			myAlert("网络异常！");
			console.log("error begin:");
			console.log(XMLHttpRequest);
			console.log(textStatus);
			console.log(errorThrown);
			console.log("error end.");
		},
		complete : complete || function(){
			unloading(loadingName);
			alert('default complete');
		}
	});
}
function myAlert(content){
	$.alert({
		title: '提示',
		content: content||'提示！'
	});
}
function myToast(content){
	$.alert({
		title: '提示',
		content: content||'提示！',
		backgroundDismiss: true
		//autoClose: 'null|8000'
	});
}

function loading(title, name){
	$('body').loading({
		loadingWidth:240,
		title: title || '加载中！',
		name: name || 'loadingName20170907',
		animateIn:'none',
		discription:'这是一个描述...',
		direction:'row',
		type:'origin',
		mustRelative:true,
		originBg:'#71EA71',
		originDivWidth:30,
		originDivHeight:30,
		originWidth:4,
		originHeight:4,
		smallLoading:false,
		titleColor:'#388E7A',
		loadingBg:'#312923',
		loadingMaskBg:'rgba(22,22,22,0.2)'
	});
}
function unloading(name){
	removeLoading(name || 'loadingName20170907');
}
