package com.cathham.train.study.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.transaction.annotation.Transactional;

import com.cathham.common.utils.PagerList;
import com.cathham.train.study.dao.BaseDao;

@SuppressWarnings(value = {"rawtypes","unchecked"})
@Transactional
public class BaseDaoImpl<T> implements BaseDao<T> {

	@Resource
	private SessionFactory	sessionFactory;

	public void save(T o) {
		sessionFactory.getCurrentSession().save(o);
	}

	public void delete(Serializable uid) {
		T t = this.get(uid);
		sessionFactory.getCurrentSession().delete(t);
	}

	public void update(T o) {
		sessionFactory.getCurrentSession().update(o);
	}

	public void saveOrUpdate(T o) {
		sessionFactory.getCurrentSession().saveOrUpdate(o);
	}

	public void merge(T o) {
		sessionFactory.getCurrentSession().merge(o);
	}

	public T get(Serializable id) {
		Session session = sessionFactory.openSession();
		T t = (T) session.get(this.getGenericType(0), id);
		session.close();
		return t;
	}

	public int count(String hqlName, Map param) {
		Session session = sessionFactory.openSession();
		Query query = session.getNamedQuery(hqlName);
		Iterator it = param.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			query.setParameter(key.toString(), param.get(key));
		}

		int count = Integer.parseInt(query.uniqueResult().toString());
		session.close();
		return count;
	}

	public PagerList<T> Find(int pageindex, String hqlName, Map param) {
		if (pageindex == 0) {
			pageindex = 1;
		}

		PagerList<T> plist = new PagerList<T>();
		// 获取记录总数
		plist.setTotal(count(hqlName + "Count", param));

		Session session = sessionFactory.openSession();
		Query query = session.getNamedQuery(hqlName);
		Iterator it = param.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			query.setParameter(key.toString(), param.get(key));
		}
		query.setFirstResult((pageindex - 1) * plist.getPageSize());
		query.setMaxResults(plist.getPageSize());
		plist.setData(query.list());
		session.close();
		return plist;
	}

	/**
	 * @param pageindex
	 * @param associationPaths
	 * @param unionCriterions
	 * @param criterions
	 * @return query.createCriteria("account", "a").add( Restrictions.eq("a.account_id", 112546)).add(
	 *         Restrictions.eq("a.start_date", "2008-8-30")).add( Restrictions.eq("a.money_sum", 1000));
	 */
	public PagerList<T> Find(int pageindex, String[] associationPaths, Order o, Map<String, Criterion[]> unionCriterions,
			Criterion... criterions) {
		if (pageindex == 0) {
			pageindex = 1;
		}
		Session session = sessionFactory.openSession();
		Criteria query = session.createCriteria(getGenericType(0));
		if (unionCriterions != null) {

			for (Map.Entry<String, Criterion[]> entry : unionCriterions.entrySet()) {
				query.createCriteria(entry.getKey(), entry.getKey());
				Criterion[] cr = entry.getValue();
				if (cr != null) {
					for (Criterion criterion : cr) {
						if (criterion != null) {
							query.add(criterion);
						}
					}
				}
			}
		}
		if (o != null) {
			query.addOrder(o);
		}
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				if (criterion != null) {
					query.add(criterion);

				}
			}
		}

		PagerList<T> plist = GetPagerList(pageindex, associationPaths, query);
		session.close();
		return plist;
	}

	public PagerList<T> Find(int pageindex, String[] associationPaths, Criterion... criterions) {
		Order o = Order.asc("");
		o = null;
		return Find(pageindex, associationPaths, o, criterions);
	}

	@Override
	public PagerList<T> Find(int pageindex, String[] associationPaths, Order o, Criterion... criterions) {
		if (pageindex == 0) {
			pageindex = 1;
		}
		Session session = sessionFactory.openSession();
		Criteria query = session.createCriteria(getGenericType(0));

		if (criterions != null) {
			for (Criterion criterion : criterions) {
				if (criterion != null) {
					query.add(criterion);

				}
			}
		}
		if (o != null) {
			query.addOrder(o);
		}
		PagerList<T> plist = GetPagerList(pageindex, associationPaths, query);
		session.close();
		return plist;
	}

	/**
	 * 模糊查询的列用"%%"串起来，不然仍然和"="一样。
	 */
	public PagerList<T> Find(int pageindex, String[] associationPaths, T param) {
		if (pageindex == 0) {
			pageindex = 1;
		}
		Session session = sessionFactory.openSession();

		Criteria query = session.createCriteria(getGenericType(0));
		query.add(Example.create(param).excludeZeroes().enableLike());

		PagerList<T> plist = GetPagerList(pageindex, associationPaths, query);
		session.close();

		return plist;
	}

	private PagerList<T> GetPagerList(int pageindex, String[] associationPaths, Criteria query) {
		if (pageindex == 0) {
			pageindex = 1;
		}
		PagerList<T> plist = new PagerList<T>();
		// 获取记录总数
		Object o = query.setProjection(Projections.rowCount()).uniqueResult();
		Long rowCount = (Long) o;

		query.setProjection(null);
		plist.setTotal(Integer.parseInt(rowCount.toString()));
		plist.setCurrentPage(pageindex);

		int d = plist.getTotal() / plist.getPageSize();
		int i = plist.getTotal() % plist.getPageSize();

		if (i > 0) {
			plist.setTotalPage(d + 1);
		} else {
			plist.setTotalPage(d);
		}

		if (associationPaths != null) {
			for (String path : associationPaths) {
				if (path != null) {
					query.setFetchMode(path, FetchMode.JOIN);
				}
			}
		}
		query.setFirstResult((pageindex - 1) * plist.getPageSize());
		query.setMaxResults(plist.getPageSize());
		List li = query.list();
		List li2 = new ArrayList();
		if (li.size() != 0 && li.get(0).getClass() == Object[].class) {
			System.out.println(li.get(0).getClass());
			for (int ii = 0; ii < li.size(); ii++) {
				Object[] obs = (Object[]) li.get(ii);
				li2.add(obs[1]);
			}
			plist.setData(li2);
		} else {

			plist.setData(li);
		}

		return plist;
	}

	// public int count(String hql) {
	// return Integer.valueOf(sessionFactory.getCurrentSession().createQuery("select count(*)"+hql).uniqueResult().toString());
	// }

	@Override
	public Integer executeHqlByList(String hql, Map param) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		if (param != null && param.size() > 0) {
			Iterator it = param.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				query.setParameter(key.toString(), param.get(key));
			}
		}
		Integer i = query.executeUpdate();
		session.close();
		return i;
	}

	/**
	 * 获取泛型类 T 具体类
	 * 
	 * @param index=0
	 * @return Class对象
	 */
	public Class getGenericType(int index) {
		Type genType = getClass().getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			throw new RuntimeException("Index outof bounds");
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}

	@Override
	public List<T> findList(String[] associationPaths, T param) {
		Session session = sessionFactory.openSession();
		Criteria query = session.createCriteria(getGenericType(0));
		query.add(Example.create(param).excludeZeroes().enableLike());
		if (associationPaths != null) {
			for (String path : associationPaths) {
				if (path != null) {
					query.setFetchMode(path, FetchMode.JOIN);
				}
			}
		}
		List list = query.list();
		session.close();
		return list;
	}

	@Override
	public List<T> findList(String[] associationPaths, Criterion... criterions) {
		Session session = sessionFactory.openSession();
		Criteria query = session.createCriteria(getGenericType(0));

		if (criterions != null) {
			for (Criterion criterion : criterions) {
				if (criterion != null) {
					query.add(criterion);
				}
			}
		}
		if (associationPaths != null) {
			for (String path : associationPaths) {
				if (path != null) {
					query.setFetchMode(path, FetchMode.JOIN);
				}
			}
		}
		List list = query.list();
		session.close();
		return list;
	}

	@Override
	public List<T> findList(String hqlName, Map param) {
		Session session = sessionFactory.openSession();
		Query query = session.getNamedQuery(hqlName);
		Iterator it = param.keySet().iterator();
		while (it.hasNext()) {
			Object key = it.next();
			query.setParameter(key.toString(), param.get(key));
		}
		List list = query.list();
		session.close();
		return list;
	}

	@Override
	public PagerList<T> Find(int pageindex, String[] associationPaths, T param, Criterion... criterions) {
		Session session = sessionFactory.openSession();
		Criteria query = session.createCriteria(getGenericType(0));
		query.add(Example.create(param).enableLike());
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				if (criterion != null) {
					query.add(criterion);

				}
			}
		}
		PagerList<T> list = GetPagerList(pageindex, associationPaths, query);
		session.close();
		return list;
	}

	public Integer executeSQL(String sql, Map param) {
		Session session = sessionFactory.openSession();
		// Query q=session.createQuery(sql);
		Query q = session.getNamedQuery(sql);
		if (param != null && param.size() > 0) {
			Iterator it = param.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				q.setParameter(key.toString(), param.get(key));
			}
		}
		// q.executeUpdate();
		Double i = (Double) q.uniqueResult();
		if (i == null) {
			return null;
		} else {
			Integer a = Integer.parseInt(new java.text.DecimalFormat("0").format(i));
			System.out.println(i);
			session.close();
			return a;
		}
	}

	public Integer executeHql(String hql) {
		return sessionFactory.getCurrentSession().createQuery(hql).executeUpdate();
	}

	public Integer executeHql(String hql, Map param) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.getNamedQuery(hql);
		if (param != null && param.size() > 0) {
			Iterator it = param.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				q.setParameter(key.toString(), param.get(key));
			}
		}
		Integer result = q.executeUpdate();
		return result;
	}

	@Override
	public T findObject(String[] associationPaths, T param, Criterion... criterions) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(getGenericType(0));
		query.add(Example.create(param).enableLike());
		if (criterions != null) {
			for (Criterion criterion : criterions) {
				if (criterion != null) {
					query.add(criterion);
				}
			}
		}
		return (T) query.uniqueResult();
	}

}
