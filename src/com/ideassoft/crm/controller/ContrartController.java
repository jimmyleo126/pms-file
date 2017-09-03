package com.ideassoft.crm.controller;


import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Campaign;
import com.ideassoft.bean.Contrart;
import com.ideassoft.bean.CouponGroup;
import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.MemberCoupon;
import com.ideassoft.bean.MemberRank;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.crm.service.ContrartService;
import com.ideassoft.crm.service.LogService;
import com.ideassoft.crm.service.MemberService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class ContrartController {
	
	@Autowired
	private ContrartService contrartService;
	
	@RequestMapping("/saveContrart.do")
	public void userUpdate(HttpServletRequest request, HttpServletResponse response, String CONTRARTID) throws Exception{
		Contrart contrart;
		if(!StringUtil.isEmpty(CONTRARTID)){
			contrart = (Contrart) ReflectUtil.setBeansFromJsonArray(request, "Contrart").get(0);
			if(null == request.getParameter("CONTRART")){
				contrart.setContrart(((Contrart) contrartService.findById(Contrart.class, CONTRARTID)).getContrart());
				contrartService.sessionClear();
			}
		}else{
			Date date=new Date();
			contrart = (Contrart) ReflectUtil.setBeansFromJsonArray(request, "Contrart").get(0);
			String sequences = contrartService.getSequence("SEQ_CONTRART_ID");
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String strdate = sdf.format(date);
			contrart.setContrartId(strdate+contrart.getBranchId()+sequences);
			contrart.setRecordTime(date);
		}
		contrartService.getHibernateTemplate().saveOrUpdate(contrart);
	}
	
	@RequestMapping("/delContrart.do")
	public void delContrart(HttpServletRequest request, HttpServletResponse response, String contractId) throws UnknownHostException{
		delContrart(contractId);
		String result = "{\"code\":\"1\"}";
		JSONUtil.responseJSON(response, result);
	}
	
	public void delContrart(String contractId) throws UnknownHostException{
		String[] contractIds = contractId.split(",");
		Contrart contrart;
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < contractIds.length; i++) {
			contrart = (Contrart) contrartService.findById(Contrart.class, contractIds[i]);
			if (!StringUtil.isEmpty(contractId)) {
				contrart.setStatus("0");
				list.add(contrart);
			}
		}
		if (!list.isEmpty()) {
			contrartService.getHibernateTemplate().saveOrUpdateAll(list);
		}
	}
	
}
