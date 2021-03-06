/**
 *  Copyright 2008-2010 biaoping.yin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.frameworkset.platform.admin.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.frameworkset.event.Event;
import org.frameworkset.event.EventHandle;
import org.frameworkset.event.EventImpl;
import org.frameworkset.platform.common.DatagridBean;
import org.frameworkset.platform.config.ResourceInfoQueue;
import org.frameworkset.platform.config.model.ResourceInfo;
import org.frameworkset.platform.resource.ResourceManager;
import org.frameworkset.platform.security.AccessControl;
import org.frameworkset.platform.security.authorization.AuthRole;
import org.frameworkset.platform.security.event.ACLEventType;
import org.frameworkset.util.annotations.PagerParam;
import org.frameworkset.util.annotations.ResponseBody;
import org.frameworkset.web.servlet.ModelMap;

import com.frameworkset.platform.admin.entity.MoveinUserCondition;
import com.frameworkset.platform.admin.entity.SmUser;
import com.frameworkset.platform.admin.entity.SmUserCondition;
import com.frameworkset.platform.admin.entity.UserRole;
import com.frameworkset.platform.admin.service.RoleService;
import com.frameworkset.platform.admin.service.SmUserException;
import com.frameworkset.platform.admin.service.SmUserService;
import com.frameworkset.util.ListInfo;
import com.frameworkset.util.StringUtil;

/**
 * <p>Title: SmUserController</p> <p>Description: 用户管理管理控制器处理类 </p> <p>bboss</p>
 * <p>Copyright (c) 2007</p> @Date 2016-11-29 21:14:30 @author yinbp @version
 * v1.0
 */
public class SmUserController {

	private static Logger log = Logger.getLogger(SmUserController.class);

	private SmUserService smUserService;
	private RoleService roleService;
	private ResourceManager resourceManager = new ResourceManager();
	public String authmain(String userId,ModelMap model){
		if(StringUtil.isEmpty(userId) ){
			model.addAttribute("errorMessage", "请选择需要设置角色的用户!");
		}
		List<UserRole> userroles = roleService.getUserRoles(userId);
		model.addAttribute("userroles", userroles);
		model.addAttribute("userId", userId);
		
		//资源授权配置开始
		String curSystem = AccessControl.getAccessControl().getCurrentSystemID();
		ResourceInfoQueue resQueue = resourceManager.getResourceInfoQueue();
		List<ResourceInfo> resourceTypes = new ArrayList<ResourceInfo>();
		for (int i = 0; resQueue != null && i < resQueue.size(); i++) {
			ResourceInfo res = resQueue.getResourceInfo(i);
			// System.out.println("res.isAuto() = " + res.isAuto());
			// 判断当前系统模块；
			if (res.isUsed() && res.containSystem(curSystem)) {
				resourceTypes.add(res);

			}
		}
//		if(roleType.equals("role")){
			model.addAttribute("roleNeedGrantResource", true);
//			model.addAttribute("roleNeedSetUser", AdminUtil.roleNeedSetUser(roleName));
//			model.addAttribute("roleNeedSetUserMessage", AdminUtil.roleNeedSetUserMessage(roleName));
//			model.addAttribute("roleNeedGrantResourceMessage", AdminUtil.roleNeedGrantResourceMessage(roleName));
//		}
		model.addAttribute("resourceTypes", resourceTypes);
		if (resourceTypes.size() > 0) {
			model.addAttribute("resourceType", resourceTypes.get(0).getId());
			model.addAttribute("resourceName", resourceTypes.get(0).getName());
		}
		SmUser user = this.smUserService.getSmUser(userId);
		model.addAttribute("roleId", userId);
		model.addAttribute("roleName", user.getUserName());
		model.addAttribute("roleType", AuthRole.TYPE_USER);
		model.addAttribute("roleCName", user.getUserRealname());
		
		//资源授权配置结束
		
		return "path:authmain";
	}
	public @ResponseBody String saveRoleUsers(String userIds,String roleId){
		if(StringUtil.isEmpty(roleId))
		{
			return "请选择要设置用户的角色";
		}
		if(StringUtil.isEmpty(userIds))
		{
			return "请选择要授予角色的用户";
		}
		smUserService.saveRoleUsers(userIds,roleId,true);
		Event event = new EventImpl(userIds,
				ACLEventType.USER_ROLE_INFO_CHANGE);
		EventHandle.sendEvent(event);
		return "success";
	}
	public @ResponseBody String saveUserRoles(String userId,String roleIds){
		if(StringUtil.isEmpty(userId))
		{
			return "请选择要设置角色的用户";
		}
		smUserService.saveUserRoles(userId,roleIds);
		Event event = new EventImpl(userId,
				ACLEventType.USER_ROLE_INFO_CHANGE);
		EventHandle.sendEvent(event);
		return "success";
	}
	
