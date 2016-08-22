package com.cathham.train.study.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

import com.cathham.common.utils.PagerList;

/**
 * 基础数据库操作类
 * 
 * @author user
 */
@SuppressWarnings(value = { "rawtypes" })
public interface BaseDao<T> {

	/**
	 * 保存一个对象
	 * 
	 * @param o
	 * @return void
	 */
	public void save(T o);

	/**
	 * 删除一个对象
	 * 
	 * @param T 　对象
	 * @return void
	 */
	public void delete(Serializable uid);

	/**
	 * 更新一个对象
	 * 
	 * @param T 对象
	 * @return void
	 */
	public void update(T o);

	/**
	 * 保存或更新对象
	 * 
	 * @param T 对象
	 * @return void
	 */
	public void saveOrUpdate(T o);

	public void merge(T o);

	/**
	 * 获得一个对象
	 * 
	 * @param c 对象类型
	 * @param id
	 * @return T 对象
	 */
	public T get(Serializable id);

	/**
	 * 执行HQL语句，用于批量修改和批量删除
	 * 
	 * @param hql
	 * @param param
	 * @return
	 */
	public Integer executeHqlByList(String hql, Map param);

	/**
	 * 通过实体的字段查询
	 * 
	 * @param pageindex
	 * @param associationPaths 级联的表属性
	 * @param param 查询条件，为空时不查询。
	 * @return 分页列表
	 */
	public PagerList<T> Find(int pageindex, String[] associationPaths, T param);

	public PagerList<T> Find(int pageindex, String[] associationPaths, Order o, Map<String, Criterion[]> unionCriterions,
			Criterion... criterions);

	/**
	 * 通过Criterion查询
	 * 
	 * @param pageindex
	 * @param associationPaths
	 * @param criterions 用法为：Restrictions.eq("good", 2),Restrictions.between("focus", 0, 5)
	 * @return
	 */
	public PagerList<T> Find(int pageindex, String[] associationPaths, Criterion... criterions);

	public PagerList<T> Find(int pageindex, String[] associationPaths, Order o, Criterion... criterions);

	/**
	 * 通过配置在实体类的命名查询查询
	 * 
	 * @param pageindex
	 * @param hqlName 命名查询名称
	 * @param param 查询条件集合
	 * @return
	 */
	public PagerList<T> Find(int pageindex, String hqlName, Map param);

	/**
	 * 查询
	 * 
	 * @param hql
	 * @return List<T>
	 */
	public List<T> findList(String[] associationPaths, T param);

	public List<T> findList(String[] associationPaths, Criterion... criterions);

	public List<T> findList(String hqlName, Map param);

	public PagerList<T> Find(int pageindex, String[] associationPaths, T param, Criterion... criterions);

	public Integer executeSQL(String sql, Map param);

	/**
	 * 执行HQL语句
	 * 
	 * @param hql
	 * @return 响应数目
	 */
	public Integer executeHql(String hql);

	/**
	 * 执行HQL语句
	 * 
	 * @param hql
	 * @param param
	 * @return 响应数目
	 */
	public Integer executeHql(String hql, Map param);

	/**
	 * 通过实体的字段查询或通过Criterion查询
	 * 
	 * @param associationPaths *@param param 查询条件，为空时不查询。
	 * @param criterions 用法为：Restrictions.eq("good", 2),Restrictions.between("focus", 0, 5)
	 * @return
	 */
	public T findObject(String[] associationPaths, T param, Criterion... criterions);
}
