package com.ideassoft.crm.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Role;
import com.ideassoft.bean.RoleRelation;
import com.ideassoft.bean.Staff;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.bean.pageConfig.ModelConfig;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.page.PageBuilder;
import com.ideassoft.crm.service.RightService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class RightController {
	
	@Autowired
	private PageBuilder pageBuilder;

	@Autowired
	private RightService rightService;
    
    @RequestMapping("/roleRightEdit.do")
    public ModelAndView roleRightEdit(String roleId, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("page/dialog/roleRightEdit");
        //循环获得model里的各个子系统里模块总数
		Map<String, Integer> subSystemNum = new HashMap<String, Integer>();
		Map<String, ModelConfig> modelConfigs = pageBuilder.getModelConfigs();
		Iterator<Map.Entry<String, ModelConfig>> iter = modelConfigs.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, ModelConfig> entry = (Map.Entry<String, ModelConfig>) iter.next();
			ModelConfig tempModelConfig = entry.getValue();
			String subSyStemValue = tempModelConfig.getSubSystem();
			if (!subSystemNum.containsKey(subSyStemValue)) {
				subSystemNum.put(subSyStemValue, 1);
			} else {
				subSystemNum.put(subSyStemValue, subSystemNum.get(subSyStemValue) + 1);	
			}
		};
		mv.addObject("map", JSONUtil.fromObject(subSystemNum));
        mv.addObject("modelconfigs", pageBuilder.getModelConfigs());
        mv.addObject("systemfunctions", JSONObject.fromObject(
        		ReflectUtil.getVariableMap(CommonConstants.SystemFunctions.class)));
        mv.addObject("subSystemNames", JSONObject.fromObject(
        		ReflectUtil.getVariableMap(CommonConstants.SubSystemNames.class)));
        
        Role role = rightService.findRoleById(roleId);
        List<RoleRelation> relations = rightService.findRoleRelationByRoleId(roleId);
        mv.addObject("role", role);
        mv.addObject("relations", JSONArray.fromObject(relations));
        return mv;
    }
    
    @RequestMapping("/roleRightRelations.do")
    public void roleRightRelations(String role, String rights, HttpServletRequest request, 
    		HttpServletResponse response) throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		Staff staff = loginUser.getStaff();
		
    	org.json.JSONObject r = new org.json.JSONObject(role);
    	//delete RoleRelation
    	if (r.has("ROLEID")) {
        	rightService.removeRoleRelation(r.get("ROLEID").toString());
		}
    	//save Role
    	Role ro = (Role) ReflectUtil.setBean(r, "Role", r.has("ROLEID") ? null : "ROLEID", 
    			r.has("ROLEID") ? null : "SEQ_NEW_ROLE", null);
    	ro.setRecordUser(staff.getStaffId());
    	rightService.getHibernateTemplate().saveOrUpdate(ro);
    	
    	//save RoleRelation
    	Map<String, String> params = new HashMap<String, String>();
		params.put("ROLEID", ro.getRoleId());
		params.put("RECORDUSER", staff.getStaffId());
    	String keyConfig = "DATAID|SEQ_NEW_ROLERELATION";
    	List<Object> rrs = ReflectUtil.setBeansFromJsonArray(rights, "RoleRelation",true, keyConfig, params);
    	rightService.getHibernateTemplate().saveOrUpdateAll(rrs);
    	
    	JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
    }
    
    @RequestMapping("/roleRightDelete.do")
    public void roleRightDelete(String roleIds, HttpServletResponse response) throws Exception {
    	String[] ids = roleIds.split(",");
    	for (String id : ids) {
    		rightService.delete(rightService.findById(Role.class, id));
    		rightService.removeRoleRelation(id);
		}
    	JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
    }
    
}



