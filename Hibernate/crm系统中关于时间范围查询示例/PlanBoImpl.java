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
		
		/* ����id���� */
		if (null != plan && null != plan.getPlaId() && 0 < plan.getPlaId()) {
			hql += " and p.plaId="+plan.getPlaId()+" ";
			return dao.select(hql);
		}
		
		/* ��������ģ���� */
		if (null != plan && null != plan.getChance() && null != plan.getChance().getChcCustName() && 1 < plan.getChance().getChcCustName().length()) {
			hql += " and c.chcCustName like '%"+plan.getChance().getChcCustName()+"%' ";
		}
		
		/* �����ϲ�ѯ */
		if (null != plan && null != plan.getChance() && null != plan.getChance().getChcCustName() && 1 == plan.getChance().getChcCustName().length()) {
			hql += " and c.chcCustName like '"+plan.getChance().getChcCustName()+"%' ";
		}
		
		/* ��chcTitle��ѯ */
		if (null != plan && null != plan.getChance() && null != plan.getChance().getChcTitle() && 0 < plan.getChance().getChcTitle().length()) {
			hql += " and c.chcTitle like '%"+plan.getChance().getChcTitle()+"%' ";
		}
		
		/* �ɹ��� */
		if (null != minRate && 0 < minRate) {
			hql += " and c.chcRate >= "+minRate+" ";
		}
		if (null != maxRate && minRate < maxRate) {
			hql += " and c.chcRate <= "+maxRate+" ";
		}
		
		/* ����ʱ�� */
		/* ����������� */
		if (null != frYYYY && 0 < frYYYY ) {
			if (null != frMM && 0 < frMM) {
				/* �����·�, ����д����(��д��1��)�鵽��ǰ����(��������ǰ��) */
				hql += " and c.chcCreateDate > '"+frYYYY+frMM+"01' ";
				
				/* ���������� */
				if (null != toYYYY && 0 < toYYYY) {
					if (null != toMM && 0 < toMM) {
						/* ����������, �鵽������(�����������·�) */
						hql += " and c.chcCreateDate < '"+toYYYY+(toMM-1)+"31' ";
					} else if (null == toMM || 0 == toMM) {
						/* û��������, �鵽������һ��(������������) */
						/* 2002-1-23------2003()  */
						hql += " and c.chcCreateDate < '"+(toYYYY-1)+"1231' ";
					}
				} else {
					/* û�������� */
					/* �鵽��ǰ */
					hql += " and c.chcCreateDate < '"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"' ";
				}
			} else if (null == frMM || 0 == frMM) {
				/* û���·�, ����д��1�²鵽��ǰ */
				hql += " and c.chcCreateDate > '"+frYYYY+"0101'";
				
				/* ���������� */
				if (null != toYYYY && 0 < toYYYY) {
					if (null != toMM && 0 < toMM) {
						/* ����������, �鵽������(�����������·�) */
						hql += " and c.chcCreateDate < '"+toYYYY+(toMM-1)+"31' ";
					} else if (null == toMM || 0 == toMM) {
						/* û��������, �鵽������һ��(������������) */
						/* 2002-1-23------2003()  */
						hql += " and c.chcCreateDate < '"+toYYYY+"1231' ";
					}
				} else {
					/* û�������� */
					/* �鵽��ǰ */
					hql += " and c.chcCreateDate < '"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"' ";
				}
			}
		} else if (null == frYYYY || 0 == frYYYY) {
			/* û�������� */
			/* �ͻ������������·����� */
			if (null != toYYYY && 0 < toYYYY) {
				/* �������� */
				if (null != toMM && 0 < toMM) {
					/* ��������, �鵽��д��������(����) */
					hql += " and c.chcCreateDate < '"+toYYYY+toMM+"31' ";
				} else if (null == toMM || 0 == toMM) {
					/* û��������, �鵽ǰһ��ȫ�� */
					hql += " and c.chcCreateDate < '"+(toYYYY-1)+"1231' ";
				}
			} 
//				else {
//				/* û���������, �ͻ������� */
//			}
		}
		
		if (null != plan && null != plan.getPlaResult() && 0 < plan.getPlaResult().length()) {
			hql += " and p.plaResult = '"+plan.getPlaResult()+"' ";
		}
		return dao.select(hql);
	}

}
