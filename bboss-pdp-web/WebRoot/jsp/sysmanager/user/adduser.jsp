<%@ page session="false" language="java"
	contentType="text/html; charset=utf-8"%>

	<!-- BEGIN FORM-->
	<form action="#" class="form-horizontal" id="form_sys_adduser">
		<div class="form-body">
		<input type="hidden" class="form-control" name="departId"  id="departId" value="${departId }">
		
		<div class="row">
			<div class="col-md-6" >
				<div class="form-group form-md-line-input">
					<label class="col-md-3 control-label" for="form_control_1">账号
						<span class="required">*</span>
					</label>
					 
					<div class="col-md-9">
						<div class="input-group">
							<div class="input-group-control">
								<input type="text" class="form-control" name="userName"
									placeholder=""  autocomplete="off">
								<div class="form-control-focus"></div>
								<span class="help-block">请输入账号</span>	
							</div>
							<span class="input-group-btn btn-right">
								<button type="button" class="btn btn-xs green-haze  "
									  aria-expanded="false">
									检查账号
								</button>
								 
							</span>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group form-md-line-input">
					<label class="col-md-3 control-label" for="form_control_1">中文名
						<span class="required">*</span>
					</label>
					<div class="col-md-9">
						<input type="text" class="form-control" placeholder="" name="userRealname"  autocomplete="off">
						<div class="form-control-focus"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6" >
				<div class="form-group form-md-line-input">
					<label class="col-md-3 control-label" for="form_control_1">口令
						<span class="required">*</span>
					</label>
					<div class="col-md-9">
						<input type="password" class="form-control" placeholder="123456" name="userPassword" id="userPassword" autocomplete="off" value="123456">
						<div class="form-control-focus"></div>
						<span class="help-block passwordhelp" ></span>	
					</div>
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group form-md-line-input">
					<label class="col-md-3 control-label" for="form_control_1">确认口令
						<span class="required">*</span>
					</label>
					<div class="col-md-9">
						<input type="password" class="form-control" placeholder="123456"
							name="userPasswordSecond" id="userPasswordSecond" autocomplete="off"  value="123456">
						<div class="form-control-focus"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6" >
				<div class="form-group form-md-line-input">
					<label class="col-md-3 control-label" for="form_control_1">口令有效期(天)
						<span class="required">*</span>
					</label>
					<div class="col-md-9">
						<input type="text" class="form-control" placeholder="" name="passwordDualtime" value="-1">
						<div class="form-control-focus"></div>
					</div>
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group form-md-line-input">
					<label class="col-md-3 control-label" for="form_control_1">身份证
						<span class="required">*</span>
					</label>
					<div class="col-md-9">
						<input type="text" class="form-control" placeholder=""
							name="userIdcard">
						<div class="form-control-focus"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6" >		
				<div class="form-group form-md-line-input">
					<label class="col-md-3 control-label" for="form_control_1">手机号码
						<span class="required">*</span>
					</label>
					<div class="col-md-9">
						<div class="input-icon">
							<input type="text" class="form-control" placeholder=""
								name="userMobiletel1">
							<div class="form-control-focus"></div>
							<i class="fa fa-bell-o"></i>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-6" >		
				<div class="form-group form-md-line-input">
					<label class="col-md-3 control-label" for="form_control_1">用户状态
						<span class="required">*</span>
					</label>
					<div class="col-md-9">
						<div class="input-icon right">
							<select class="form-control" name="userIsvalid">
								<option value="2">开通</option>
                                <option value="1">申请</option>
                                <option value="3">停用</option>
                                <option value="0">删除</option>
							</select>
							<div class="form-control-focus"></div>
							 
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6" >							
				<div class="form-group form-md-line-input">
					<label class="col-md-3 control-label" for="form_control_1">邮箱</label>
					<div class="col-md-9">
						<div class="input-group">
							<span class="input-group-addon"> <i class="fa fa-envelope"></i>
							</span> <input type="text" class="form-control" name="userEmail"
								placeholder="Enter your email">
							<div class="form-control-focus"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-6" >
				<div class="form-group form-md-line-input">
					<label class="col-md-3 control-label" for="form_control_1">住址</label>
					<div class="col-md-9">
						<div class="input-group">
							<input type="text" class="form-control" name="userAddress"
								placeholder=""> <span class="input-group-addon">
								<i class="fa fa-i-cursor"></i>
							</span>
							<div class="form-control-focus"></div>
						</div>
					</div>
				</div>
			</div>
		</div>	
		<div class="row">
			<div class="col-md-6" >		
				<div class="form-group form-md-line-input">
					<label class="col-md-3 control-label" for="form_control_1">工号</label>
					
					<div class="col-md-9">
						<div class="input-group">
							<div class="input-group-control">
								<input type="text" class="form-control" name="userWorknumber"
									placeholder=""  autocomplete="off">
								<div class="form-control-focus"></div>
								<span class="help-block">请输入工号</span>	
							</div>
							<span class="input-group-btn btn-right">
								<button type="button" class="btn btn-xs green-haze  "
									  aria-expanded="false">
									检查工号
								</button>
								 
							</span>
						</div>
						
					</div>
				</div>
			</div>
			<div class="col-md-6" >	
				<div class="form-group form-md-line-input">
					<label class="col-md-3 control-label" for="form_control_1">性别</label>
					<div class="col-md-9">
						<select class="form-control" name="userSex">
							<option value="M">男</option>
                                <option value="F">女</option>
                                <option value="-1">未知</option>
						</select>
						<div class="form-control-focus"></div>
					</div>
				</div>
			</div>
		</div>	
		<div class="row">
			<div class="col-md-6" >			
				<div class="form-group form-md-line-input">
					<label class="col-md-3 control-label" for="form_control_1">用户类型</label>
					<div class="col-md-9">
						<select class="form-control" name="userType">
							<option value="0">系统用户</option>
                                <option value="1">域用户</option>
                                <option value="2">第三方用户</option>
						</select>
						<div class="form-control-focus"></div>
					</div>
				</div>
			</div>
			<div class="col-md-6" >	
				<div class="form-group form-md-line-input">
					<label class="col-md-3 control-label" for="form_control_1">出生日期</label>
					<div class="col-md-9">
						<div class="input-group">
							<input type="text" class="form-control date-picker" name="userBirthday"
								placeholder="请输入出生日期"> <span class="input-group-addon">
								<i class="fa fa-calendar"></i>
							</span>
							<div class="form-control-focus"></div>
							 
						</div>
					</div>
				</div>
			</div>
				
				
				
			</div>
		</div>
		<div class="row">
			
			<div class="col-md-12" >		
				<div class="form-group form-md-line-input">
					<label class="col-md-3 control-label" for="form_control_1">备注</label>
					<div class="col-md-9">
						<textarea class="form-control" name="remark1" rows="3"></textarea>
						<div class="form-control-focus"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="form-actions">
			<div class="row">
				<div class="col-md-offset-3 col-md-9">
					<button type="button" id="sys_addUser_button" class="btn green">创建</button>
					<button type="reset" class="btn default">重置</button>
					<button type="reset" class="btn default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</form>
<script type="text/javascript">
	jQuery(document).ready(function() {
		SysUser.initAddUser();
		PlatformCommonUtils.initPickers();
		
	});
</script>

