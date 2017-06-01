package org.onetwo4j.sample.utils;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultUserDetailServiceImpl implements UserDetailsService {
	

	@Override
	public LoginUserInfo loadUserByUsername(String name) throws UsernameNotFoundException {
		throw new UnsupportedOperationException("not implements yet!");
	}
	
}
