package org.crap.jrain.core.validate.security;

import java.security.KeyPair;

import org.crap.jrain.core.validate.security.component.RSA;

public abstract class KeyPairCollection {
	
	private int total = 0;
	
	protected KeyPair create() {
		return RSA.generateKeyPair();
	}
	
	public void generate(int num) {
		for (int i = 0; i < num; i++) {
			add(create());
		}
		this.total += num;
	}
	
	public abstract void add(KeyPair kp);
	
	public abstract KeyPair get(int index);

	public void clear() {
		clearCollention();
		setTotal(0);
	}
	
	public abstract void clearCollention();
	
	public KeyPair random() {
		int index = (int)(Math.random()*(this.total-1));
		return get(index);
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	
}
