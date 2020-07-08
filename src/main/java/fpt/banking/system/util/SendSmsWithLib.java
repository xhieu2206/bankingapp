package fpt.banking.system.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SendSmsWithLib {

	public static void sendSms(String phone, String message) {
		// TODO Auto-generated method stub
		/*
		 * Create the POST request
		 */
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://chungdv.ddns.net:1688/services/api/messaging");
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		System.out.println(phone);
		System.out.println(message);
		params.add(new BasicNameValuePair("To", phone));
		params.add(new BasicNameValuePair("Message", message));
		try {
		    httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
		}
		try {
		    HttpResponse response = httpClient.execute(httpPost);
		    HttpEntity respEntity = response.getEntity();

		    if (respEntity != null) {
		        String content =  EntityUtils.toString(respEntity);
		        System.out.println(content);
		    }
		} catch (ClientProtocolException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		SendSmsWithLib.sendSms("0963558935", "You have created a credential in our banking app with username: " + "username" + " and password is: " + "password"
//				+ ", please login into our application to see your new account detail and card detail");
//	}
}
