package com.cvmaker.sort;

import java.util.Comparator;

import com.cvmaker.bean.BasicInfoBean;


public class SortResume implements Comparator<BasicInfoBean>{

	@Override
	public int compare(BasicInfoBean e1, BasicInfoBean e2) {
		// TODO Auto-generated method stub
		if(e1.getMembership() != e2.getMembership()) {
			return e2.getMembership() - e1.getMembership();
		}
		else {
			String[] skill1 = e1.getInterests().split(" |\n");
    		String[] skill2 = e2.getInterests().split(" |\n");
    		if(skill1.length!=skill2.length)
    			return skill2.length - skill1.length;
    		else {
    			if (e1.getExperienceInYears() != e2.getExperienceInYears()) 
    				return e2.getExperienceInYears() - e1.getExperienceInYears();
    			else
    				return e2.getExperienceInMonths() - e1.getExperienceInMonths();
    		}
		}
			
	}

	
}