package com.github.ordnaelmedeiros.jpafluidselect;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.criteria.Join;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.github.ordnaelmedeiros.jpafluidselect.base.SelectTestBase;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address;
import com.github.ordnaelmedeiros.jpafluidselect.models.Address_;
import com.github.ordnaelmedeiros.jpafluidselect.models.People;
import com.github.ordnaelmedeiros.jpafluidselect.models.People_;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupBySelectTest extends SelectTestBase {
	
	private Object[] leandro;
	private Join<People, Address> joinAddress;

	@Test
	public void testCount() {
		
		List<Object[]> list = new Select(em)
			.fromMultiSelect(People.class)
			.fields()
				.add(People_.name)
				.count(People_.id)
			.end()
			.group()
				.add(People_.name)
			.end()
			.orderAsc(People_.name)
			.getResultList();
		
		
		list.stream().forEach(o -> {
			//System.out.println(String.format("%10s sum: %02d", o));
			if (o[0].equals("Leandro")) {
				this.leandro = o;
			}
		});
		
		assertEquals(2l, this.leandro[1]);
		
	}
	
	@Test
	public void testSum() {
		
		List<Object[]> list = new Select(em)
			.fromMultiSelect(People.class)
			.fields()
				.add(People_.name)
				.sum(People_.id)
			.end()
			.group()
				.add(People_.name)
			.end()
			.orderAsc(People_.name)
			.getResultList();
		
		list.stream().forEach(o -> {
			//System.out.println(String.format("%10s sum: %02d", o));
			if (o[0].equals("Leandro")) {
				this.leandro = o;
			}
		});
		
		assertEquals(4l, this.leandro[1]);
		
	}
	
	@Test
	public void testCountByStreet() {
		
		List<Object[]> list = new Select(em)
			.fromMultiSelect(People.class)
			.join(People_.address).extractJoin(j -> this.joinAddress = j).end()
			.fields()
				.add(this.joinAddress, Address_.street)
				.count(People_.id)
			.end()
			.group()
				.add(this.joinAddress, Address_.street)
			.end()
			.order()
				.asc(this.joinAddress, Address_.street)
			.end()
			.getResultList();
		
		/*
		list.stream().forEach(o -> {
			System.out.println(String.format("%10s sum: %02d", o));
		});
		*/
		assertEquals(4l, list.size());
		
		assertEquals("One", list.get(0)[0]);
		assertEquals(2l, list.get(0)[1]);
		
		assertEquals("Seven", list.get(1)[0]);
		assertEquals(1l, list.get(1)[1]);
		
		assertEquals("Six", list.get(2)[0]);
		assertEquals(2l, list.get(2)[1]);
		
		assertEquals("Two", list.get(3)[0]);
		assertEquals(2l, list.get(3)[1]);
		
	}
	
}
