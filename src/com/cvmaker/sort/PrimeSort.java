package com.cvmaker.sort;

import java.util.Comparator;

import com.cvmaker.bean.BasicInfoBean;

public class PrimeSort implements Comparator<BasicInfoBean>{

	@Override
	public int compare(BasicInfoBean e1, BasicInfoBean e2) {
		// TODO Auto-generated method stub
		 return e1.getMembership() - e2.getMembership(); 
	}
}