	public @ResponseBody String saveMoveusers(String userIds,String fromDepartId,String toDepartId){
		if(StringUtil.isEmpty(fromDepartId) || StringUtil.isEmpty(toDepartId) || StringUtil.isEmpty(userIds)){
			return "请确保调出用户、调出部门、被调出部门都已经选中!";
		}
		if(fromDepartId.equals(toDepartId)){
			return "调出部门、被调出部门不能是同一个部门!";
		}
		this.smUserService.saveMoveusers( userIds, fromDepartId, toDepartId);
		return "success";
		
	}
	
	
	
	public String toMoveOutSmUser(String fromdepartId,ModelMap model){
		if(StringUtil.isEmpty(fromdepartId))
			model.addAttribute("errormsg","没有选择调入部门");
		model.addAttribute("fromdepartId",fromdepartId);
		return "path:toMoveOutSmUser";
	}
	public String toMoveInSmUser(String toDepartId,ModelMap model){
		if(StringUtil.isEmpty(toDepartId))
			model.addAttribute("errormsg","没有选择调出部门");
		model.addAttribute("toDepartId",toDepartId);
		return "path:toMoveInSmUser";
	}
	public @ResponseBody String addSmUser(SmUser smUser) {
		// 控制器
		try {
			smUserService.addSmUser(smUser);
			return "success";
		} catch (SmUserException e) {
			log.error("add SmUser failed:", e);
			return StringUtil.formatBRException(e);
		} catch (Throwable e) {
			log.error("add SmUser failed:", e);
			return StringUtil.formatBRException(e);
		}

	}
	public @ResponseBody String deleteSmUser(String userId) {
		try {
			smUserService.deleteSmUser(userId);
			return "success";
		} catch (SmUserException e) {
			log.error("delete SmUser failed:", e);
			return StringUtil.formatBRException(e);
		} catch (Throwable e) {
			log.error("delete SmUser failed:", e);
			return StringUtil.formatBRException(e);
		}

	}
	public @ResponseBody String deleteBatchSmUser(String userIds,String user_deltype) {
		try {
			if(StringUtil.isNotEmpty(userIds))
			{
				String[] uids = userIds.split(",");
				smUserService.deleteBatchSmUser(uids,user_deltype);
			}
			return "success";
		} catch (Throwable e) {
			log.error("delete Batch userIds failed:", e);
			return StringUtil.formatBRException(e);
		}

	}
	public @ResponseBody String updateSmUser(SmUser smUser) {
		try {
			smUserService.updateSmUser(smUser);
			return "success";
		} catch (Throwable e) {
			log.error("update SmUser failed:", e);
			return StringUtil.formatBRException(e);
		}

	}
	public String getSmUser(String userId, ModelMap model) throws SmUserException {
		try {
			SmUser smUser = smUserService.getSmUser(userId);
			model.addAttribute("smUser", smUser);
			return "path:getSmUser";
		} catch (SmUserException e) {
			throw e;
		} catch (Throwable e) {
			throw new SmUserException("get SmUser failed::userId=" + userId, e);
		}

	}
	public String queryListInfoSmUsers(SmUserCondition conditions,
			@PagerParam(name = PagerParam.SORT, defaultvalue = "USER_NAME") String sortKey,
			@PagerParam(name = PagerParam.DESC, defaultvalue = "false") boolean desc,
			@PagerParam(name = PagerParam.OFFSET) long offset,
			@PagerParam(name = PagerParam.PAGE_SIZE, defaultvalue = "10") int pagesize, ModelMap model)
					throws SmUserException {
		// Constant.component_type_actionimpl
		try {
			if (sortKey != null && !sortKey.equals("")) {
				conditions.setSortKey(sortKey);
				conditions.setSortDesc(desc);
			}
			String userMobiletel1 = conditions.getUserMobiletel1();
			if (userMobiletel1 != null && !userMobiletel1.equals("")) {
				conditions.setUserMobiletel1("%" + userMobiletel1 + "%");
			}
			String userName = conditions.getUserName();
			if (userName != null && !userName.equals("")) {
				conditions.setUserName("%" + userName + "%");
			}
			String userRealname = conditions.getUserRealname();
			if (userRealname != null && !userRealname.equals("")) {
				conditions.setUserRealname("%" + userRealname + "%");
			}
			String userWorknumber = conditions.getUserWorknumber();
			if (userWorknumber != null && !userWorknumber.equals("")) {
				conditions.setUserWorknumber("%" + userWorknumber + "%");
			}

			ListInfo smUsers = smUserService.queryListInfoSmUsers(conditions, offset, pagesize);
			model.addAttribute("smUsers", smUsers);
			return "path:queryListInfoSmUsers";
		} catch (SmUserException e) {
			throw e;
		} catch (Exception e) {
			throw new SmUserException("pagine query SmUser failed:", e);
		}

	}
	
