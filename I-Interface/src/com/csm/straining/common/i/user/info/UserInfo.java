package com.csm.straining.common.i.user.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * @author chensongming
 */
public class UserInfo implements Serializable{

	private static final long serialVersionUID = 1756368323477794675L;
	
	public long userID;
	public String username = "";
	public String signNature = "";
	public String phone = "";
	public List<String> tags = new ArrayList<String>();
	public String location = "";
	public String occupation = "";
	public String avatar = "";
	public byte status;
	
	// 业务字段
	public int rank;
	public int score; 

}
