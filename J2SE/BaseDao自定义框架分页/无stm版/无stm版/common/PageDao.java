package com.common;

import java.util.List;

public interface PageDao {
	public List select(String sql, Class clazz, String... pars);

	public int getCount(String sql);
}
