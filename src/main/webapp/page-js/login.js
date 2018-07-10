var login = new Vue({
	el: '#loginForm',
	data:{
		username: '',
		password: '',
		dataLoadingText: 'loading...',
		isShow: 'none',
		errorMsg: '',
		oldPassword: '',
		newPassword: '',
		retypeNewPassword: '',
	},
	methods: {
		userLogin: function(){
			//init
			errorMsg = '';
			/*$('#loginErrorMsg').css("display","none");*/
			this.isShow = 'none';
			
			if (this.username==null||this.username==''){
				this.errorMsg = 'User ID can not be empty';
				/*$('#loginErrorMsg').css("display","block");*/
				this.isShow = 'block';
				return;
			}
			if (this.password==null||this.password==''){
				this.errorMsg = 'Password can not be empty';
				/*$('#loginErrorMsg').css("display","block");*/
				this.isShow = 'block';
				return;
			}
			if (this.password==this.username){
				this.errorMsg = 'Password cannot be the same as User ID';
				/*$('#loginErrorMsg').css("display","block");*/
				this.isShow = 'block';
				return;
			}
			if (this.password.length<8){
				this.errorMsg = 'Password can not be less than 8 characters';
				/*$('#loginErrorMsg').css("display","block");*/
				this.isShow = 'block';
				return;
			}
			if (this.password.length>18){
				this.errorMsg = 'Maximum entry for password is 18 characters in length';
				/*$('#loginErrorMsg').css("display","block");*/
				this.isShow = 'block';
				return;
			}
			var patt1 = new RegExp(/\s+/g);
			if (patt1.test(this.password)){
				this.errorMsg = 'Password should not contain white spaces';
				/*$('#loginErrorMsg').css("display","block");*/
				this.isShow = 'block';
				return;
			}	
				
			$('#subLoginBtn').button('loading');
			
			var user = {
				userId: this.username,
				password: this.password
			};
			
			AdminUtil.ajax('/user/login/password', 'GET', 'json', '', user, false, function(data){
				AdminUtil.setStorage("user",data.data);
				$('#subLoginBtn').button('reset');
				
				if(data.data.firstLogin=='N'){
					window.location.href = 'index';
				}
				
				if(data.data.firstLogin=='Y'){
					login.errorMsg = ''
					login.oldPassword= ''
					login.newPassword= ''
					login.retypeNewPassword= ''
					$('#changePasswordModal').modal('show');
				}
				
			},function(data){
				login.errorMsg = data.msg;
				$('#subLoginBtn').button('reset');
				$('#loginErrorMsg').css("display","block");
			});
		},
		changePasswordAction : function(){
			
			var patt1 = new RegExp(/\s+/g);
			if (patt1.test(this.newPassword)){
				AdminUtil.alert('Password should not contain white spaces');
				return;
			}	
			if (this.oldPassword==null||this.oldPassword==''){
				AdminUtil.alert('Old password can not be empty');
				return;
			}
			if (this.newPassword==null||this.newPassword==''){
				AdminUtil.alert('New password can not be empty');
				return;
			}
			if (this.newPassword.length<8){
				AdminUtil.alert('New password can not be less than 8 characters');
				return;
			}
			if (this.newPassword.length>18){
				AdminUtil.alert('Maximum entry for new password is 18 characters in length');
				return;
			}
			if(this.oldPassword!=this.password){
				AdminUtil.alert('Old password is wrong');
				return
			}
			if (this.newPassword==this.username){
				AdminUtil.alert('New password cannot be the same as User ID');
				return;
			}
			if (this.newPassword==this.password){
				AdminUtil.alert('New password cannot be the same as old password');
				return;
			}
			if(this.newPassword!=this.retypeNewPassword){
				AdminUtil.alert('Your Retype new password and new password do not match');
				return
			}
   		 	var $btn = $("#changePasswordButton"); 
   			
   		 	$btn.attr("disabled", true)
			
			var data = {
				userId: this.username,
				password: this.newPassword
			};
			
			AdminUtil.ajax("/user/firstChangePassword","POST","json",AdminUtil.formContentType,data,false,function(data){
				
//				var data = {
//						userId: login.username,
//						password: login.newPassword
//					};
					
//					AdminUtil.ajax("/user/login/password","GET","json",AdminUtil.formContentType,data,false,function(data){
//						AdminUtil.setStorage("user",data.data);
//						
//						$btn.attr("disabled", false)
//						AdminUtil.alertSuccess("Success!Automatically login in 5 seconds");
//			   			setTimeout(function(){
//			   				window.location.href = 'index';
//			   			},3000);
//					
//					},function(data){
//						$btn.attr("disabled", false);
//						AdminUtil.alert(data.msg);
//						$('#subLoginBtn').button('reset');
//						$('#loginErrorMsg').css("display","block");
//					});
				
				AdminUtil.alertSuccess("Change password successfully, please use your new password to login IMS Admin Console.");
				$('#changePasswordModal').modal('hide');
				
			},function(data){
				$btn.attr("disabled", false);
				AdminUtil.alert(data.msg);
				$('#subLoginBtn').button('reset');
			});
			
		},

	}
});