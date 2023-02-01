package org.zxc.game_share.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一结果返回集
 * 看了B站 尚硅谷ssm实战后，觉得非常不错的一个类
 * @author 尚硅谷 雷丰阳
 * @since 2018
 */
public class Msg {
	
	//状态码(设定200表示成功，非200表示失败)
	private int code;
	
	//提示信息
	private String msg;
	
	//用户要返回给浏览器的数据
	private Map<String, Object> extend = new HashMap<String, Object>();

	public static Msg success(){
		Msg result = new Msg();
		result.setCode(200);
		result.setMsg("处理成功！");
		return result;
	}

	public static Msg success(int code, String msg){
		Msg result = new Msg();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}
	
	public static Msg fail(int code, String msg){
		Msg result = new Msg();
		result.setCode(code);
		result.setMsg(msg);
		return result;
	}
	
	//添加用户返回给浏览器的数据
	public Msg add(String key,Object value){
		this.getExtend().put(key, value);
		return this;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getExtend() {
		return extend;
	}

	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}
}