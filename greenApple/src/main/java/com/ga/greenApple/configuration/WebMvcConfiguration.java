package com.ga.greenApple.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ga.greenApple.service.SessionCheck;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	// spring의 root-context.xml과 servlet-context.xml 내용
	
	// file upload setting
	@Bean
	public CommonsMultipartResolver multiResolver() {
		CommonsMultipartResolver cmr = new CommonsMultipartResolver();
		cmr.setDefaultEncoding("utf-8");
		cmr.setMaxUploadSize(10*1024*1024); // 최대 10M
		
		return cmr;
	}
	
	// session check
	/* sessonCheck는 InterceptorRegistry를 재정의 한 것이니 메소드명을
	 addInterceptors로 꼭 적어줘야한다 */
	@Override
	public void addInterceptors(InterceptorRegistry registory) {
		registory.addInterceptor(new SessionCheck())
			.excludePathPatterns("/**/join", "/**/join/idCheck", "/**/login"
					, "/**/product", "/**/product/seasonal", "/**/product/view"
					, "/**/reviewList")
			// 위 지정한 것들 제외하여 모두 세션 체크를 한다
			.addPathPatterns("/**");
	}
	
	// spring 암호화
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
