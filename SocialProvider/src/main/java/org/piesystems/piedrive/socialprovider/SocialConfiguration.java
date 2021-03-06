/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.piesystems.piedrive.socialprovider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.SessionUserIdSource;
import org.springframework.social.dropbox.connect.DropboxConnectionFactory;
import org.springframework.social.google.connect.GoogleConnectionFactory;

/**
 *
 * @author Svetoslav Videnov <s.videnov@dsg.tuwien.ac.at>
 */
@Configuration
@EnableSocial
public class SocialConfiguration implements SocialConfigurer {

	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfc, Environment e) {
		cfc.addConnectionFactory(new DropboxConnectionFactory(
				e.getProperty("spring.social.dropbox.appId"),
				e.getProperty("spring.social.dropbox.appSecret"),
				"pieDrive/0.1"));
		
		GoogleConnectionFactory google = new GoogleConnectionFactory(
				e.getProperty("spring.social.google.appId"),
				e.getProperty("spring.social.google.appSecret"));
		
		google.setScope("https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/drive");
		
		cfc.addConnectionFactory(google);
	}
	
	@Bean
	public SecurityUserIdSource securityUserIdSource() {
		return new SecurityUserIdSource();
	}

	@Override
	public UserIdSource getUserIdSource() {
		return securityUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator cfl) {
		return new InMemoryUsersConnectionRepository(cfl);
	}

	@Bean
	public ConnectController connectController(
			ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository,
			Environment env) {
		SocialController cc = new SocialController(connectionFactoryLocator, 
				connectionRepository, securityUserIdSource());
		cc.setApplicationUrl(env.getProperty("spring.social.app.url"));
		return cc;
	}
}
