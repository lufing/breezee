/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.common;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * 分页查询信息
 * <p>
 * Created on 2015/11/5.
 */
public class PageInfo implements Pageable, Serializable {

	public final static int DEFAULT_PAGE_SIZE = 5;

	private int pageNumber;

	private int pageSize = DEFAULT_PAGE_SIZE;

	private Sort sort;

	private String sortId;

	public PageInfo() {
	}

	public PageInfo(int pageNumber, int pageSize, Sort sort) {
		this(pageNumber, pageSize);
		this.sort = sort;
	}

	public PageInfo(int page, int size, Sort.Direction direction, String... properties) {
		this(page, size, new Sort(direction, properties));
	}

	public PageInfo(int pageNumber, int pageSize) {
		if (pageNumber < 0) {
			throw new IllegalArgumentException("Page index must not be less than zero!");
		}
		if (pageSize < 1) {
			throw new IllegalArgumentException("Page size must not be less than one!");
		}
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	@Override
	public int getPageNumber() {
		return pageNumber;
	}

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public int getOffset() {
		return pageNumber * pageSize;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	@Override
	public Pageable next() {
		return new PageInfo(getPageNumber() + 1, pageSize, sort);
	}

	@Override
	public Pageable previousOrFirst() {
		return hasPrevious() ? previous() : first();
	}

	public Pageable previous() {
		return getPageNumber() == 0 ? this : new PageInfo(getPageNumber() - 1, getPageSize(), getSort());
	}

	@Override
	public Pageable first() {
		return new PageInfo(0, pageSize, sort);
	}

	@Override
	public boolean hasPrevious() {
		return pageNumber > 0;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public String getSortId() {
		return sortId;
	}

	public void setSortId(String sortId) {
		this.sortId = sortId;
		if (this.sort == null && this.sortId != null)
			this.sort = new Sort(Sort.Direction.ASC, sortId);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + pageNumber;
		result = prime * result + pageSize;
		return result;
	}

}
