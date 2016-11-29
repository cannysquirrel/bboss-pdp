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

package com.frameworkset.platform.admin.service;

import com.frameworkset.platform.admin.entity.*;
import com.frameworkset.util.ListInfo;
import com.frameworkset.common.poolman.ConfigSQLExecutor;
import org.apache.log4j.Logger;
import java.util.List;
import com.frameworkset.orm.transaction.TransactionManager;

/**
 * <p>Title: SmOrganizationServiceImpl</p> <p>Description: 机构管理管理业务处理类 </p>
 * <p>bboss</p> <p>Copyright (c) 2007</p> @Date 2016-11-28 16:33:16 @author
 * yinbp @version v1.0
 */
public class SmOrganizationServiceImpl implements SmOrganizationService {

	private static Logger log = Logger
			.getLogger(com.frameworkset.platform.admin.service.SmOrganizationServiceImpl.class);

	private ConfigSQLExecutor executor;
	public void addSmOrganization(SmOrganization smOrganization) throws SmOrganizationException {
		// 业务组件
		try {
			executor.insertBean("addSmOrganization", smOrganization);
		} catch (Throwable e) {
			throw new SmOrganizationException("add SmOrganization failed:", e);
		}

	}
	public void deleteSmOrganization(String orgId) throws SmOrganizationException {
		try {
			executor.delete("deleteByKey", orgId);
		} catch (Throwable e) {
			throw new SmOrganizationException("delete SmOrganization failed::orgId=" + orgId, e);
		}

	}
	public void deleteBatchSmOrganization(String... orgIds) throws SmOrganizationException {
		TransactionManager tm = new TransactionManager();
		try {
			tm.begin();
			executor.deleteByKeys("deleteByKey", orgIds);
			tm.commit();
		} catch (Throwable e) {

			throw new SmOrganizationException("batch delete SmOrganization failed::orgIds=" + orgIds, e);
		} finally {
			tm.release();
		}

	}
	public void updateSmOrganization(SmOrganization smOrganization) throws SmOrganizationException {
		try {
			executor.updateBean("updateSmOrganization", smOrganization);
		} catch (Throwable e) {
			throw new SmOrganizationException("update SmOrganization failed::", e);
		}

	}
	public SmOrganization getSmOrganization(String orgId) throws SmOrganizationException {
		try {
			SmOrganization bean = executor.queryObject(SmOrganization.class, "selectById", orgId);
			return bean;
		} catch (Throwable e) {
			throw new SmOrganizationException("get SmOrganization failed::orgId=" + orgId, e);
		}

	}
	public ListInfo queryListInfoSmOrganizations(SmOrganizationCondition conditions, long offset, int pagesize)
			throws SmOrganizationException {
		ListInfo datas = null;
		try {
			datas = executor.queryListInfoBean(SmOrganization.class, "queryListSmOrganization", offset, pagesize,
					conditions);
		} catch (Exception e) {
			throw new SmOrganizationException("pagine query SmOrganization failed:", e);
		}
		return datas;

	}
	public List<SmOrganization> queryListSmOrganizations(SmOrganizationCondition conditions)
			throws SmOrganizationException {
		try {
			List<SmOrganization> beans = executor.queryListBean(SmOrganization.class, "queryListSmOrganization",
					conditions);
			return beans;
		} catch (Exception e) {
			throw new SmOrganizationException("query SmOrganization failed:", e);
		}

	}
	/** (non-Javadoc)
	 * @see com.frameworkset.platform.admin.service.SmOrganizationService#getChildres(java.lang.String)
	 */
	@Override
	public List<SmOrganization> getChildren(String parent) {
		if(parent.equals("#"))
		{
			parent = "0";
		}
		try {
			List<SmOrganization> beans = executor.queryList(SmOrganization.class, "selectChildren",
					parent);
			return beans;
		} catch (Exception e) {
			throw new SmOrganizationException("query Children SmOrganization failed:", e);
		}
	}
}