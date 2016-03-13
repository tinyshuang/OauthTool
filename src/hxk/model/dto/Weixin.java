package hxk.model.dto;


/**
 * @author Administrator
 * @description
 *2014-12-11  下午2:47:35
 */
public final class Weixin implements Login {
    
    @Override
    public String getAppKet() {
	return "";
    }
    @Override
    public String getSecretKey() {
	return "";
    }
    @Override
    public String getCallBack() {
	return null;
    }
    @Override
    public String getOpenIdUrl() {
	return null;
    }
    
    
    @Override
    public String getUrl() {
	return "";
    }
    
    
    @Override
    public String getTokenUrl() {
	return "https://api.weixin.qq.com/sns/oauth2/access_token";
    }
    
    
}
