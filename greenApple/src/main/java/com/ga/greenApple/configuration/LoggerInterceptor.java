package com.ga.greenApple.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

// @Slf4j : 로깅 추상화 라이브러리
// 로깅 추상화 : 로깅을 직접 하지 않고 로깅 구현체를 찾아 기능을 사용할 수 있게 해준다
@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {

	@Override
	// 컨트롤러의 메소드에 매핑된 특정 URI가 호출됐을 때 실행되는 메서드로, 컨트롤러를 경유(접근)하기 직전에 실행
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		log.debug("===============================================");
//		log.debug("==================== BEGIN ====================");
//		log.debug("Request URI ===> " + request.getRequestURI());
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	// 컨트롤러를 경유(접근) 한 후, 즉 화면(View)으로 결과를 전달하기 전에 실행되는 메소드
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//		log.debug("==================== END ======================");
//		log.debug("===============================================");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

}
