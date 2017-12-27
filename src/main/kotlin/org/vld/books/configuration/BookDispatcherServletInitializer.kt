package org.vld.books.configuration

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class BookDispatcherServletInitializer : AbstractAnnotationConfigDispatcherServletInitializer() {

    override fun getRootConfigClasses(): Array<Class<*>>? = null

    override fun getServletConfigClasses(): Array<Class<*>> = arrayOf(BookWebMvcConfiguration::class.java)

    override fun getServletMappings(): Array<String> = arrayOf("/")
}
