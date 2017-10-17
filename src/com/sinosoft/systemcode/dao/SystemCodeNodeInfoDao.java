package com.sinosoft.systemcode.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.sinosoft.systemcode.domain.SystemCodeNodeInfo;
import com.sinosoftframework.core.hiberante.HibernateDao;
import com.sinosoftframework.core.utils.bean.BeanUtils;
import com.sinosoftframework.core.utils.page.ExtjsPage;

@Repository
public class SystemCodeNodeInfoDao extends HibernateDao<SystemCodeNodeInfo, String>
{

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public List<SystemCodeNodeInfo> find(String hql)
  {
    return createQuery(hql, new Object[0]).list();
  }

  public int getNodeCount(String codeId)
  {
    Map values = new HashMap();
    values.put("systemCodeId", codeId);
    Query query = createQuery("from SystemCodeNodeInfo where systemcode.id=:systemCodeId", 
      values);
    return query.list().size();
  }

  public ExtjsPage<SystemCodeNodeInfo> listQuery(Criteria criteria, int pageNo, int pageSize)
    throws NoSuchFieldException
  {
    Assert.notNull(criteria);
    Assert.isTrue(pageNo >= 1, "pageNo should be eg 1");
    CriteriaImpl impl = null;
    if ((criteria instanceof CriteriaImpl))
      impl = (CriteriaImpl)criteria;
    else {
      Assert.isTrue(criteria instanceof CriteriaImpl);
    }
    Projection projection = impl.getProjection();

    List orderEntries = (List)BeanUtils.forceGetProperty(impl, "orderEntries");
    BeanUtils.forceSetProperty(impl, "orderEntries", new ArrayList());
    Integer totalCount = getCount(criteria);
    criteria.setProjection(projection);
    if (projection == null) {
      criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
    }
    BeanUtils.forceSetProperty(impl, "orderEntries", orderEntries);

    if (totalCount.intValue() < 1) {
      return new ExtjsPage();
    }

    int start = (pageNo - 1) * pageSize;
    List result = 
      criteria.setFirstResult(start).setMaxResults(pageSize).list();
    List nodeResult = 
      new ArrayList();
    for (int i = 0; i < result.size(); i++) {
      SystemCodeNodeInfo scni = (SystemCodeNodeInfo)result.get(i);
      nodeResult.addAll(getChilds(scni));
    }
    ExtjsPage page = 
      new ExtjsPage();
    page.setResult(nodeResult);
    page.setTotalCount(nodeResult.size());
    return page;
  }

  public List<SystemCodeNodeInfo> getChilds(SystemCodeNodeInfo systemCodeNode)
  {
    List result = 
      new ArrayList();
    result.add(systemCodeNode);
    Set childs = systemCodeNode.getChildNodes();
    Iterator iterator = childs.iterator();
    while (iterator.hasNext()) {
      SystemCodeNodeInfo scni = (SystemCodeNodeInfo)iterator.next();
      result.addAll(getChilds(scni));

      if (scni.getParentId() != null) {
        Map map = new HashMap();
        map.put("codeTypeId", scni.getSystemcode().getId());
        map.put("codeId", scni.getParentId());
        String name = ((SystemCodeNodeInfo)findUnique(
          "From SystemCodeNodeInfo Where systemcode.id=:codeTypeId and id=:codeId", 
          map)).getName();

        scni.setParentName(name);
      } else {
        scni.setParentName(scni.getSystemcode().getName());
      }
    }
    return result;
  }

  public void deleteNodeById(String codeId)
  {
    Map values = new HashMap();
    values.put("oid", codeId);
    batchExecute("delete from SystemCodeNodeInfo Where id=:oid", 
      values);
  }

  public List<SystemCodeNodeInfo> querySystemCodeNodeInfoRecord(String type)
  {
    Criterion criterionForType = 
      Restrictions.eq("codeType", type);

    return find(new Criterion[] { criterionForType });
  }

	public void updateCodeNodeSequence(final List syscniList,
			final String codeType, final String code, final Boolean isPlus) {
		String sql = "UPDATE System_Code_Node SET Sequence = ? WHERE Code_Type = ? AND Code = ? AND Code != ?";
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pst, int i)
					throws SQLException {
				Map a = (Map) syscniList.get(i);
				String codeNode = a.get("code").toString();
				Integer seq = Integer.valueOf(Integer.parseInt(a.get("seq")
						.toString()));
				if (isPlus.booleanValue())
					pst.setInt(1, seq.intValue() + 1);
				else
					pst.setInt(1, seq.intValue() - 1);
				pst.setString(2, codeType);
				pst.setString(3, codeNode);
				pst.setString(4, code);
			}
			public int getBatchSize() {
				return syscniList.size();
			}
		});
	}
	/**
	 * 根据issueTypeCode的查询父类的code
	 * @author guoh
	 * @date 2016-12-28
	 * @param codeType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SystemCodeNodeInfo> getByCodeType(String codeType){
		Session session = this.getSession();
		StringBuffer  hql = new StringBuffer();
		hql.append("from SystemCodeNodeInfo s where s.id =(");
		hql.append("select parentId from SystemCodeNodeInfo c");
		hql.append(" where c.code ='").append(codeType).append("')");
		Query query = session.createQuery(hql.toString());
		return query.list();
	}
}
