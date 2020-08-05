package com.cvmaker.api;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.cvmaker.bean.BasicInfoBean;
import com.cvmaker.bean.UserBean;
import com.cvmaker.pdf.GeneratePDF;
import com.cvmaker.resources.AppConfig;
import com.cvmaker.resources.Factory;
import com.cvmaker.resources.JsonUtil;
import com.cvmaker.service.SignInService;
import com.cvmaker.service.SignUpService;
import com.cvmaker.validator.Validator;

@Path("cvmaker")
public class SigningAPI {
	
	@Path("signUp")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signUp(String dataRecieved) throws Exception {
		////System.out.println("Inside Signup API method");
        Response response = null;
        try {
        	String message = null;
            UserBean userBean = (UserBean)JsonUtil.convertJsonToJava(dataRecieved,UserBean.class);
            ////System.out.println("Inside signup api class");
            
            Validator validator = new Validator();
            validator.validateUser(userBean);
            
            ////System.out.println("In Sign up API dataRecieved is validated:"+dataRecieved+" The UserBean = " +userBean.toString());
            
            SignUpService signUpService = Factory.createSignUpService();
            userBean = signUpService.signUp(userBean);
            if(userBean.getId()==-1) 
            	message = "Account already created.Please login";
            else
            	message = "The Sign Up was sucessful";
            ////System.out.println("Message="+message);
            userBean.setMessage(message);
            String returnString = JsonUtil.convertJavaToJson(userBean);
            if(userBean.getId()==-1)
            	response = Response.status(Status.SERVICE_UNAVAILABLE).entity(returnString).build();
            else
            	response = Response.status(Status.OK).entity(returnString).build();
            //System.out.println("In Sign up API returnString:"+returnString);
            
        } catch (Exception e) {
            e.printStackTrace();
//            String errorMessage=AppConfig.PROPERTIES.getProperty(e.getMessage());
            //System.out.println("The sign up failed");
            UserBean userBean = new UserBean();
            userBean.setMessage(e.getMessage());
            
            String returnString = JsonUtil.convertJavaToJson(userBean);

            response = Response.status(Status.SERVICE_UNAVAILABLE).entity(returnString).build();
        }
        return response;
    }
	
	@Path("signin")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signIn(String dataRecieved) throws Exception {
		//System.out.println("Inside Sign In API method");
        Response response = null;
        String message = null;
        try {
            UserBean userBean = (UserBean)JsonUtil.convertJsonToJava(dataRecieved,UserBean.class);
            //System.out.println("In Sign in API dataRecieved:"+dataRecieved);
            SignInService signInService = Factory.createSignInService();
            userBean = signInService.signIn(userBean);
            String returnString = JsonUtil.convertJavaToJson(userBean);
            if(userBean.getMessage().equals("Login Successful"))
            	response = Response.status(Status.OK).entity(returnString).build();
            else
            	response = Response.status(Status.SERVICE_UNAVAILABLE).entity(returnString).build();
	        
        } catch (Exception e) {
            e.printStackTrace();
            UserBean userBean = new UserBean();
            userBean.setMessage(e.getMessage());
            //System.out.println("error message"+e.getMessage());
            String returnString = JsonUtil.convertJavaToJson(userBean);
            response = Response.status(Status.SERVICE_UNAVAILABLE).entity(returnString).build();
        }
        return response;
    }
	
