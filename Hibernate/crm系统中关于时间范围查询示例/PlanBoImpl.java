package com.bo.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bo.PlanBo;
import com.dao.PlanDao;
import com.dao.impl.PlanDaoImpl;
import com.entity.Plan;

public class PlanBoImpl implements PlanBo {
	private PlanDao dao = new PlanDaoImpl();

	public List selectByCondition(Plan plan, Integer minRate,
			Integer maxRate, Integer frYYYY, Integer frMM, Integer toYYYY,
			Integer toMM) {
		String hql = "select p, c from Plan p, Chance c where p.chance.chcId = c.chcId ";
		
		/* 根据id单查 */
		if (null != plan && null != plan.getPlaId() && 0 < plan.getPlaId()) {
			hql += " and p.plaId="+plan.getPlaId()+" ";
			return dao.select(hql);
		}
		
		/* 根据姓名模糊查 */
		if (null != plan && null != plan.getChance() && null != plan.getChance().getChcCustName() && 1 < plan.getChance().getChcCustName().length()) {
			hql += " and c.chcCustName like '%"+plan.getChance().getChcCustName()+"%' ";
		}
		
		/* 按姓氏查询 */
		if (null != plan && null != plan.getChance() && null != plan.getChance().getChcCustName() && 1 == plan.getChance().getChcCustName().length()) {
			hql += " and c.chcCustName like '"+plan.getChance().getChcCustName()+"%' ";
		}
		
		/* 按chcTitle查询 */
		if (null != plan && null != plan.getChance() && null != plan.getChance().getChcTitle() && 0 < plan.getChance().getChcTitle().length()) {
			hql += " and c.chcTitle like '%"+plan.getChance().getChcTitle()+"%' ";
		}
		
		/* 成功率 */
		if (null != minRate && 0 < minRate) {
			hql += " and c.chcRate >= "+minRate+" ";
		}
		if (null != maxRate && minRate < maxRate) {
			hql += " and c.chcRate <= "+maxRate+" ";
		}
		
		/* 创建时间 */
		/* 填了年份下限 */
		if (null != frYYYY && 0 < frYYYY ) {
			if (null != frMM && 0 < frMM) {
				/* 填了月份, 从填写年月(填写月1日)查到当前年月(不包含当前月) */
				hql += " and c.chcCreateDate > '"+frYYYY+frMM+"01' ";
				
				/* 填了上限年 */
				if (null != toYYYY && 0 < toYYYY) {
					if (null != toMM && 0 < toMM) {
						/* 填了上限月, 查到上限月(不包含上限月份) */
						hql += " and c.chcCreateDate < '"+toYYYY+(toMM-1)+"31' ";
					} else if (null == toMM || 0 == toMM) {
						/* 没有上限月, 查到上限年一年(不包含上限年) */
						/* 2002-1-23------2003()  */
						hql += " and c.chcCreateDate < '"+(toYYYY-1)+"1231' ";
					}
				} else {
					/* 没有上限年 */
					/* 查到当前 */
					hql += " and c.chcCreateDate < '"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"' ";
				}
			} else if (null == frMM || 0 == frMM) {
				/* 没填月份, 从填写年1月查到当前 */
				hql += " and c.chcCreateDate > '"+frYYYY+"0101'";
				
				/* 填了上限年 */
				if (null != toYYYY && 0 < toYYYY) {
					if (null != toMM && 0 < toMM) {
						/* 填了上限月, 查到上限月(不包含上限月份) */
						hql += " and c.chcCreateDate < '"+toYYYY+(toMM-1)+"31' ";
					} else if (null == toMM || 0 == toMM) {
						/* 没有上限月, 查到上限年一年(不包含上限年) */
						/* 2002-1-23------2003()  */
						hql += " and c.chcCreateDate < '"+toYYYY+"1231' ";
					}
				} else {
					/* 没有上限年 */
					/* 查到当前 */
					hql += " and c.chcCreateDate < '"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"' ";
				}
			}
		} else if (null == frYYYY || 0 == frYYYY) {
			/* 没有下限年 */
			/* 客户端屏蔽下限月份输入 */
			if (null != toYYYY && 0 < toYYYY) {
				/* 有上限年 */
				if (null != toMM && 0 < toMM) {
					/* 有上限月, 查到填写上限年月(包含) */
					hql += " and c.chcCreateDate < '"+toYYYY+toMM+"31' ";
				} else if (null == toMM || 0 == toMM) {
					/* 没有上限月, 查到前一年全年 */
					hql += " and c.chcCreateDate < '"+(toYYYY-1)+"1231' ";
				}
			} 
//				else {
//				/* 没有上限年份, 客户端屏蔽 */
//			}
		}
		
		if (null != plan && null != plan.getPlaResult() && 0 < plan.getPlaResult().length()) {
			hql += " and p.plaResult = '"+plan.getPlaResult()+"' ";
		}
		return dao.select(hql);
	}

}
