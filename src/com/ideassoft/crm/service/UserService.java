package com.ideassoft.crm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.Role;
import com.ideassoft.bean.Staff;
import com.ideassoft.bean.SysParam;
import com.ideassoft.bean.UserRole;
import com.ideassoft.core.dao.GenericDAOImpl;

@Service
public class UserService extends GenericDAOImpl {

	public Staff findUser(String userInfo) {
		Object staff = findOneByProperties(Staff.class, "loginName", userInfo);
		return staff != null ? (Staff) staff : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> findUserRoles(String userId) {
		String hql = "select p from t in class " + UserRole.class.getName() + ", p in class " + Role.class.getName() +
			" where t.roleId = p.roleId and t.userId = ?";
		return findByHQL(hql, new String[] { userId });
	}
	
	public Staff findRegisterUser(String loginName, String psw) {
		String hql = " from Staff where loginName = ? and password = ? and status = 2";
		List<?> result = findByHQL(hql, new String[] { loginName, psw });
		return result != null && !result.isEmpty() ? (Staff) result.get(0) : null;
	}
	
	public boolean hasBackdoor() {
		Object result = findOneByProperties(SysParam.class, "paramType", "BACKDOOR");
		return result != null;
	}
	
	public void removeUserRole(String userId) {
		String hql = "delete UserRole where userId = :USERID";
		executeUpdateHQL(hql, new String[] { "USERID" }, new String[] { userId });
	}
	
	public List<?> getShifttime() throws Exception {
		return findBySQL("shifttime", true);
	}
	
}
