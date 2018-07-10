var ajaxReqAbortObj;

var AdminUtil = {
		
	formContentType: "application/x-www-form-urlencoded; charset=UTF-8",	
	
	jsonContentType: "application/json",
	
	emailReg : /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/, //E-mail verification	
	    
	ajax : function(api, type, dataType, contentType, datas ,isLoading,successCB,errorCB) {
		if(isLoading) {
			//$.showLoading("Loading");
		}
		var args = arguments;
		var url =  rootPath + api;
		var type = (type == null || type == "") ? "POST" : type;
		var dataType = (dataType == null || dataType == "") ? "json" : dataType;
		var async = (async == null || async == "" || $.type(async) === "boolean") ? true : async;
		var contentType = (contentType == null || contentType == "") ? AdminUtil.formContentType : contentType;
		
		ajaxReqAbortObj = $.ajax({
			url: url,
			type: type,
			timeout : 60 * 1000,
			data: datas,
			dataType: dataType,   //"json"
			contentType: contentType,
			cache:false,
			async: true,
			beforeSend: function(xhr) {
			
			},
			success: function(data) {
				if(isLoading) {
					//$.hideLoading();
				}
				
				if(data.code=="E9000A") {
					AdminUtil.alertInterceptor("Your session was timeout since it had been inactive for 60 minutes. Please login again.",function(){
						top.location.href = "/application/ims/admin/login.html";
		        	});
				}
				if(data.success == true){  //success
					successCB && successCB.apply(this, [data, args, this]);
					return;
				} else if(data.success == false) {  //error
					errorCB && errorCB.apply(this, [data, args, this]);
					return;
				}
				
			},
			error: function(data){

				//$.hideLoading();
				errorCB && errorCB.apply(this, [data, args, this]);
			}
		});
	},
	
	getHostPath : function() {
		var curWwwPath = window.document.location.href;
		var pathName = window.document.location.pathname;
		var pos = curWwwPath.indexOf(pathName);
		var localhostPath = curWwwPath.substring(0, pos);
		var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1); 
		return localhostPath + projectName;
	},
	setStorage : function(key,value) {

		value = JSON.stringify(value);
		localStorage.setItem(key, value);
	},
	getStorage : function(key) {
		var value = localStorage.getItem(key);
		if(value) {
			return JSON.parse(value);
		}
	},
	deleteStorage: function(key) {
		localStorage.removeItem(key);
	},
	browser : {
		 versions:function(){
		        var u = navigator.userAgent, app = navigator.appVersion;
		        return {
		            trident: u.indexOf('Trident') > -1, //IE内核
		            presto: u.indexOf('Presto') > -1, //opera内核
		            webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
		            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,//火狐内核
		            mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
		            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
		            android: u.indexOf('Android') > -1 || u.indexOf('Adr') > -1, //android终端
		            iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
		            iPad: u.indexOf('iPad') > -1, //是否iPad
		            webApp: u.indexOf('Safari') == -1, //是否web应该程序，没有头部与底部
		            weixin: u.indexOf('MicroMessenger') > -1, //是否微信 （2015-01-22新增）
		            qq: u.match(/\sQQ/i) == " qq" //是否QQ
		        };
		    }(),
		    language:(navigator.browserLanguage || navigator.language).toLowerCase()
	},
	getQueryString : function(name) {
		 var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	     var r = window.location.search.substr(1).match(reg);
	     if(r!=null)return  unescape(r[2]); return null;
	},
	alertSuccess : function(successMsg) {
		$(".confirm").attr("disabled", false);
		swal({
            title: successMsg,
            type: "success"
        });
	},
	alert : function(alertMsg) {
		$(".confirm").attr("disabled", false);
		swal({
			title : "",
            type: "warning",
            text: alertMsg
        });
	},
	alertConfirm :function(alertMsg,cb) {
		$(".confirm").attr("disabled", false);
		swal({
            title : "",
            text: alertMsg,
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes",
            cancelButtonText: "Cancel",
            closeOnConfirm: false,
            closeOnCancel: true
        },
        function (isConfirm) {
        	$(".confirm").attr("disabled", true);
        	$(".confirm").text("loading...");
            if (isConfirm) {
            	cb && cb();
            } 
        });
	},
	alertInterceptor :function(alertMsg,cb) {
		$(".confirm").attr("disabled", false);
		swal({
			title : "",
			text: alertMsg,
			type: "warning",
			confirmButtonColor: "#DD6B55",
			confirmButtonText: "OK",
			closeOnConfirm: false,
			closeOnCancel: true
		},
		function (isConfirm) {
			if (isConfirm) {
				cb && cb();
			} 
		});
	},
	formatTime : function(inputTime) {
		inputTime = parseInt(inputTime);
		var date = new Date(inputTime);  
	    var y = date.getFullYear();    
	    var m = date.getMonth() + 1;    
	    m = m < 10 ? ('0' + m) : m;    
	    var d = date.getDate();    
	    d = d < 10 ? ('0' + d) : d;    
	    var h = date.getHours();  
	    h = h < 10 ? ('0' + h) : h;  
	    var minute = date.getMinutes();  
	    var second = date.getSeconds();  
	    minute = minute < 10 ? ('0' + minute) : minute;    
	    second = second < 10 ? ('0' + second) : second;   
	    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;  
	},
	getCilckLinkTitle: function(obj){
		//同级的标题的class="active" 移除
		$(obj).parent().siblings().removeClass("active");
		$(obj).parent().siblings().children().removeClass("active");
		$(obj).parent().siblings().children('ul').children().removeClass('active')
		$(obj).parent().parent().parent().siblings().removeClass("active");
		
		$(obj).parent().parent().parent().addClass("active");
		$(obj).parent().addClass("active");
		//点击左边的链接栏的时候显示对应的text
		/*adminApp.headTitle.parentTitle = $($($(obj).parents('#nav-link')).prop('firstChild')).find('span').text();*/
    	/*adminApp.headTitle.childListTitle = $(obj).parent().text();*/
		adminApp.headTitle.parentTitle = $(obj).parent().parent().prev().text();
		adminApp.headTitle.childListTitle = $(obj).parent().text()
	},
	isNotData: function(id) {
		$('#'+id).append('<div class="infont col-md-3 col-sm-4" style="width:100%;padding-top: 3px;"><a href="#" style="display: block; color: #d4174a;"><i class="fa fa-frown-o" style="display: block;color: #d31145;"></i><p style="font-size: 15px;">No data</p></a></div>');
	},
	isNotDataTemplate: function() {
		Vue.component('not-data',{
			props: ['show'],
			template: '<div class="infont col-md-3 col-sm-4" style="width:100%;padding-top: 3px;" v-show="show"><a href="#" style="display: block; color: #d4174a;"><i class="fa fa-frown-o" style="display: block;color: #d31145;"></i><p style="font-size: 15px;">No data</p></a></div>'
		});
	}
};


var rootPath = AdminUtil.getHostPath();

//var rootPath = "http://10.65.3.171:8089/aia6/";