	public @ResponseBody DatagridBean getDepartUsers(SmUserCondition conditions,
			int draw,int start,int length,Map<String,String> datas,ModelMap model)
					throws SmUserException {
		// Constant.component_type_actionimpl
		try {
//			if (sortKey != null && !sortKey.equals("")) {
//				conditions.setSortKey(sortKey);
//				conditions.setSortDesc(desc);
//			}
			String userMobiletel1 = conditions.getUserMobiletel1();
			if (userMobiletel1 != null && !userMobiletel1.equals("")) {
				conditions.setUserMobiletel1("%" + userMobiletel1 + "%");
			}
			String userName = conditions.getUserName();
			if (userName != null && !userName.equals("")) {
				conditions.setUserName("%" + userName + "%");
			}
			String userRealname = conditions.getUserRealname();
			if (userRealname != null && !userRealname.equals("")) {
				conditions.setUserRealname("%" + userRealname + "%");
			}
			String userWorknumber = conditions.getUserWorknumber();
			if (userWorknumber != null && !userWorknumber.equals("")) {
				conditions.setUserWorknumber("%" + userWorknumber + "%");
			}
//
			ListInfo smUsers = smUserService.getDepartUsers(conditions, start , length);
			DatagridBean data = new DatagridBean();
			data.setDraw(draw);
			if(smUsers != null)
			{
				if(smUsers.getDatas() != null)
					data.setData(smUsers.getDatas());
				else
					data.setData(new ArrayList());
				data.setRecordsFiltered((int)smUsers.getTotalSize());
				data.setRecordsTotal((int)smUsers.getTotalSize());
			}
			 
			
			return data;
		} catch (SmUserException e) {
			throw e;
		} catch (Exception e) {
			throw new SmUserException("pagine query SmUser failed:", e);
		}

	}
	 
