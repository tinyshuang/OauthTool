package hxk.model.dto;

/**
 * @author Administrator
 * @description
 *2014-12-11  下午2:46:36
 */
public interface Login {
    public static final String GRANTTYPE = "grant_type";
    public static final String CODE = "code";
    public static final String REDIRECTURL = "redirect_uri";
    public static final String APPKEY = "client_id";
    public static final String SECRET = "client_secret";
    
    public static final String ASSESS_TOKEN = "access_token";
    public static final String AUTHORIZATION_CODE = "authorization_code";
    public static final String OPENID = "openid";

    
    
    
    public String getAppKet();
    public String getSecretKey();
    public String getCallBack();
    public String getOpenIdUrl();
    
    public String getUrl();
    
    public String getTokenUrl();
    
}
