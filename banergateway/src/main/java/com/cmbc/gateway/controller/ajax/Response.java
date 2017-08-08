package com.cmbc.gateway.controller.ajax;

public class Response<T> {
	public Response() {
	}

	public Response(Integer status) {
		this.status = status;
	}

	Integer status;
	T responses;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public T getResponses() {
		return responses;
	}

	public void setResponses(T responses) {
		this.responses = responses;
	}

}
