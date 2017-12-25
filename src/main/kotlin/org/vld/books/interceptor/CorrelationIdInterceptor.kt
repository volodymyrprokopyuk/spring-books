package org.vld.books.interceptor

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CorrelationIdInterceptor : HandlerInterceptorAdapter() {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(HandlerInterceptorAdapter::class.java)
    }

    override fun preHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?): Boolean {
        logger.info("CorrelationIdInterceptor.preHandle()")
        return true
    }

    override fun postHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, modelAndView: ModelAndView?) {
        logger.info("CorrelationIdInterceptor.postHandle()")
    }

    override fun afterCompletion(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, ex: Exception?) {
        logger.info("CorrelationIdInterceptor.afterCompletion()")
    }
}
