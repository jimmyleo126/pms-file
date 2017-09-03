package com.ideassoft.crm.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
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
import com.ideassoft.core.ajax.AjaxResult;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.page.Pagination;
import com.ideassoft.crm.service.LogService;
import com.ideassoft.crm.service.MemberService;
import com.ideassoft.util.JSONUtil;
import com.ideassoft.util.MD5Util;
import com.ideassoft.util.ReflectUtil;
import com.ideassoft.util.RequestUtil;

@Transactional
@Controller
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private LogService logService;
	/**
	 * 保存，修改会员信息
	 * @param request
	 * @param response
	 * @param MEMBERID
	 * @throws Exception
	 */
	@RequestMapping("/saveMember.do")
	public void userUpdate(HttpServletRequest request, HttpServletResponse response, String MEMBERID) throws Exception{
		Member member;
		MemberAccount account = null;
		if(null != MEMBERID){
			member = (Member) ReflectUtil.setBeansFromJsonArray(request, "Member").get(0);
			if(null == request.getParameter("PHOTOS")){
				member.setPhotos(((Member) logService.findById(Member.class, MEMBERID)).getPhotos());
				logService.sessionClear();
			}
			logService.memberLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), member,"3");
		}else{
			Date date=new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.YEAR, 1);
			member = (Member) ReflectUtil.setBeansFromJsonArray(request, "Member", true, "MEMBERID|SEQ_MEMBER_ID", null).get(0);
			member.setPassword(MD5Util.getCryptogram("888888"));
			member.setRecordTime(new Date());
			member.setValidTime(date);
		    member.setInvalidTime(calendar.getTime());
		    member.setRegisterTime(date);
		    member.setRecordTime(date);
			account = (MemberAccount) ReflectUtil.setBeansFromJsonArray(request, "MemberAccount", true, "ACCOUNTID|SEQ_ACCOUNT_ID", null).get(0);
			account.setMemberId(member.getMemberId());
			account.setCurrBalance((double) 0);//当前余额
			account.setCurrIntegration((long) 0);//当前积分
			account.setTotalRecharge((double) 0);//充值金额
			account.setDiscountGift((double) 0);//折扣赠送
			account.setChargeGift((double) 0);//充值赠送
			account.setTotalConsume((double) 0);//消费金额
			account.setTotalIntegration((long) 0);//获取积分
			account.setIngegrationGift((long) 0);//积分赠送
			account.setTotalConsIntegration((long) 0);//消费积分
			account.setTotalRoomnights(0);//总房晚
			account.setCurrRoomnights(0);//当前房晚
			account.setTotalNoshow((short) 0);//总未现天数
			account.setCurrNoshow((short) 0);//当前未现天数
			account.setThisYearIntegration((long) 0);//当年积分
			account.setRecordTime(new Date());
			logService.memberLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), member,"1");
		}
		memberService.getHibernateTemplate().saveOrUpdate(member);
		if(null != account){
			memberService.getHibernateTemplate().saveOrUpdate(account);
		}
	}
	
	/**
	 * 会员卡升级页面
	 * @param accountid
	 * @param memberid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/memberUpGrade.do")
    public ModelAndView memberUpGrade(String accountid, String memberid) {
        ModelAndView mv = new ModelAndView();
        Member member = (Member) memberService.findById(Member.class, memberid);
        MemberAccount memberaccount = (MemberAccount) memberService.findOneByProperties(MemberAccount.class, "memberId", member.getMemberId());
        if(member.getMemberRank().equals("5")){
			List<Member> memberlist = memberService.findByProperties(Member.class, "recommend", member.getMemberId());
			if(memberlist.size() >= 10 && memberaccount.getCurrRoomnights() >= 20 && memberaccount.getCurrNoshow() < 0){
				int num = 0;
				for(int i=0;i<memberlist.size();i++){
					if(Integer.parseInt(memberlist.get(i).getMemberRank()) >= 5){
						num = num + 1;
					}
				}
				if(num >= 2){
					mv.addObject("memberblack","6");
				}
			}
        }
        mv.addObject("accountid", accountid);
        mv.addObject("memberid", memberid);
        mv.addObject("memberrank", member.getMemberRank());
        mv.setViewName("page/crm/member/memberUpGrade");
        return mv;
    }
	/**
	 * 会员卡升级选择
	 * @param memberrank
	 * @param accountid
	 * @param memberid
	 * @param memberrankname
	 * @return
	 */
	@RequestMapping("/payUpGradeMemberLevel.do")
    public ModelAndView payUpGradeMemberLevel(String memberrank, String accountid, String memberid, String memberrankname) {
        ModelAndView mv = new ModelAndView();
        SysParam rank = (SysParam) memberService.findOneByProperties(SysParam.class, "paramType", "MEMBERRANK", "paramDesc", memberrank);
        mv.addObject("memberrankmoney",rank.getContent());
        mv.addObject("memberrank", memberrank);
        mv.addObject("accountid", accountid);
        mv.addObject("memberid", memberid);
        mv.addObject("memberrankname",memberrankname);
        mv.setViewName("page/crm/member/payUpGradeMemberLevel");
        return mv;
    }
	//pms处新增会员购卡
	@RequestMapping("/payUpGradeMemberLevelInPms.do")
    public ModelAndView payUpGradeMemberLevelInPms(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("page/ipmspage/member/memberLevelInPms");
        return mv;
    }
	
	@RequestMapping("/selectMemberInfo.do")
    public void selectMemberInfo(HttpServletRequest request, HttpServletResponse response, String msoidcard) {
		List<?> list = memberService.findBySQL("selectMemberInfo", new String[]{msoidcard, msoidcard}, true);
		String result = null;
		if (null == list || list.size() == 0) {
			result = "未查询到相关会员信息！";
			JSONUtil.responseJSON(response, new AjaxResult(1, result));
		}else{
			JSONUtil.responseJSON(response, new AjaxResult(0, result));
		}
    }
	
	@RequestMapping("/queryMemberInfo.do")
    public ModelAndView queryMemberInfo(HttpServletRequest request, HttpServletResponse response, String msoidcard) {
        ModelAndView mv = new ModelAndView();
        String gendor = "";
        if(!StringUtil.isEmpty(msoidcard)){
        	 if(msoidcard.length() > 11){
             	Member member = (Member) memberService.findOneByProperties(Member.class, "idcard", msoidcard);
             	MemberAccount memberAccount = (MemberAccount) memberService.findOneByProperties(MemberAccount.class, "memberId", member.getMemberId());
             	MemberRank rank = (MemberRank) memberService.findOneByProperties(MemberRank.class, "memberRank", member.getMemberRank());
				switch (Integer.parseInt(member.getGendor())) {  
				case 0:
					gendor = "女";
					mv.addObject("gendor", gendor);
				case 1:
					gendor = "男";
					mv.addObject("gendor", gendor);
				default:  
				}  
             	mv.addObject("member", member);
             	mv.addObject("memberAccount", memberAccount);
             	mv.addObject("rank", rank);
             	mv.setViewName("page/ipmspage/member/memberpage");
             }else{
             	Member member = (Member) memberService.findOneByProperties(Member.class, "mobile", msoidcard);
             	MemberAccount memberAccount = (MemberAccount) memberService.findOneByProperties(MemberAccount.class, "memberId", member.getMemberId());
             	MemberRank rank = (MemberRank) memberService.findOneByProperties(MemberRank.class, "memberRank", member.getMemberRank());
             	switch (Integer.parseInt(member.getGendor())) {  
				case 0:
					gendor = "女";
					mv.addObject("gendor", gendor);
				case 1:
					gendor = "男";
					mv.addObject("gendor", gendor);
				default:  
				}  
             	mv.addObject("member", member);
             	mv.addObject("memberAccount", memberAccount);
             	mv.addObject("rank", rank);
             	mv.setViewName("page/ipmspage/member/memberpage");
             }
        }else{
        	mv.setViewName("page/ipmspage/member/memberpagenull");
        }
        return mv;
    }
	
	/**
	 * 会员卡升级的等级充值
	 * @param request
	 * @param response
	 * @param memberrank
	 * @param accountid
	 * @param memberid
	 * @param money
	 * @param paytype
	 * @throws UnknownHostException
	 */
	@RequestMapping("/savaMemberRank.do")
    public void savaMemberRank(HttpServletRequest request, HttpServletResponse response,
    		String memberrank,String accountid,String memberid,Double money, String paytype) throws UnknownHostException {
		MemberAccount memberAccount =  (MemberAccount) memberService.getCustomerInfo(accountid);
		memberAccount.setCurrBalance(memberAccount.getCurrBalance()+money);
		memberAccount.setTotalRecharge(memberAccount.getTotalRecharge()+money);
		Member member =  (Member) memberService.getmember(memberid);
		member.setMemberRank(memberrank);
		MemberRank rank = (MemberRank) memberService.findOneByProperties(MemberRank.class, "memberRank", memberrank);
		String remark=member.getMemberName()+"会员升级到:"+ rank.getRankName();
		logService.changeMemberScoreAndReserveLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), remark,"4");
		memberService.save(memberAccount);
		memberService.save(member);
		String result = "{\"code\":\"1\"}";
		JSONUtil.responseJSON(response, result);
    }
	
	/**
	 * 储值卡充值
	 * @param accountid
	 * @param memberid
	 * @param membername
	 * @return
	 */
	@RequestMapping("/turnToCardRecharge.do")
    public ModelAndView turnToCardRecharge(String accountid,String memberid,String membername) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("accountid", accountid);
        mv.addObject("memberid", memberid);
        mv.addObject("membername", membername);
        mv.setViewName("page/crm/member/addCustom");
        return mv;
    }
	
	/**
	 * 充值活动,优惠卷读取
	 * @param request
	 * @param response
	 * @param account_Id
	 * @param dataId
	 * @param member_Id
	 * @throws Exception
	 */
	@RequestMapping("/changeMemberScoreAndReserve.do")
	public void changeMemberScoreAndReserve(HttpServletRequest request, HttpServletResponse response, String account_Id, String dataId, String member_Id, String discount_gift) throws Exception {
		MemberAccount me =  (MemberAccount) memberService.getCustomerInfo(account_Id);//获取会员账户
		Member member =  (Member) memberService.getmember(member_Id);
		String remark = "";
		if(!StringUtil.isEmpty(discount_gift)){
			me.setCurrBalance(me.getCurrBalance()+Integer.parseInt(discount_gift));//根据充值添加到当前余额
			me.setTotalRecharge(me.getTotalRecharge()+Integer.parseInt(discount_gift));//根据充值添加充值金额
			remark = member.getMemberName()+"充值金额:"+discount_gift;
		}
		if(!StringUtil.isEmpty(dataId)){
			Campaign campaign = (Campaign) memberService.findById(Campaign.class, dataId);//获取营销活动
			me.setCurrBalance(me.getCurrBalance()+campaign.getOperMoney());//根据充值添加到当前余额
			me.setTotalRecharge(me.getTotalRecharge()+campaign.getOperMoney());//根据充值添加充值金额
			remark = member.getMemberName()+"充值金额:"+campaign.getOperMoney();
			CouponGroup coupongroup = (CouponGroup) memberService.findById(CouponGroup.class, campaign.getCouponGroupId());//获取优惠卷组合
			if(null != coupongroup){
				List<MemberCoupon> couponlist = new ArrayList<MemberCoupon>();
				//10元优惠卷
				if(null != coupongroup.getTenCoupon()){
					SysParam sysparam = (SysParam) memberService.findOneByProperties(SysParam.class, "paramType", "COUPON", "paramName", "TEN_COUPON");
					Calendar c = Calendar.getInstance();
			        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(sysparam.getOrderNo().toString()));
					for(int i = 0;i < Integer.parseInt(coupongroup.getTenCoupon());i++){
						MemberCoupon membercoupon = new MemberCoupon();
						membercoupon.setMemberId(me.getMemberId());
						membercoupon.setCouponId(memberService.getSequence("SEQ_TEN_COUPON_ID"));
						membercoupon.setCouponPrice("10");
						membercoupon.setStartTime(new Date());
						membercoupon.setEndTime(c.getTime());
						membercoupon.setStatus("1");
						couponlist.add(membercoupon);
						sysparam.setParamDesc(membercoupon.getCouponId());
						memberService.update(sysparam);
					}
				}
				//20元优惠卷
				if(null != coupongroup.getTwentyCoupon()){
					SysParam sysparam = (SysParam) memberService.findOneByProperties(SysParam.class, "paramType", "COUPON", "paramName", "TWENTY_COUPON");
					Calendar c = Calendar.getInstance();
			        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(sysparam.getOrderNo().toString()));
					for(int i = 0;i < Integer.parseInt(coupongroup.getTwentyCoupon());i++){
						MemberCoupon membercoupon = new MemberCoupon();
						membercoupon.setMemberId(me.getMemberId());
						membercoupon.setCouponId(memberService.getSequence("SEQ_TWENTY_COUPON_ID"));
						membercoupon.setCouponPrice("20");
						membercoupon.setStartTime(new Date());
						membercoupon.setEndTime(c.getTime());
						membercoupon.setStatus("1");
						couponlist.add(membercoupon);
						sysparam.setParamDesc(membercoupon.getCouponId());
						memberService.update(sysparam);
					}
				}
				//30元优惠卷
				if(null != coupongroup.getThirtyCoupon()){
					SysParam sysparam = (SysParam) memberService.findOneByProperties(SysParam.class, "paramType", "COUPON", "paramName", "THIRTY_COUPON");
					Calendar c = Calendar.getInstance();
			        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(sysparam.getOrderNo().toString()));
					for(int i = 0;i < Integer.parseInt(coupongroup.getThirtyCoupon());i++){
						MemberCoupon membercoupon = new MemberCoupon();
						membercoupon.setMemberId(me.getMemberId());
						membercoupon.setCouponId(memberService.getSequence("SEQ_THIRTY_COUPON_ID"));
						membercoupon.setCouponPrice("30");
						membercoupon.setStartTime(new Date());
						membercoupon.setEndTime(c.getTime());
						membercoupon.setStatus("1");
						couponlist.add(membercoupon);
						sysparam.setParamDesc(membercoupon.getCouponId());
						memberService.update(sysparam);
					}
				}
				//50元优惠卷
				if(null != coupongroup.getFiftyCoupon()){
					SysParam sysparam = (SysParam) memberService.findOneByProperties(SysParam.class, "paramType", "COUPON", "paramName", "FIFTY_COUPON");
					Calendar c = Calendar.getInstance();
			        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(sysparam.getOrderNo().toString()));
					for(int i = 0;i < Integer.parseInt(coupongroup.getFiftyCoupon());i++){
						MemberCoupon membercoupon = new MemberCoupon();
						membercoupon.setMemberId(me.getMemberId());
						membercoupon.setCouponId(memberService.getSequence("SEQ_FIFTY_COUPON_ID"));
						membercoupon.setCouponPrice("50");
						membercoupon.setStartTime(new Date());
						membercoupon.setEndTime(c.getTime());
						membercoupon.setStatus("1");
						couponlist.add(membercoupon);
						sysparam.setParamDesc(membercoupon.getCouponId());
						memberService.update(sysparam);
					}
				}
				//100元优惠卷
				if(null != coupongroup.getHundredCoupon()){
					SysParam sysparam = (SysParam) memberService.findOneByProperties(SysParam.class, "paramType", "COUPON", "paramName", "HUNDRED_COUPON");
					Calendar c = Calendar.getInstance();
			        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(sysparam.getOrderNo().toString()));
					for(int i = 0;i < Integer.parseInt(coupongroup.getHundredCoupon());i++){
						MemberCoupon membercoupon = new MemberCoupon();
						membercoupon.setMemberId(me.getMemberId());
						membercoupon.setCouponId(memberService.getSequence("SEQ_HUNDRED_COUPON_ID"));
						membercoupon.setCouponPrice("100");
						membercoupon.setStartTime(new Date());
						membercoupon.setEndTime(c.getTime());
						membercoupon.setStatus("1");
						couponlist.add(membercoupon);
						sysparam.setParamDesc(membercoupon.getCouponId());
						memberService.update(sysparam);
					}
				}
				for(int i=0;i<couponlist.size();i++){
					memberService.save(couponlist.get(i));
				}
			}
		}
		memberService.save(me);
		logService.changeMemberScoreAndReserveLog((LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY), remark,"");
		String result = "{\"code\":\"1\"}";
		JSONUtil.responseJSON(response, result);
	}
	
	/**
	 * 删除会员
	 * @param request
	 * @param response
	 * @param memberid
	 * @throws UnknownHostException
	 */
	@RequestMapping("/delMember.do")
	public void delMember(HttpServletRequest request, HttpServletResponse response,String memberid) throws UnknownHostException{
		LoginUser loginUser = (LoginUser) request.getSession(true).getAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		delMember(memberid,loginUser);
		String result = "{\"code\":\"1\"}";
		JSONUtil.responseJSON(response, result);
	}
	
	public void delMember(String memberid, LoginUser loginUser) throws UnknownHostException{
		String[] memberids = memberid.split(",");
		Member member;
		MemberAccount ma;
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < memberids.length; i++) {
			member = (Member) memberService.findById(Member.class, memberids[i]);
			if (member != null) {
				member.setStatus("0");
				logService.memberLog(loginUser, member,"2");
				list.add(member);
			}
			ma = (MemberAccount) memberService.findOneByProperties(MemberAccount.class, "status", "1", "memberId", memberids[i]);
			if (ma != null) {
				ma.setStatus("0");
				list.add(ma);
			}
		}
		
		if (!list.isEmpty()) {
			memberService.getHibernateTemplate().saveOrUpdateAll(list);
		}
	}
	
	@RequestMapping("/queryContract.do")
    public ModelAndView queryContract(HttpServletRequest request, HttpServletResponse response, String contract) throws IOException {
        ModelAndView mv = new ModelAndView();
        contractToHtml("D:/工具/apache-tomcat-7.0.72/webapps/ipms/upload/"+contract,"D:/工具/apache-tomcat-7.0.72/webapps/ipms/upload/","D:/工具/apache-tomcat-7.0.72/webapps/ipms/upload/1.html");
        mv.setViewName("page/crm/member/contract");
        return mv;
    }
	
	@RequestMapping("/rentRenewal.do")
    public ModelAndView rentRenewal(HttpServletRequest request, HttpServletResponse response, String contractId) throws IOException {
        ModelAndView mv = new ModelAndView();
        Contrart contrart = (Contrart) memberService.findById(Contrart.class, contractId);
        Member member = (Member) memberService.findById(Member.class, contrart.getMemberId());
        String typeofpaymentName = "";
        if(contrart.getTypeOfPayment().equals("1")){
        	typeofpaymentName = "月付";
        }else if(contrart.getTypeOfPayment().equals("3")){
        	typeofpaymentName = "季付";
        }
        else if(contrart.getTypeOfPayment().equals("6")){
        	typeofpaymentName = "半年付";
        }
        else if(contrart.getTypeOfPayment().equals("12")){
        	typeofpaymentName = "年付";
        }
        mv.addObject("contractId",contractId);
        mv.addObject("memberName",member.getMemberName());
        mv.addObject("roomId",contrart.getRoomId());
        mv.addObject("typeofpayment",contrart.getTypeOfPayment());
        mv.addObject("typeofpaymentName",typeofpaymentName);
        mv.addObject("endTime",contrart.getEndTime());
        mv.setViewName("page/crm/member/rentRenewal");
        return mv;
    }
	
	@RequestMapping("/changeApartmentRent.do")
    public void apartmentRent(HttpServletRequest request, HttpServletResponse response, String contractId, String rent_typeofpayment) throws IOException {
		Contrart contrart = (Contrart) memberService.findById(Contrart.class, contractId);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(contrart.getContrartEndTime());
		calendar.add(Calendar.MONTH, Integer.parseInt(rent_typeofpayment));
		contrart.setContrartEndTime(calendar.getTime());
		memberService.update(contrart);
		String result = "{\"code\":\"1\"}";
		JSONUtil.responseJSON(response, result);
    }
	
	/**
	 * 弹出框,查询充值活动
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/selectCampaign.do")
	public ModelAndView selectCampaign(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/crm/member/memberDialog");
		return mv;
	}
	
	@RequestMapping("/queryCampaign.do")
	public void queryCampaign(HttpServletRequest request,HttpServletResponse response, String cond_CUSTOMVALUE) {
		Pagination pagination;
		Integer pageNum = Integer.parseInt(request.getParameter("page"));
		Integer rows = Integer.parseInt(request.getParameter("rows"));
		pagination = new Pagination(pageNum, rows);
		List<?> result = memberService.findBySQLWithPagination("queryCampaign", new String[]{cond_CUSTOMVALUE }, pagination,true);
		JSONUtil.responseJSON(response, JSONUtil.buildReportJSONByPagination(result, pagination));
	}
	
	public static void contractToHtml(String fileName ,String imageFile , String htmFile) throws IOException{
        File f = new File(fileName);
        if (f.getName().endsWith(".docx")|| f.getName().endsWith(".DOCX") || f.getName().endsWith(".doc")) {
            InputStream in = new FileInputStream(f);
            XWPFDocument document = new XWPFDocument(in);
            File imgFile = new File(imageFile);
            XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imgFile));
            options.setExtractor(new FileImageExtractor(imgFile));
            OutputStream out = new FileOutputStream(new File(htmFile));  
            XHTMLConverter.getInstance().convert(document, out, options);  
        }         
    }
	
}
