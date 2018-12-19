// 请求生成后立即分配处理程序，请记住该请求针对 jqxhr 对象

var Q = {};

// 公共post发送方法
Q.post = function(paraObj) {
	paraObj.type = "POST";
	return Q.ajax(paraObj);
}
// 公共get发送方法
Q.get = function(paraObj) {
	paraObj.type = "GET";
	return Q.ajax(paraObj);
}
Q.ajax = function(paraObj) {
	var resData;
	// $("#request-process-patent").html("正在提交数据，请勿关闭当前窗口...");
	if (Q.isEmpty(paraObj.type)) {
		paraObj.type = "POST";
	}
	if (Q.isEmpty(paraObj.url)) {
		alert("url 必输");
	}
	if (Q.isEmpty(paraObj.contentType)) {
		paraObj.contentType = "application/json; charset=utf-8";
	}
	// 启用缓存
	if (Q.isEmpty(paraObj.cache)) {
		paraObj.cache = false;
	}
	// 异步请求
	if (Q.isEmpty(paraObj.async)) {
		paraObj.async = false;
	}
	if (!Q.isEmpty(paraObj.data)) {
		paraObj.data = JSON.stringify(paraObj.data);
	}
	if (Q.isEmpty(paraObj.error)) {
		paraObj.error = function(data) {
			console.log("错误：" + data);
		};
	}
	if (Q.isEmpty(paraObj.success)) {
		paraObj.success = function(data) {
			console.log(data);
			resData = Q.getData(data);
		}
	}
	if (Q.isEmpty(paraObj.complete)) {
		paraObj.complete = function(data) {
			// alert("complete:"+data);
		};
	}
	$.ajax(paraObj);
	return resData;
}
/**
 * 判断字符串是否为空
 */
Q.isEmpty = function(data) {
	return data == null || data == undefined || data == "";
}
/**
 * 判断字符串是否为空list
 */
Q.isEmptyList = function(list) {
	return list == null || list == undefined || list.length == 0;
}
/**
 * null和undefined字符串转为空字符串
 */
Q.emptyToStr = function(data) {
	return data == null || data == undefined ? "" : data;
}

/**
 * 取返回值
 */
Q.getData = function(data) {
	if (data == null || data == undefined) {
		return null;
	}
	if("SYS_W_001"==data){
		alert("授权信息失效，请重新登陆");
		window.location.href="/pages/sign-in.html";
		return;
	}
	if (data.code != "SYS_S_000") {
		alert(data.desc);
		return;
	}
	return data.output;
}

/**
 * 获取地址栏参数
 */
Q.getParam = function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}