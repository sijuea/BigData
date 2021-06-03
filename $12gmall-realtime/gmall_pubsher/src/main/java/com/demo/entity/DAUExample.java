package com.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class DAUExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DAUExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andMidIsNull() {
            addCriterion("MID is null");
            return (Criteria) this;
        }

        public Criteria andMidIsNotNull() {
            addCriterion("MID is not null");
            return (Criteria) this;
        }

        public Criteria andMidEqualTo(String value) {
            addCriterion("MID =", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidNotEqualTo(String value) {
            addCriterion("MID <>", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidGreaterThan(String value) {
            addCriterion("MID >", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidGreaterThanOrEqualTo(String value) {
            addCriterion("MID >=", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidLessThan(String value) {
            addCriterion("MID <", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidLessThanOrEqualTo(String value) {
            addCriterion("MID <=", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidLike(String value) {
            addCriterion("MID like", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidNotLike(String value) {
            addCriterion("MID not like", value, "mid");
            return (Criteria) this;
        }

        public Criteria andMidIn(List<String> values) {
            addCriterion("MID in", values, "mid");
            return (Criteria) this;
        }

        public Criteria andMidNotIn(List<String> values) {
            addCriterion("MID not in", values, "mid");
            return (Criteria) this;
        }

        public Criteria andMidBetween(String value1, String value2) {
            addCriterion("MID between", value1, value2, "mid");
            return (Criteria) this;
        }

        public Criteria andMidNotBetween(String value1, String value2) {
            addCriterion("MID not between", value1, value2, "mid");
            return (Criteria) this;
        }

        public Criteria andLogdateIsNull() {
            addCriterion("LOGDATE is null");
            return (Criteria) this;
        }

        public Criteria andLogdateIsNotNull() {
            addCriterion("LOGDATE is not null");
            return (Criteria) this;
        }

        public Criteria andLogdateEqualTo(String value) {
            addCriterion("LOGDATE =", value, "logdate");
            return (Criteria) this;
        }

        public Criteria andLogdateNotEqualTo(String value) {
            addCriterion("LOGDATE <>", value, "logdate");
            return (Criteria) this;
        }

        public Criteria andLogdateGreaterThan(String value) {
            addCriterion("LOGDATE >", value, "logdate");
            return (Criteria) this;
        }

        public Criteria andLogdateGreaterThanOrEqualTo(String value) {
            addCriterion("LOGDATE >=", value, "logdate");
            return (Criteria) this;
        }

        public Criteria andLogdateLessThan(String value) {
            addCriterion("LOGDATE <", value, "logdate");
            return (Criteria) this;
        }

        public Criteria andLogdateLessThanOrEqualTo(String value) {
            addCriterion("LOGDATE <=", value, "logdate");
            return (Criteria) this;
        }

        public Criteria andLogdateLike(String value) {
            addCriterion("LOGDATE like", value, "logdate");
            return (Criteria) this;
        }

        public Criteria andLogdateNotLike(String value) {
            addCriterion("LOGDATE not like", value, "logdate");
            return (Criteria) this;
        }

        public Criteria andLogdateIn(List<String> values) {
            addCriterion("LOGDATE in", values, "logdate");
            return (Criteria) this;
        }

        public Criteria andLogdateNotIn(List<String> values) {
            addCriterion("LOGDATE not in", values, "logdate");
            return (Criteria) this;
        }

        public Criteria andLogdateBetween(String value1, String value2) {
            addCriterion("LOGDATE between", value1, value2, "logdate");
            return (Criteria) this;
        }

        public Criteria andLogdateNotBetween(String value1, String value2) {
            addCriterion("LOGDATE not between", value1, value2, "logdate");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("UID is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("UID is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(String value) {
            addCriterion("UID =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(String value) {
            addCriterion("UID <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(String value) {
            addCriterion("UID >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(String value) {
            addCriterion("UID >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(String value) {
            addCriterion("UID <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(String value) {
            addCriterion("UID <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLike(String value) {
            addCriterion("UID like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotLike(String value) {
            addCriterion("UID not like", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<String> values) {
            addCriterion("UID in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<String> values) {
            addCriterion("UID not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(String value1, String value2) {
            addCriterion("UID between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(String value1, String value2) {
            addCriterion("UID not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andAppidIsNull() {
            addCriterion("APPID is null");
            return (Criteria) this;
        }

        public Criteria andAppidIsNotNull() {
            addCriterion("APPID is not null");
            return (Criteria) this;
        }

        public Criteria andAppidEqualTo(String value) {
            addCriterion("APPID =", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidNotEqualTo(String value) {
            addCriterion("APPID <>", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidGreaterThan(String value) {
            addCriterion("APPID >", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidGreaterThanOrEqualTo(String value) {
            addCriterion("APPID >=", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidLessThan(String value) {
            addCriterion("APPID <", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidLessThanOrEqualTo(String value) {
            addCriterion("APPID <=", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidLike(String value) {
            addCriterion("APPID like", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidNotLike(String value) {
            addCriterion("APPID not like", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidIn(List<String> values) {
            addCriterion("APPID in", values, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidNotIn(List<String> values) {
            addCriterion("APPID not in", values, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidBetween(String value1, String value2) {
            addCriterion("APPID between", value1, value2, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidNotBetween(String value1, String value2) {
            addCriterion("APPID not between", value1, value2, "appid");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("AREA is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("AREA is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("AREA =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("AREA <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("AREA >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("AREA >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("AREA <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("AREA <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("AREA like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("AREA not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("AREA in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("AREA not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("AREA between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("AREA not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andOsIsNull() {
            addCriterion("OS is null");
            return (Criteria) this;
        }

        public Criteria andOsIsNotNull() {
            addCriterion("OS is not null");
            return (Criteria) this;
        }

        public Criteria andOsEqualTo(String value) {
            addCriterion("OS =", value, "os");
            return (Criteria) this;
        }

        public Criteria andOsNotEqualTo(String value) {
            addCriterion("OS <>", value, "os");
            return (Criteria) this;
        }

        public Criteria andOsGreaterThan(String value) {
            addCriterion("OS >", value, "os");
            return (Criteria) this;
        }

        public Criteria andOsGreaterThanOrEqualTo(String value) {
            addCriterion("OS >=", value, "os");
            return (Criteria) this;
        }

        public Criteria andOsLessThan(String value) {
            addCriterion("OS <", value, "os");
            return (Criteria) this;
        }

        public Criteria andOsLessThanOrEqualTo(String value) {
            addCriterion("OS <=", value, "os");
            return (Criteria) this;
        }

        public Criteria andOsLike(String value) {
            addCriterion("OS like", value, "os");
            return (Criteria) this;
        }

        public Criteria andOsNotLike(String value) {
            addCriterion("OS not like", value, "os");
            return (Criteria) this;
        }

        public Criteria andOsIn(List<String> values) {
            addCriterion("OS in", values, "os");
            return (Criteria) this;
        }

        public Criteria andOsNotIn(List<String> values) {
            addCriterion("OS not in", values, "os");
            return (Criteria) this;
        }

        public Criteria andOsBetween(String value1, String value2) {
            addCriterion("OS between", value1, value2, "os");
            return (Criteria) this;
        }

        public Criteria andOsNotBetween(String value1, String value2) {
            addCriterion("OS not between", value1, value2, "os");
            return (Criteria) this;
        }

        public Criteria andChIsNull() {
            addCriterion("CH is null");
            return (Criteria) this;
        }

        public Criteria andChIsNotNull() {
            addCriterion("CH is not null");
            return (Criteria) this;
        }

        public Criteria andChEqualTo(String value) {
            addCriterion("CH =", value, "ch");
            return (Criteria) this;
        }

        public Criteria andChNotEqualTo(String value) {
            addCriterion("CH <>", value, "ch");
            return (Criteria) this;
        }

        public Criteria andChGreaterThan(String value) {
            addCriterion("CH >", value, "ch");
            return (Criteria) this;
        }

        public Criteria andChGreaterThanOrEqualTo(String value) {
            addCriterion("CH >=", value, "ch");
            return (Criteria) this;
        }

        public Criteria andChLessThan(String value) {
            addCriterion("CH <", value, "ch");
            return (Criteria) this;
        }

        public Criteria andChLessThanOrEqualTo(String value) {
            addCriterion("CH <=", value, "ch");
            return (Criteria) this;
        }

        public Criteria andChLike(String value) {
            addCriterion("CH like", value, "ch");
            return (Criteria) this;
        }

        public Criteria andChNotLike(String value) {
            addCriterion("CH not like", value, "ch");
            return (Criteria) this;
        }

        public Criteria andChIn(List<String> values) {
            addCriterion("CH in", values, "ch");
            return (Criteria) this;
        }

        public Criteria andChNotIn(List<String> values) {
            addCriterion("CH not in", values, "ch");
            return (Criteria) this;
        }

        public Criteria andChBetween(String value1, String value2) {
            addCriterion("CH between", value1, value2, "ch");
            return (Criteria) this;
        }

        public Criteria andChNotBetween(String value1, String value2) {
            addCriterion("CH not between", value1, value2, "ch");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("TYPE =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("TYPE <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("TYPE >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TYPE >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("TYPE <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("TYPE <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("TYPE like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("TYPE not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("TYPE in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("TYPE not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("TYPE between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("TYPE not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andVsIsNull() {
            addCriterion("VS is null");
            return (Criteria) this;
        }

        public Criteria andVsIsNotNull() {
            addCriterion("VS is not null");
            return (Criteria) this;
        }

        public Criteria andVsEqualTo(String value) {
            addCriterion("VS =", value, "vs");
            return (Criteria) this;
        }

        public Criteria andVsNotEqualTo(String value) {
            addCriterion("VS <>", value, "vs");
            return (Criteria) this;
        }

        public Criteria andVsGreaterThan(String value) {
            addCriterion("VS >", value, "vs");
            return (Criteria) this;
        }

        public Criteria andVsGreaterThanOrEqualTo(String value) {
            addCriterion("VS >=", value, "vs");
            return (Criteria) this;
        }

        public Criteria andVsLessThan(String value) {
            addCriterion("VS <", value, "vs");
            return (Criteria) this;
        }

        public Criteria andVsLessThanOrEqualTo(String value) {
            addCriterion("VS <=", value, "vs");
            return (Criteria) this;
        }

        public Criteria andVsLike(String value) {
            addCriterion("VS like", value, "vs");
            return (Criteria) this;
        }

        public Criteria andVsNotLike(String value) {
            addCriterion("VS not like", value, "vs");
            return (Criteria) this;
        }

        public Criteria andVsIn(List<String> values) {
            addCriterion("VS in", values, "vs");
            return (Criteria) this;
        }

        public Criteria andVsNotIn(List<String> values) {
            addCriterion("VS not in", values, "vs");
            return (Criteria) this;
        }

        public Criteria andVsBetween(String value1, String value2) {
            addCriterion("VS between", value1, value2, "vs");
            return (Criteria) this;
        }

        public Criteria andVsNotBetween(String value1, String value2) {
            addCriterion("VS not between", value1, value2, "vs");
            return (Criteria) this;
        }

        public Criteria andLoghourIsNull() {
            addCriterion("LOGHOUR is null");
            return (Criteria) this;
        }

        public Criteria andLoghourIsNotNull() {
            addCriterion("LOGHOUR is not null");
            return (Criteria) this;
        }

        public Criteria andLoghourEqualTo(String value) {
            addCriterion("LOGHOUR =", value, "loghour");
            return (Criteria) this;
        }

        public Criteria andLoghourNotEqualTo(String value) {
            addCriterion("LOGHOUR <>", value, "loghour");
            return (Criteria) this;
        }

        public Criteria andLoghourGreaterThan(String value) {
            addCriterion("LOGHOUR >", value, "loghour");
            return (Criteria) this;
        }

        public Criteria andLoghourGreaterThanOrEqualTo(String value) {
            addCriterion("LOGHOUR >=", value, "loghour");
            return (Criteria) this;
        }

        public Criteria andLoghourLessThan(String value) {
            addCriterion("LOGHOUR <", value, "loghour");
            return (Criteria) this;
        }

        public Criteria andLoghourLessThanOrEqualTo(String value) {
            addCriterion("LOGHOUR <=", value, "loghour");
            return (Criteria) this;
        }

        public Criteria andLoghourLike(String value) {
            addCriterion("LOGHOUR like", value, "loghour");
            return (Criteria) this;
        }

        public Criteria andLoghourNotLike(String value) {
            addCriterion("LOGHOUR not like", value, "loghour");
            return (Criteria) this;
        }

        public Criteria andLoghourIn(List<String> values) {
            addCriterion("LOGHOUR in", values, "loghour");
            return (Criteria) this;
        }

        public Criteria andLoghourNotIn(List<String> values) {
            addCriterion("LOGHOUR not in", values, "loghour");
            return (Criteria) this;
        }

        public Criteria andLoghourBetween(String value1, String value2) {
            addCriterion("LOGHOUR between", value1, value2, "loghour");
            return (Criteria) this;
        }

        public Criteria andLoghourNotBetween(String value1, String value2) {
            addCriterion("LOGHOUR not between", value1, value2, "loghour");
            return (Criteria) this;
        }

        public Criteria andTsIsNull() {
            addCriterion("TS is null");
            return (Criteria) this;
        }

        public Criteria andTsIsNotNull() {
            addCriterion("TS is not null");
            return (Criteria) this;
        }

        public Criteria andTsEqualTo(Long value) {
            addCriterion("TS =", value, "ts");
            return (Criteria) this;
        }

        public Criteria andTsNotEqualTo(Long value) {
            addCriterion("TS <>", value, "ts");
            return (Criteria) this;
        }

        public Criteria andTsGreaterThan(Long value) {
            addCriterion("TS >", value, "ts");
            return (Criteria) this;
        }

        public Criteria andTsGreaterThanOrEqualTo(Long value) {
            addCriterion("TS >=", value, "ts");
            return (Criteria) this;
        }

        public Criteria andTsLessThan(Long value) {
            addCriterion("TS <", value, "ts");
            return (Criteria) this;
        }

        public Criteria andTsLessThanOrEqualTo(Long value) {
            addCriterion("TS <=", value, "ts");
            return (Criteria) this;
        }

        public Criteria andTsIn(List<Long> values) {
            addCriterion("TS in", values, "ts");
            return (Criteria) this;
        }

        public Criteria andTsNotIn(List<Long> values) {
            addCriterion("TS not in", values, "ts");
            return (Criteria) this;
        }

        public Criteria andTsBetween(Long value1, Long value2) {
            addCriterion("TS between", value1, value2, "ts");
            return (Criteria) this;
        }

        public Criteria andTsNotBetween(Long value1, Long value2) {
            addCriterion("TS not between", value1, value2, "ts");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}