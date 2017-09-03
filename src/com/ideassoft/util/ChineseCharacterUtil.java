package com.ideassoft.util;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ideassoft.bean.Check;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class ChineseCharacterUtil {

	/**
	 * 将汉字转成拼音（去首字母或全拼）
	 * @param hanzi
	 */
	public static String convertHanzi2Pinyin(String hanzi){
		 /***
		 * ^[\u2E80-\u9FFF]+$ 匹配所有东亚区的语言 
		 * ^[\u4E00-\u9FFF]+$ 匹配简体和繁体 
		 * ^[\u4E00-\u9FA5]+$ 匹配简体
		 */
		String regExp = "^[\u4E00-\u9FFF]+$";
		StringBuffer sb = new StringBuffer();
		boolean full;
		if(hanzi == null || "".equals(hanzi.trim())){
			return "";
		}
		String pinyin = "";
		for(int i = 0; i < hanzi.length(); i++){
			if(i == 0){
				full = true;
			}else{
				full = false;
			}
			char unit = hanzi.charAt(i);
			if(match(String.valueOf(unit), regExp)){
				pinyin = convertSingleHanzi2Pinyin(unit);
				if(full){
					sb.append(pinyin);
				}else{
					sb.append(pinyin.charAt(0));
				}
			}else{
				sb.append(unit);
			}
		}
		return sb.toString();
	}
	
	 /***
	 * 将单个汉字转成拼音
	 * @param unit
	 * @return
	 */
	private static String convertSingleHanzi2Pinyin(char unit) {
		HanyuPinyinOutputFormat hypyopf = new HanyuPinyinOutputFormat();
		hypyopf.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		String[] res;
		StringBuffer sb = new StringBuffer();
		try{
			res = PinyinHelper.toHanyuPinyinStringArray(unit, hypyopf);
			//对于多音字，只用第一个拼音
			sb.append(res[0]);
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		return sb.toString();
	}

	 /***
	 * @param valueOf 源字符串
	 * @param regExp 正则表达式
	 * @return 是否匹配
	 */
	private static boolean match(String valueOf, String regExp) {
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(valueOf);
		return matcher.find();
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(convertHanzi2Pinyin("刘靖"));
		Calendar  time  = Calendar.getInstance();
		time.set(Calendar.HOUR_OF_DAY, 12);
		time.set(Calendar.MINUTE, 15);
		Calendar  time1  = Calendar.getInstance();
		//time1.set(Calendar.HOUR_OF_DAY, 18);
		time1.set(Calendar.MINUTE, 13);
		System.out.println(DateUtil.subHour(time1, time));
		
	}


}