	@Path("fillForm")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response fillForm(String dataRecieved) throws Exception {
		//System.out.println("Inside fillform API method");
        Response response = null;
        try {
            BasicInfoBean infoBean = (BasicInfoBean)JsonUtil.convertJsonToJava(dataRecieved,BasicInfoBean.class);
            //System.out.println(infoBean);
            SignUpService signUpService = Factory.createSignUpService();
            infoBean.setMessage(signUpService.fillForm(infoBean));
            //String returnString = JSONParser.beanToJson(infoBean);
            if(infoBean.getMessage().equals("Success"))
            	response = Response.status(Status.OK).entity(dataRecieved).build();
            else
            	response = Response.status(Status.SERVICE_UNAVAILABLE).entity(dataRecieved).build();
            
            
        } catch (Exception e) {
            e.printStackTrace();
            UserBean userBean = new UserBean();
            userBean.setMessage(e.getMessage());
            //System.out.println("error message"+e.getMessage());
            String returnString = JsonUtil.convertJavaToJson(userBean);
            response = Response.status(Status.SERVICE_UNAVAILABLE).entity(returnString).build();
        }
        return response;
    }
	@Path("/fetchResume/{userId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchResume(@PathParam("userId") Integer id) {
	      // search my database and get a string representation and return it
		String returnValue = null;
		Response response = null;
		try {
			//System.out.println("Inside fetch resume");
			SignUpService service = Factory.createSignUpService();
			BasicInfoBean bean = service.getUser(id);
			returnValue = JsonUtil.convertJavaToJson(bean);
			//System.out.println(returnValue);
			response = Response.status(Status.OK).entity(returnValue).build();
		} catch (Exception e) {
			e.printStackTrace();
			String returnString = "{\"message\":\""
					+ AppConfig.PROPERTIES.getProperty(e.getMessage()) + "\"}";
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	   }
	
	@Path("/fetchResumes/{skills}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchResumes(@PathParam("skills") String skills) {
	      // search my database and get a string representation and return it
		String returnValue = null;
		Response response = null;
		try {
			System.out.println(skills+"inside api");
			SignUpService service = Factory.createSignUpService();
			List<BasicInfoBean> bean = service.getAllUsers(skills);
			returnValue = JsonUtil.convertJavaToJson(bean);
			System.out.println(returnValue);
			response = Response.status(Status.OK).entity(returnValue).build();
		} catch (Exception e) {
			e.printStackTrace();
			String returnString = "{\"message\":\""
					+ AppConfig.PROPERTIES.getProperty(e.getMessage()) + "\"}";
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	   }
	@Path("/downloadResume/{userId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response downloadResume(@PathParam("userId") Integer id) {
	      // search my database and get a string representation and return it
		String returnValue = null;
		Response response = null;
		try {
			//System.out.println("Inside fetch resume");
			SignUpService service = Factory.createSignUpService();
			BasicInfoBean bean = service.getUser(id);
			GeneratePDF pdf = new GeneratePDF();
			pdf.createPDF(bean);
			returnValue = JsonUtil.convertJavaToJson(bean);
			//System.out.println(returnValue);
			response = Response.status(Status.OK).entity(returnValue).build();
		} catch (Exception e) {
			e.printStackTrace();
			String returnString = "{\"message\":\""
					+ "Exception occured" + "\"}";
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	   }
	
	@Path("/fetchTopResumes/{skills}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchTopResumes(@PathParam("skills") String skills) {
	      // search my database and get a string representation and return it
		String returnValue = null;
		Response response = null;
		try {
			//System.out.println("Inside fetch resume");
			SignUpService service = Factory.createSignUpService();
			List<BasicInfoBean> bean = service.fetchTopProfiles(skills);
			returnValue = JsonUtil.convertJavaToJson(bean);
			//System.out.println(returnValue);
			response = Response.status(Status.OK).entity(returnValue).build();
		} catch (Exception e) {
			e.printStackTrace();
			String returnString = "{\"message\":\""
					+ AppConfig.PROPERTIES.getProperty(e.getMessage()) + "\"}";
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	}
	
	@Path("/fetchSkills")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response fetchSkills() {
	      // search my database and get a string representation and return it
		String returnValue = null;
		Response response = null;
		try {
			//System.out.println("Inside fetch resume");
			SignUpService service = Factory.createSignUpService();
			Set<String> skills = service.getSkills();
			returnValue = JsonUtil.convertJavaToJson(skills);
			System.out.println(returnValue);
			response = Response.status(Status.OK).entity(returnValue).build();
		} catch (Exception e) {
			e.printStackTrace();
			String returnString = "{\"message\":\""
					+ AppConfig.PROPERTIES.getProperty(e.getMessage()) + "\"}";
			response = Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(returnString).build();
		}
		return response;
	   }
}
