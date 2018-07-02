package com.way.utils.http;


public class HttpProxy {
	
	private boolean isProxy = true;
	
	private String address = "cnpriproxy.aia.biz";
	
	private int port = 10938;

	public boolean isProxy() {
		return isProxy;
	}

	public void setProxy(boolean isProxy) {
		this.isProxy = isProxy;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "HttpProxy [isProxy=" + isProxy + ", address=" + address
				+ ", port=" + port + "]";
	}
	
	
}
