package hxk.util.oauth;


import static hxk.model.dto.Login.APPKEY;
import static hxk.model.dto.Login.ASSESS_TOKEN;
import static hxk.model.dto.Login.AUTHORIZATION_CODE;
import static hxk.model.dto.Login.CODE;
import static hxk.model.dto.Login.GRANTTYPE;
import static hxk.model.dto.Login.REDIRECTURL;
import static hxk.model.dto.Login.SECRET;
import hxk.model.dto.Login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;



/**
 * @author Administrator
 * @description
 *2014-12-10  下午2:33:23
 */
public abstract class Oauth {
    private static Logger logger = Logger.getLogger(Oauth.class);
    
    /** @description post方法访问链接	
     * @param code
     * @param url
     * @param responseData
     * @return
     *2014-12-11  下午2:59:07
     *返回类型:String	
     */
    protected static String post(String code, String url,Login login) {
	String data = null;
	PostMethod postMethod = new PostMethod(url);
	postMethod.addParameter(GRANTTYPE, AUTHORIZATION_CODE);
	postMethod.addParameter(CODE,code);
	postMethod.addParameter(REDIRECTURL,login.getCallBack());
	postMethod.addParameter(APPKEY,login.getAppKet());
	postMethod.addParameter(SECRET,login.getSecretKey());
	HttpClient client = new HttpClient();
	try {
	    client.executeMethod(postMethod);
	    data = postMethod.getResponseBodyAsString();
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return data;
    }
    
    /** @description get方法访问链接	
     * @param url
     *2014-12-11  下午3:07:38
     *返回类型:void	
     */
    protected static String get(String url) {
	String info = null;
	GetMethod getMethod = new GetMethod(url);
	HttpClient client = new HttpClient();
	try {
	     client.executeMethod(getMethod);
	     info = change(getMethod);
	     logger.info("--"+info);
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return info;
    }
    
    /**
     * @description GetMethod的返回字符串转码	
     *2015-5-22  下午2:37:12
     *返回类型:String
     */
    protected static String change(GetMethod getMethod) throws IOException{
	InputStream ins = getMethod.getResponseBodyAsStream();
	//按指定的字符集构建文件流
	BufferedReader br = new BufferedReader(new InputStreamReader(ins,"UTF-8"));
	StringBuffer sbf = new StringBuffer();
	String line = null;
	while ((line = br.readLine()) != null)
	{
	sbf.append(line);
	}

	br.close();
	return sbf.toString();
    }
    
    
    /** @description 判断第三方认证网站返回的数据是不是正确的	
     * @param data
     * @return
     *2014-12-11  下午4:19:59
     *返回类型:boolean	
     */
    protected static boolean isEmpty(String data) {
	return !data.equals("") && data.indexOf(ASSESS_TOKEN) != -1;
    }
    
    
    
    /**
     * @description 获取Token以及相应的ID	
     * @param code 访问链接之后的第三方返回到指定action的Code值
     * @param login 我们的第三方实例,记录相关信息
     * @return
     *2016-3-13  下午1:07:25
     *返回类型:Map<String,String>
     */
    public abstract Map<String, String> getAsscessTokenAndId(String code,Login login);
    
    /**
     * @description 根据getAsscessTokenAndId返回的map来访问用户信息	
     * @param map
     * @return
     *2016-3-13  下午1:09:16
     *返回类型:JSONObject
     */
    public abstract JSONObject getUserInfo(Map<String , String> map);
    
    
}
