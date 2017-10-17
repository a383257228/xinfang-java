
package com.sinosoft.authority.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.engine.TypedValue;
import org.hibernate.loader.criteria.CriteriaQueryTranslator;
import org.hibernate.type.Type;
import org.hibernate.util.ArrayHelper;
import org.hibernate.util.StringHelper;

public class SQLAliasedCriterion implements org.hibernate.criterion.Criterion {
  private static final long serialVersionUID = 1L;

  private final String sql;
  private final TypedValue[] typedValues;

  public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery) throws HibernateException {
    return applyAliases(criteria, criteriaQuery);
  }

  private String applyAliases(Criteria criteria, CriteriaQuery criteriaQuery) {
    StringBuilder res = new StringBuilder();
    String src = StringHelper.replace(sql, "{alias}", criteriaQuery.getSQLAlias(criteria));
    int i = 0;
    int cnt = src.length();
    while (i < cnt) {
      int l = src.indexOf('{', i);
      if (l == -1) {
        break;
      }

      String before = src.substring(i, l);
      res.append(before);

      int r = src.indexOf('}', l);
      String alias = src.substring(l + 1, r);
      if (alias.length() == 0) {
        alias = "this_";
      }
      Criteria cri = null;
      // get criteria using it's path
      cri = ((org.hibernate.loader.criteria.CriteriaQueryTranslator) criteriaQuery).getCriteria(alias);
      if (cri == null) {
        // if not found - get criteria using it's alias
        //        cri = ((org.hibernate.loader.criteria.CriteriaQueryTranslator) criteriaQuery).getAliasedCriteria(alias);
        cri = this.getAliasedCriteria(alias, criteriaQuery);
      }
      if (cri != null) {
        String sqlAlias = criteriaQuery.getSQLAlias(cri);
        res.append(sqlAlias);
      } else {
        // try to search criteria or alias in parent criteria (if we are in subcriteria)
        CriteriaQuery outerQueryTranslator = this.getOuterQueryTranslator((org.hibernate.loader.criteria.CriteriaQueryTranslator) criteriaQuery);
        if (outerQueryTranslator != null) {
          // get criteria using it's path
          cri = ((org.hibernate.loader.criteria.CriteriaQueryTranslator) outerQueryTranslator).getCriteria(alias);
          if (cri == null) {
            // if not found - get criteria using it's alias
            //            cri = ((org.hibernate.loader.criteria.CriteriaQueryTranslator) outerQueryTranslator).getAliasedCriteria(alias);
            cri = this.getAliasedCriteria(alias, outerQueryTranslator);

          }
          if (cri != null) {
            String sqlAlias = ((org.hibernate.loader.criteria.CriteriaQueryTranslator) outerQueryTranslator).getSQLAlias(cri);
            res.append(sqlAlias);
          } else {
            res.append(alias);
          }
        } else {
          res.append(alias);
        }
      }
      i = r + 1;
    }
    String after = src.substring(i, cnt);
    res.append(after);

    return res.toString();
  }

  //  public CriteriaQuery getOuterQueryTranslator() { return this.outerQueryTranslator; }

  protected CriteriaQuery getOuterQueryTranslator(CriteriaQueryTranslator criteriaQueryTranslator) {
    try {
      Field f = CriteriaQueryTranslator.class.getDeclaredField("outerQueryTranslator");
      f.setAccessible(true);
      return (CriteriaQuery) f.get(criteriaQueryTranslator);
    } catch (Exception e) {
      System.err.println("Unexpected Exception: " + e);
      throw new RuntimeException(e);
    }
  }

  protected Criteria getAliasedCriteria(String alias, CriteriaQuery criteriaQuery) {
    org.hibernate.loader.criteria.CriteriaQueryTranslator cqt = (org.hibernate.loader.criteria.CriteriaQueryTranslator) criteriaQuery;

    try {
      Method m = cqt.getClass().getDeclaredMethod("getAliasedCriteria", String.class);
      m.setAccessible(true);
      return (Criteria) m.invoke(cqt, alias);
    } catch (Exception e) {
      System.err.println("Unexpected Exception: " + e);
      throw new RuntimeException(e);
    }
  }

  public TypedValue[] getTypedValues( Criteria criteria,  CriteriaQuery criteriaQuery) throws HibernateException {
    return typedValues;
  }

  @Override
  public String toString() {
    return sql;
  }

  public SQLAliasedCriterion(String sql, Object[] values, Type[] types) {
    this.sql = sql;
    typedValues = new TypedValue[values.length];
    for (int i = 0; i < typedValues.length; i++) {
      typedValues[i] = new TypedValue(types[i], values[i], EntityMode.POJO);
    }
  }

  public SQLAliasedCriterion(String sql, Object value, Type type) {
    this(sql, new Object[] { value }, new Type[] { type });
  }

  public SQLAliasedCriterion(String sql) {
		this(sql, ArrayHelper.EMPTY_OBJECT_ARRAY, ArrayHelper.EMPTY_TYPE_ARRAY);
	}
}
