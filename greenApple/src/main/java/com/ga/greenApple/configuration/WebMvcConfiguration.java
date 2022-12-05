package com.ga.greenApple.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ga.greenApple.service.SessionCheck;

// WebMvcConfigurer : 이 인터페이스를 구현하면 @EnableWebMvc의 자동 설정을 베이스로 가진다
// 원하는 설정을 추가할 수 있다는 장점이 있다 (Override 가능)

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
	// sessonCheck는 InterceptorRegistry를 재정의 한 것이니 메소드명을 addInterceptors로 꼭 적어줘야한다
	// addInterceptors : 애플리케이션 내 인터셉터를 등록해주는 기능
	@Override
	public void addInterceptors(InterceptorRegistry registory) {
		registory.addInterceptor(new LoggerInterceptor())
			// excludePathPatterns : 이곳에 지정된 URI나 경로는 인터셉터 호출에서 제외 => 정적 파일 무시
			.excludePathPatterns(
					"/product", "/product/seasonal/**", "/product/view/**",
					"/join", "/join/idCheck", "/login", "/member/session",
					"/review/list/**", "/review/imgList", "/review/reviewNum"
					)
			// 인터셉터 호출에서 경로를 허용 (세션 체크를 하겠다)
			.addPathPatterns(
					"/product/nowOrder", 
					"/member/information", "/member/update", "/member/delete",
					"/member/myReview", "/logout",
					"/review/insert", "/review/update", "/review/delete",
					"/cart/**", "/order/**", "/admin/**"
					);
	}
	
	// spring 암호화
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
