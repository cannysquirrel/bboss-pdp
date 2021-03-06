<%@ page session="false" language="java"
	contentType="text/html; charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/tld/admin-taglib.tld" prefix="admin"%>

<table
	class="table table-striped table-hover table-bordered table-dictitemlist">
	<thead>
		<tr>
			<th width="5%" ><input type="checkbox" class="checkall"
				onClick="checkAll('.table-dictitemlist .checkall','.table-dictitemlist .checkone')" /></th>
			<th>名称</th>
			<th>值</th>
		</tr>
	</thead>
	<tbody>
		<pg:equal actual="${actiontype }" value="view" evalbody="true">
			<pg:yes>
				<pg:list requestKey="paramsList">
					
					<tr>
						<td  width="5%" ><input type="checkbox" name="paramId" class="checkone"
							value="<pg:cell colName='paramId' />"
							onClick="checkOne('.table-dictitemlist .checkall','.table-dictitemlist .checkone')" /></td>
						<td><p class="form-control-static"> <pg:cell colName="name" /> </p></td>
						<td><p class="form-control-static"> <pg:cell colName="value" /> </p></td>
		
					</tr>
				</pg:list>
			</pg:yes>
			<pg:no>
				<pg:list requestKey="paramsList">
					
					<tr>
						<td  width="5%" ><pg:rowid increament="1"/><input type="checkbox" name="paramId" class="checkone"
							value="<pg:cell colName='paramId' />"
							onClick="checkOne('.table-dictitemlist .checkall','.table-dictitemlist .checkone')" /></td>
						<td><div class="form-group form-md-line-input">
								<div class="col-md-12">
		
									<div class="input-icon right">
										<input type="text" class="form-control  input-xs"
											placeholder="名称" name="name" value="<pg:cell colName='name' />">
										<div class="form-control-focus"></div>
		
									</div>
								</div>
		
							</div></td>
						<td><div class="form-group form-md-line-input">
								<div class="col-md-12">
		
									<div class="input-icon right">
										<input type="text" class="form-control  input-xs"
											placeholder="值" name="value"
											value="<pg:cell colName='value' />">
										<div class="form-control-focus"></div>
		
									</div>
								</div>
		
							</div></td>
		
					</tr>
				</pg:list>
			</pg:no>
		</pg:equal>
	</tbody>
</table>
<script type="text/javascript">
	jQuery(document).ready(function() {
		<pg:notequal actual="${actiontype }" value="view" >
		SysDict.initDictItemOrderTable();
		</pg:notequal>
		
	});
</script>