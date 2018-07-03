CREATE TABLE [dbo].[twc0_SYS_USER]  ( 
	[id]            	int IDENTITY(1,1) NOT NULL,
	[lan_id]        	nvarchar(40) NULL,
	[user_name]     	nvarchar(200) NOT NULL,
	[password]      	nvarchar(400) NOT NULL,
	[status]        	char(1) NOT NULL,
	[is_super_admin]	char(1) NOT NULL,
	[unlock_time]   	datetime NULL,
	[lock_flag]     	varchar(10) NULL 
	)
GO
SET IDENTITY_INSERT [dbo].[twc0_SYS_USER] ON

GO

INSERT INTO [dbo].[twc0_SYS_USER]([id], [lan_id], [user_name], [password], [status], [is_super_admin], [unlock_time], [lock_flag])
  VALUES(2, NULL, N'EMKH036', N'215874d9997143478ed34c92115270f16889a99e9231e99e93f4368fd105f50b', 'A', 'Y', NULL, NULL)
GO
INSERT INTO [dbo].[twc0_SYS_USER]([id], [lan_id], [user_name], [password], [status], [is_super_admin], [unlock_time], [lock_flag])
  VALUES(3, NULL, N'ASNPGTL', N'29372a01fae607ad30689922af8dc0f4bdbbeb4e784e99b5b90bea80f247f85b', 'A', 'N', NULL, NULL)
GO
INSERT INTO [dbo].[twc0_SYS_USER]([id], [lan_id], [user_name], [password], [status], [is_super_admin], [unlock_time], [lock_flag])
  VALUES(4, NULL, N'admin', N'ab38eadaeb746599f2c1ee90f8267f31f467347462764a24d71ac1843ee77fe3', 'A', 'Y', '20180301 11:48:47.093', 'N')
GO
INSERT INTO [dbo].[twc0_SYS_USER]([id], [lan_id], [user_name], [password], [status], [is_super_admin], [unlock_time], [lock_flag])
  VALUES(8, NULL, N'zzp', N'135a41351615b09af002c0288c5ff6e73be995ee303ab978d200fc125e54ab02', 'A', 'Y', NULL, NULL)
GO
INSERT INTO [dbo].[twc0_SYS_USER]([id], [lan_id], [user_name], [password], [status], [is_super_admin], [unlock_time], [lock_flag])
  VALUES(19, NULL, N'test001', N'0f6b1fcde8810109bf7392902dfdb48d0e84baa0879ef06782bbf7dfaf62604c', 'A', 'N', NULL, NULL)
GO
INSERT INTO [dbo].[twc0_SYS_USER]([id], [lan_id], [user_name], [password], [status], [is_super_admin], [unlock_time], [lock_flag])
  VALUES(21, NULL, N'aaron', N'c4318372f98f4c46ed3a32c16ee4d7a76c832886d887631c0294b3314f34edf1', 'A', 'N', '20180208 23:55:28.613', 'Y')
GO
INSERT INTO [dbo].[twc0_SYS_USER]([id], [lan_id], [user_name], [password], [status], [is_super_admin], [unlock_time], [lock_flag])
  VALUES(22, NULL, N'zhangsan', N'72a37acac9a9bf449ce4f5a848cae19114228e8972f2b124227d332deb4c514a', 'A', 'N', '20180208 10:49:54.0', 'N')
GO
INSERT INTO [dbo].[twc0_SYS_USER]([id], [lan_id], [user_name], [password], [status], [is_super_admin], [unlock_time], [lock_flag])
  VALUES(23, NULL, N'aaaa', N'3f21a8490cef2bfb60a9702e9d2ddb7a805c9bd1a263557dfd51a7d0e9dfa93e', 'A', 'N', NULL, NULL)
GO
INSERT INTO [dbo].[twc0_SYS_USER]([id], [lan_id], [user_name], [password], [status], [is_super_admin], [unlock_time], [lock_flag])
  VALUES(25, N'asnpht6', N'test', N'test', 'A', 'N', '20180612 16:49:53.0', 'admin;user')
GO
INSERT INTO [dbo].[twc0_SYS_USER]([id], [lan_id], [user_name], [password], [status], [is_super_admin], [unlock_time], [lock_flag])
  VALUES(2055, NULL, N'user1', N'user1', 'A', 'Y', NULL, NULL)
GO
INSERT INTO [dbo].[twc0_SYS_USER]([id], [lan_id], [user_name], [password], [status], [is_super_admin], [unlock_time], [lock_flag])
  VALUES(2056, NULL, N'test01', N'test01', 'A', 'N', NULL, NULL)
GO
SET IDENTITY_INSERT [dbo].[twc0_SYS_USER] OFF

GO

