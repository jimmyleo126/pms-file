package com.ideassoft.crm.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.SmsSendLog;
import com.ideassoft.bean.SmsTemplate;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.constants.CommonConstants;
import com.ideassoft.core.notifier.service.impl.NotifyService;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.service.ManageService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;

@Controller
public class ManageController {

	@Autowired
	NotifyService notifyService;
	@Autowired
	ManageService manageService;

	@RequestMapping("/turnToSmsTemplateAddPage.do")
	public ModelAndView turnToSmsTemplateAddPage(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		// 查找所有的门店传递到页面上去
		List<?> branchList = this.manageService.queryAllOfBranch();
		List<?> templateTypeList = this.manageService.queryAllOfTemplateType("SMS_TYPE");
		mv.addObject("branchList", branchList);
		mv.addObject("templateTypeList", templateTypeList);
		mv.setViewName("page/crm/message/addSmsTemplates");
		return mv;
	}

	@RequestMapping("/saveNewTemplate.do")
	public void saveNewTemplate(HttpServletRequest request, HttpServletResponse response, SmsTemplate smsTemplate)
			throws Exception {
		String templateId = this.manageService.getSequence("SEQ_SMSTEMPLATES_ID", null);
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String recordUser = loginUser.getStaff().getStaffId();
		smsTemplate.setRecordUser(recordUser);
		smsTemplate.setTemplateId(templateId);
		this.manageService.save(smsTemplate);
		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
	}

	@RequestMapping("/turnToalterSmsTemplatePage.do")
	public ModelAndView turnToalterSmsTemplatePage(HttpServletRequest request, String templateId) throws Exception {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/crm/message/alterSmsTemplate");
		// 将数据库当前templateId的记录加载到页面
		List<?> templateInfo = this.manageService.findByProperties(SmsTemplate.class, "templateId", templateId);
		mv.addObject("templateInfo", templateInfo);
		// 将页面下拉框内容加载
		List<?> branchList = this.manageService.queryAllOfBranch();
		List<?> templateTypeList = this.manageService.queryAllOfTemplateType("SMS_TYPE");
		mv.addObject("branchList", branchList);
		mv.addObject("templateTypeList", templateTypeList);
		return mv;
	}

	@RequestMapping("/alterSmsTemplate.do")
	public void alterSmsTemplate(HttpServletRequest request, HttpServletResponse response, SmsTemplate smsTemplate)
			throws Exception {
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		String recordUser = loginUser.getStaff().getStaffId();
		smsTemplate.setRecordUser(recordUser);
		smsTemplate.setRecordTime(new Date());
		this.manageService.update(smsTemplate);
		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
	}

	@RequestMapping("/turnToSmsShowDetailPage.do")
	public ModelAndView turnToSmsShowDetailPage(HttpServletRequest request, String templateId) throws Exception {
		List<?> branchList = this.manageService.queryAllOfBranch();
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/crm/message/querySmsInfoDetail");
		mv.addObject("templateId", templateId);
		mv.addObject("branchList", branchList);
		return mv;
	}

	@RequestMapping("/querySmsInDetail.do")
	public void querySmsInDetail(HttpServletRequest request, HttpServletResponse response, String templateId,
			String queryRecipentNo, String querySendTimeStart, String querySendTimeEnd, String queryBranchId,
			String queryStatus) throws Exception {
		Pagination pagination;
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		pagination = new Pagination(pageNum, rows);
		List<?> result = this.manageService.querySmsInDetailByCon(templateId, queryRecipentNo, querySendTimeStart,
				querySendTimeEnd, queryBranchId, queryStatus, pagination);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));
	}
	
	@RequestMapping("/selectSmsContent.do")
	public ModelAndView selectSmsContent(HttpServletRequest request, String dataId) throws Exception {
		List<?> templateInfo = this.manageService.findByProperties(SmsSendLog.class, "dataId", dataId);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/crm/message/querySmsContent");
		mv.addObject("templateInfo", templateInfo);
		return mv;
	}
	
	@RequestMapping("/testtest.do")
	public ModelAndView testtest(HttpServletRequest request) throws Exception {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/crm/message/testtest");
		return mv;
	}
	
	@RequestMapping("/ggggg.do")
	public void ggggg(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 Map<String,String> data = new HashMap<String,String>();
		  data.put("{客户名}", "jiangshunmin");
		  data.put("{维修单号}", "dddddddd");
		
		  for(int i = 0;i <= 5; i++){
			  notifyService.happenSendSms(null,null,null,"10000140",data,"18205253515",String.valueOf(i));
			  System.out.println("fffffffffffff"+i);
		  }
		JSONUtil.responseJSON(response, new AjaxResult(CommonConstants.AJAXRESULT.SUCESS, null));
	}

}