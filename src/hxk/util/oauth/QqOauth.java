package hxk.util.oauth;

import static hxk.model.dto.Login.ASSESS_TOKEN;
import static hxk.model.dto.Login.OPENID;
import hxk.model.dto.Login;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class QqOauth extends Oauth {

    /**
     * @description 利用返回的code获取token与用户的uid	
     * @param code 服务端返回的code
     * @param url 要申请的url
     * @return
     *返回类型:Map<String,String>
     */
    @Override
    public Map<String, String> getAsscessTokenAndId(String code, Login login) {
	Map<String , String> map = new HashMap<String, String>();
	String tokenstring = post(code, login.getTokenUrl(),login);
	//取得token
	if(isEmpty(tokenstring)){
	    String[] value = tokenstring.split("&");
	    for (String string : value) {
		String[] temp = string.split("=");
		map.put(temp[0], temp[1]);
	    }
	}
	String openidstring = get(login.getOpenIdUrl()+map.get(ASSESS_TOKEN));
	if(openidstring.contains("callback")){
	    	openidstring = openidstring.substring(openidstring.indexOf("{"), openidstring.indexOf("}")+1);
    		JSONObject jsonData = JSONObject.fromObject(openidstring);
    		map.put(OPENID, (String)jsonData.get(OPENID));
    		map.put("appid", (String)jsonData.get("client_id"));
	}
	return  map;
    }

    @Override
    public JSONObject getUserInfo(Map<String, String> map) {
	 String infoUrl = "https://graph.qq.com/user/get_user_info?access_token="+map.get(Login.ASSESS_TOKEN)+"&oauth_consumer_key="+map.get("appid")+"&openid="+map.get(Login.OPENID);
	 JSONObject json = JSONObject.fromObject(get(infoUrl));
	 return json;
    }

}
