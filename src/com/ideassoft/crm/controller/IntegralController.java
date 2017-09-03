package com.ideassoft.crm.controller;


import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ideassoft.bean.Member;
import com.ideassoft.bean.MemberAccount;
import com.ideassoft.bean.SysParam;
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.crm.service.IntegralService;
import com.ideassoft.crm.service.LogService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.RequestUtil;


@Transactional
@Controller
public class IntegralController {
	
	@Autowired
	private IntegralService integralService;
	
	@Autowired
	private LogService logService;
	
	/**
	 * 打开积分兑换比例页面
	 * @return
	 */
	@RequestMapping("/turnToExchangeScore.do")
	public ModelAndView turnToExchangeScore(){
        ModelAndView mv = new ModelAndView();
        SysParam score = (SysParam) integralService.findOneByProperties(SysParam.class, "paramType", "SCORE");
        SysParam money = (SysParam) integralService.findOneByProperties(SysParam.class, "paramType", "MONEY");
        mv.addObject("score",null == score ? "" : score.getContent());
        mv.addObject("money",null == money ? "" : money.getContent());
        mv.setViewName("page/crm/integral/exchangeScore");
        return mv;
    }
	
	/**
	 * 修改积分兑换比例
	 * @param request
	 * @param response
	 * @param score
	 * @param money
	 */
	@RequestMapping("/updateExchangeScoreRule.do")
	public void updateExchangeScoreRule(HttpServletRequest request, HttpServletResponse response,String score,String money){
		SysParam Score = (SysParam) integralService.findOneByProperties(SysParam.class, "paramType", "SCORE");
        SysParam Money = (SysParam) integralService.findOneByProperties(SysParam.class, "paramType", "MONEY");
		String result;
        if(null == Score){
        	SysParam sysparam = new SysParam();
        	sysparam.setParamId(integralService.getSequence("SEQ_NEW_PARAMID"));
        	sysparam.setParamType("SCORE");
        	sysparam.setParamName("积分");
        	sysparam.setContent(score);
        	sysparam.setStatus("1");
        	integralService.saveIfo(sysparam);
        	result = "{\"code\":\"1\"}";
        }else{
        	Score.setContent(score);
        	integralService.saveIfo(Score);
        	result = "{\"code\":\"1\"}";
        }
        if(null == Money){
        	SysParam sysparam = new SysParam();
        	sysparam.setParamId(integralService.getSequence("SEQ_NEW_PARAMID"));
        	sysparam.setParamType("MONEY");
        	sysparam.setParamName("金额");
        	sysparam.setContent(money);
        	sysparam.setStatus("1");
        	integralService.saveIfo(sysparam);
        	result = "{\"code\":\"1\"}";
        }else{
        	Money.setContent(money);
        	integralService.saveIfo(Money);
        	result = "{\"code\":\"1\"}";
        }
        JSONUtil.responseJSON(response, result);
	}
	
	/**
	 * 积分调整页面
	 * @param accountid
	 * @return
	 */
	@RequestMapping("/turnToScoreAdjustment.do")
	public ModelAndView turnToScoreAdjustment(String accountid){
		ModelAndView mv = new ModelAndView();
		mv.addObject("accountid",accountid);
		mv.setViewName("page/crm/integral/scoreAdjustment");
		return mv;
	}
	
	/**
	 * 修改会员积分
	 * @param request
	 * @param response
	 * @param integration
	 * @param accountid
	 * @throws UnknownHostException
	 */
	@RequestMapping("/changeIntegration.do")
	public void changeIntegration(HttpServletRequest request, HttpServletResponse response,Long integration,String accountid) throws UnknownHostException{
		MemberAccount account = (MemberAccount)integralService.getAccount(accountid);
		Member member =  (Member) integralService.findById(Member.class, account.getMemberId());
		if(integration < account.getCurrIntegration()){
			JSONUtil.responseJSON(response, new AjaxResult(1,"扣除积分大于剩余积分!"));
		}else{
			account.setCurrIntegration(account.getCurrIntegration()+(integration));
			integralService.saveIfo(account);
			String remark=member.getMemberName()+"积分调整:"+integration;
			logService.changeIntegrationLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), remark);
			JSONUtil.responseJSON(response, new AjaxResult(1,"修改成功!"));
		}
	}
}
