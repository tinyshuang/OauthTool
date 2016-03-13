package hxk.util.oauth;

import static hxk.model.dto.Login.ASSESS_TOKEN;
import static hxk.model.dto.Login.OPENID;
import hxk.model.dto.Login;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class WeiboOauth extends Oauth {

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
	String data = post(code, login.getTokenUrl(),login);
	if(data.contains(Login.ASSESS_TOKEN)){
	    JSONObject json = JSONObject.fromObject(data);
	    map.put(ASSESS_TOKEN, json.getString(ASSESS_TOKEN));
	    map.put(OPENID, json.getString("uid"));
	}
	return  map;
    }

    @Override
    public JSONObject getUserInfo(Map<String, String> map) {
	 String infoUrl ="https://api.weibo.com/2/users/show.json?access_token="+map.get(Login.ASSESS_TOKEN)+"&uid="+map.get(Login.OPENID);
	 JSONObject json = JSONObject.fromObject(get(infoUrl));
	 return json;
    }

}
