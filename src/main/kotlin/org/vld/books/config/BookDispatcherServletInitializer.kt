package org.vld.books.config

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

class BookDispatcherServletInitializer : AbstractAnnotationConfigDispatcherServletInitializer() {

    override fun getRootConfigClasses(): Array<Class<*>>? = null

    override fun getServletConfigClasses(): Array<Class<*>> = arrayOf(BookConfig::class.java)

    override fun getServletMappings(): Array<String> = arrayOf("/")
}
