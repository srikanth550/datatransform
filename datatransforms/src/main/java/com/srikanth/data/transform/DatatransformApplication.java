package com.srikanth.data.transform;

import com.srikanth.data.transform.service.DataTransformService;

public class DatatransformApplication {

	public static void main(String[] args) {
		DataTransformService dataTransformService = new DataTransformService();
		
		String result = dataTransformService.getJsonObj();
		
		System.out.println(result);
	}
}