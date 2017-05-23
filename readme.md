#dropwizard demo

###问题汇总
1. response对象的timestamp对象 <br />
答：（1）不优雅的解决：增加类 HandleEntityResponseFilter.java
（2）优雅的解决：environment.getObjectMapper() 进行修改
2. health check 没有起作用 <br />
答：访问地址：http://127.0.0.1:8081/healthcheck
3. 动态加入resource类 <br />
答：environment.jersey().packages("com.zy.dropwizard.resource");
4. yml文件path问题 <br />
答：增加类 ClasspathOrFileConfigurationSourceProvider.java
5. pojo驼峰转下划线 <br />
答：在类上增加@JsonSnakeCase
6. migration整合 <br />
答：com.zy.dropwizard.migration包下
7. 文件上传功能 <br />
答：environment.jersey().register(MultiPartFeature.class);
8. 日志打印 <br />
dropwizard打印的日志格式如下： <br /><br />
TRACE [2010-04-06 06:42:35,271] com.example.dw.Thing: Contemplating doing a thing. <br />
DEBUG [2010-04-06 06:42:35,274] com.example.dw.Thing: About to do a thing. <br />
INFO  [2010-04-06 06:42:35,274] com.example.dw.Thing: Doing a thing <br />
WARN  [2010-04-06 06:42:35,275] com.example.dw.Thing: Doing a thing <br />
ERROR [2010-04-06 06:42:35,275] com.example.dw.Thing: This may get ugly. <br />

所以可以使用 tail -f dw.log | grep '^ERROR' 来查看ERROR的日志

9. 关于过滤器(filter) <br />
可以使用Jersey filters 或者 Servlet filters 

10. spring整合 <br />
答：
servlet.setInitParameter("contextClass", "org.springframework.web.context.support.XmlWebApplicationContext"); <br />
servlet.setInitParameter("contextConfigLocation", configuration.getSpring().getContextConfigLocation());    <br />
servlet.addServletListeners(new ContextLoaderListener(), new RequestContextListener()); <br />

11. jersey和spring整合 <br />
答：引入pom依赖"jersey-spring3" <br />           

12. druid监控 <br />
答：
servlet.addServlet("DruidStatView",StatViewServlet.class).addMapping("/druid/*");  <br />
访问地址：http://127.0.0.1:8080/dropwizard-demo/druid/index.html

13. testng整合 <br />
答：可参考 SimpleTest.java 和 BookServiceTest.java

14. 异常统一处理 <br />
答：可参考shuyun-support-common的exception包

X. 使用spring AOP <br />