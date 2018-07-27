package org.crap.jrain.core.validate.security;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

public class DefaultKeyPairCollection extends KeyPairCollection {

	public static final List<KeyPair> keyPairList = new ArrayList<>();
	
	@Override
	public void add(KeyPair kp) {
		keyPairList.add(kp);
	}

	@Override
	public KeyPair get(int index) {
		return keyPairList.get(index);
	}

	@Override
	public void clearCollention() {
		keyPairList.clear();
	}

}
