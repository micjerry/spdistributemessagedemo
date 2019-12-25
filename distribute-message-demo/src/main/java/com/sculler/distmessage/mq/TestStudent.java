package com.sculler.distmessage.mq;

public class TestStudent {
	private String fullName;
	private String className;
	
	public TestStudent() {
		super();
	}
	
	public TestStudent(String fullName, String className) {
		this.fullName = fullName;
		this.className = className;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	@Override
	public String toString() {
		return "Student [name=" + fullName + ", class=" + className + "]";
	}
}
