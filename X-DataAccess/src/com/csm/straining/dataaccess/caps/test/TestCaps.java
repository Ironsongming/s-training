package com.csm.straining.dataaccess.caps.test;

import java.sql.SQLException;

import com.csm.straining.dataaccess.Dao;
import com.csm.straining.dataaccess.DbConfig;
import com.csm.straining.dataaccess.entity.test.Test;
import com.csm.straining.dataaccess.mapper.test.TestMapper;


/**
 * @author chensongming
 */
public class TestCaps {
	
	public static void insert() {
		
		Dao<TestMapper> dao = null;
		try {
			dao = DbConfig.openSessionMaster(TestMapper.class);
			Test t = new Test();
			t.setName("test");
			t.setAge(11);
			dao.mapper().insert(t);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (dao != null) {
				dao.close();
			}
		}
		
		
	}

}
