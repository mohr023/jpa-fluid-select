package com.github.ordnaelmedeiros.jpafluidselect;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.metamodel.SingularAttribute;

public class Order<T, D, R> {

	private R back;
	private From<T, D> from;
	private Select select;

	public Order(Select select, From<T, D> from, R back) {
		this.select = select;
		this.from = from;
		this.back = back;
	}
	
	private CriteriaBuilder b() {
		return select.getBuilder();
	}
	
	public R end() {
		return this.back;
	}
	
	private List<javax.persistence.criteria.Order> orders = new ArrayList<>();
	
	public Order<T,D,R> asc(SingularAttribute<D, ?> attribute) {
		orders.add(b().asc(from.get(attribute)));
		return this;
	}
	public Order<T,D,R> desc(SingularAttribute<D, ?> attribute) {
		orders.add(b().desc(from.get(attribute)));
		return this;
	}

	public boolean isEmpty() {
		return this.orders.isEmpty();
	}

	public List<javax.persistence.criteria.Order> getList() {
		return orders;
	}
	
}