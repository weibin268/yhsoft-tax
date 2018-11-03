package com.yhsoft.common.web.restapi;

public class TestController extends  BaseController{

	public void test(RestApiContext context) {
		
		BaseArgs args = super.getArgs(context, BaseArgs.class);
		
		System.out.println(args.getUserId());
		
		//context.getResult().setMessage("zwb---");

		//throw new WebApiException("ׯΰ��");
		
		//return true;
		
	}
	
}
