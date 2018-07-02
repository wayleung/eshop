var adminApp = new Vue({
	el: '#wrapper',
	data: {
		user: '',
		app: {
			appsMaintaince: 'Apps Maintaince',
			addApps: 'Add Apps',
			appRule: 'App Receive Rule'
		},
		headTitle: {
			parentTitle: 'Apps maintaince',
			childListTitle: 'Add Apps'	
		},
  		editUserModel:{
   			id :"",
   			oldpassword : "",
   			checkpassword : "",
   			retypeNewPassword : "",
   			password :"",
   		},
	},
	methods: {
		getUserSession: function(){
			this.user = AdminUtil.getStorage("user");
		},
		showTitle: function(obj){
			
		},
   		openEditPasswordModal : function() {
   			adminApp.editUserModel.checkpassword = AdminUtil.getStorage("user").password;
   			adminApp.editUserModel.id = AdminUtil.getStorage("user").id;
   			$('#editPasswordModal').modal('show');
   		},
   		editPasswordAction : function() {
   			
   			var patt1 = new RegExp(/\s+/g);
			if (patt1.test(this.editUserModel.password)){
				AdminUtil.alert('Password should not contain white spaces');
				return;
			}	
			if (this.editUserModel.password==null||this.editUserModel.password==''){
				AdminUtil.alert('Password can not be empty');
				return;
			}
			if (this.editUserModel.password.length<8){
				AdminUtil.alert('Password can not be less than 8 characters');
				return;
			}
			if (this.editUserModel.password.length>18){
				AdminUtil.alert('Maximum entry for password is 18 characters in length');
				return;
			}
			if (this.editUserModel.password==this.user.userId){
				AdminUtil.alert('Password cannot be the same as ID');
				return;
			}
			if (this.editUserModel.password==this.editUserModel.oldpassword){
				AdminUtil.alert('New password cannot be the same as old password');
				return;
			}
			if (this.editUserModel.password!=this.editUserModel.retypeNewPassword){
				AdminUtil.alert('Your retype new password and new password do not match');
				return;
			}
   			
   		 	var $btn = $("#editPasswordButton"); 
   			
   		 	$btn.attr("disabled", true)
   			
   			var data = {
   		 		"id" : this.editUserModel.id,
   		 		"checkpassword" : this.editUserModel.checkpassword,
   		 		"oldpassword" : this.editUserModel.oldpassword,
   				"password" : this.editUserModel.password,
   			}
   			AdminUtil.ajax("/user/updatePassword","POST","json",AdminUtil.formContentType,data,false,function(data){
   				
   				$('#editPasswordModal').modal('hide')
   				
   				adminApp.editUserModel.checkpassword = "";
   				adminApp.editUserModel.oldpassword = "";
   				adminApp.editUserModel.password = "";
   				
   				AdminUtil.alertSuccess("Change password successfully, please use your new password to login IMS Admin Console.Forwarding to login page in 5 seconds.");
   				
   				setTimeout(function(){
   					//window.location.href = '/application/ims/user/logout';
   					window.location.href = rootPath + "/user/logout";
   				},3000);
   				
   			}, function(data){
   				if(!data.success){
   					$btn.attr("disabled", false)
   					AdminUtil.alert(data.msg);
   					return
   				}
   			});	
   		},
	}
});