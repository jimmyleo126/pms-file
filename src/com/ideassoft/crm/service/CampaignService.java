package com.ideassoft.crm.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ideassoft.bean.Campaign;
import com.ideassoft.bean.CampaignRule;
import com.ideassoft.bean.Staff;
import com.ideassoft.core.dao.GenericDAOImpl;
import com.ideassoft.core.page.Pagination;

@Service
public class CampaignService extends GenericDAOImpl {
	public List<?> queryAllOfBusiness() {
		String hql = "from SysParam where paramType = 'BUSINESS_TYPE'";
		return findByHQL(hql, new String[] {});
	}

	public List<?> queryCouponGroupDetail(String groupId, Pagination pagination) {
		List<?> result = findBySQLWithPagination("couponDetail",new String[] {groupId}, pagination,true);
		return result;
	}

	public List<?> querybranchNameAndType(String branchName, String branchType, Pagination pagination) {
		List<?> result = findBySQLWithPagination("selectBranchIdType",new String[] {branchName, branchType}, pagination,true);
		return result;
	}

	public List<?> queryUsingPersonAndType(String campaignId) {
		List<?> result = findBySQL("queryPersonAndType", new String[] { campaignId }, true);
		return result != null && !result.isEmpty() ?  result : null;
	}
	
	public void deleteCampaignRules(String campaignId) {
		List<?> result =this.findByProperties(CampaignRule.class, "campaignId", campaignId);
		for(int i=0;i < result.size(); i++){
			//this.delete((CampaignRule)(result.get(i)));
			((CampaignRule)(result.get(i))).setStatus("0");
			this.update((CampaignRule)(result.get(i)));
		}	
	}

	public boolean IsCurrExist(String businessId, String startDate, String endDate, String usingPerson) {
		List<?> result = findBySQL("campaignIsCurrExist", new String[] { businessId, usingPerson, startDate, endDate, startDate, endDate, startDate, endDate}, true);
		if(result == null || result.size() == 0){
			return false;
		}else{
			return true;
		}
	}

	public boolean IsDiscountRoomExist(String branchId, String startDate, String endDate, String roomType) {
		List<?> result = findBySQL("IsDiscountRoomExist", new String[] { branchId, roomType, startDate, endDate, startDate, endDate, startDate, endDate}, true);
		if(result == null || result.size() == 0){
			return false;
		}else{
			return true;
		}
	}
	
}