	public String queryListSmUsers(SmUserCondition conditions, ModelMap model) throws SmUserException {
		try {
			String userMobiletel1 = conditions.getUserMobiletel1();
			if (userMobiletel1 != null && !userMobiletel1.equals("")) {
				conditions.setUserMobiletel1("%" + userMobiletel1 + "%");
			}
			String userName = conditions.getUserName();
			if (userName != null && !userName.equals("")) {
				conditions.setUserName("%" + userName + "%");
			}
			String userRealname = conditions.getUserRealname();
			if (userRealname != null && !userRealname.equals("")) {
				conditions.setUserRealname("%" + userRealname + "%");
			}
			String userWorknumber = conditions.getUserWorknumber();
			if (userWorknumber != null && !userWorknumber.equals("")) {
				conditions.setUserWorknumber("%" + userWorknumber + "%");
			}
			List<SmUser> smUsers = smUserService.queryListSmUsers(conditions);
			model.addAttribute("smUsers", smUsers);
			return "path:queryListSmUsers";
		} catch (SmUserException e) {
			throw e;
		} catch (Exception e) {
			throw new SmUserException("query SmUser failed:", e);
		}

	}
	public String toUpdateSmUser(String userId, ModelMap model) throws SmUserException {
		try {
			SmUser smUser = smUserService.getSmUser(userId);
			model.addAttribute("smUser", smUser);
			return "path:updateSmUser";
		} catch (SmUserException e) {
			throw e;
		} catch (Throwable e) {
			throw new SmUserException("get SmUser failed::userId=" + userId, e);
		}

	}
	public String toAddSmUser(String departId,ModelMap model) {
		model.addAttribute("departId",departId);
		return "path:addSmUser";
	}
	public String index() {
		return "path:index";

	}
	public @ResponseBody String updateUserStatus(String userId,String user_updatetype){
		try {
			smUserService.updateUserStatus(userId,user_updatetype);
			return "success";
		} catch (Throwable e) {
			log.error("updateUserStatus failed:", e);
			return StringUtil.formatBRException(e);
		}
		
	}
	public String tomodifyPassword(String userId,boolean frompersonal, ModelMap model){
		try {
			if(frompersonal)
				userId = AccessControl.getAccessControl().getUserID();
			SmUser smUser = smUserService.getSmUser(userId);
			model.addAttribute("smUser", smUser);
			model.addAttribute("frompersonal", frompersonal);
			return "path:modifypassword";
		} catch (SmUserException e) {
			throw e;
		} catch (Throwable e) {
			throw new SmUserException("get SmUser failed::userId=" + userId, e);
		}
	}
	public @ResponseBody String resetpassword(String userId){
		String currentUserId = AccessControl.getAccessControl().getUserID();
		if(currentUserId.equals(userId) || AccessControl.getAccessControl().checkPermission("user", "modifypassword","admin"))//判断用户是否有重置密码的权限
		{
			smUserService.resetpassword(userId);
			return "success";
		}
		else
		{
			return "无权修改用户口令!";
		}
	}
	public @ResponseBody String modifypassword(String userId,String newPassword,String newPasswordSecond,String oldPassword){
		String currentUserId = AccessControl.getAccessControl().getUserID();
		if(currentUserId.equals(userId) || AccessControl.getAccessControl().checkPermission("user", "modifypassword","admin"))//判断用户是否有重置密码的权限
		{
			
			return smUserService.modifypassword(  userId,  newPassword,  newPasswordSecond,  oldPassword);
		}
		else
		{
			return "无权修改用户口令!";
		}
	}
	
	public String toOrderSmUser(String departId, ModelMap model){
		model.addAttribute("departid", departId);
		List<SmUser> users = smUserService.getDepartUsers( departId);
		model.addAttribute("users", users);
		return "path:toOrderSmUser";
	}
	public @ResponseBody String saveSmUsersOrder(String[] userId){
		smUserService.saveSmUsersOrder(userId);
		return "success";
	}
	public String moveinuserlist(MoveinUserCondition condition,@PagerParam(name = PagerParam.SORT, defaultvalue = "USER_NAME") String sortKey,
			@PagerParam(name = PagerParam.DESC, defaultvalue = "false") boolean desc,
			@PagerParam(name = PagerParam.OFFSET) long offset,
			@PagerParam(name = PagerParam.PAGE_SIZE, defaultvalue = "10") int pagesize,ModelMap model){
		if(condition.getUserAttr() != null && !condition.getUserAttr().equals("")){
			condition.setUserAttr("%"+condition.getUserAttr()  + "%");
		}
		ListInfo users = smUserService.getMoveinUsers(  condition,  offset,pagesize);
		model.addAttribute("users", users);
		return "path:moveinuserlist";
	}
}