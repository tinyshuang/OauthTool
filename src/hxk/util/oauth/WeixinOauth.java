package hxk.util.oauth;

import hxk.model.dto.Login;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import net.sf.json.JSONObject;

/**
 * @author Administrator
 * @description
 *2016-3-13  下午1:10:27
 */
public class WeixinOauth extends Oauth {

    /**
     * @description 利用返回的code获取token与用户的uid	
     * @param code 服务端返回的code
     * @param url 要申请的url
     * @return
     *返回类型:Map<String,String>
     */
    @Override
    public Map<String, String> getAsscessTokenAndId(String code, Login login) {
	String data = "" ;
	Map<String , String> token = new HashMap<String, String>();
	PostMethod postMethod = new PostMethod(login.getTokenUrl());
	postMethod.addParameter("grant_type", "authorization_code");
	postMethod.addParameter("code",code);
	postMethod.addParameter("appid",login.getAppKet());
	postMethod.addParameter("secret",login.getSecretKey());
	HttpClient client = new HttpClient();
	try {
	    client.executeMethod(postMethod);
	    data = postMethod.getResponseBodyAsString();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	if(data.contains(Login.ASSESS_TOKEN)){
	    JSONObject jsonData = JSONObject.fromObject(data);
	    token.put(Login.ASSESS_TOKEN, (String)jsonData.get(Login.ASSESS_TOKEN));
	    token.put(Login.OPENID, (String)jsonData.get(Login.OPENID));
	    token.put("scope", (String)jsonData.get("scope"));
	}
	return  token;
    }

    /***
     * 获取微信用户信息
     */
    @Override
    public JSONObject getUserInfo(Map<String, String> map) {
	String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+map.get(Login.ASSESS_TOKEN)+"&openid="+map.get(Login.OPENID);
	JSONObject json = JSONObject.fromObject(get(infoUrl));
	return json;
    }

}
