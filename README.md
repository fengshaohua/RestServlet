#RESTServlet
#简介
##RestServlet是为了使servlet实现RESTful风格请求的一个简单的小插件

###只需要一个注解，即可帮助你实现，风格与SpingMVC类似（其实我就是仿照那个写的●rz）
###从此再也不用一个请求建立一个Servlet了


#使用方法
* 下载RestServlet.jar
* 本插件依赖于cglib与asm
* 下载完导入/WEB-INF/lib
* 配置    
<br>
###1,新建个config.xml文件，加入将要扫描的包的地址，配置如下
<br>

        <?xml version="1.0" encoding="UTF-8"?><br>
        <main>
            <package>xyz.fefine.controller</package>
            <package>xyz.fefine.controller</package>
        </main>
###2,在web.xml下加入dispatcherServlet，配置如下
        <servlet>
            <servlet-name>restDispatcherServlet</servlet-name>
            <servlet-class>xyz.fefine.servlet.MainServlet</servlet-class>
            <init-param>
                <!--此param不要更改  -->
                <param-name>contextConfigLocation</param-name>
                <!-- 这里写你的配置文件的地址，就上上面的那个config.xml的地址-->
                <param-value>config.xml</param-value>
            </init-param>
            <load-on-startup>1</load-on-startup>
        </servlet>
        <servlet-mapping>
            <servlet-name>restDispatcherServlet</servlet-name>
            <!-- 过滤的列表 -->
            <url-pattern>*.do</url-pattern>
        </servlet-m1apping>
    
###3,实现类 
<font color="red">Warring:每个方法的前两个参数必须为request,response</font>

        public class ST {
            /**
            * {a}为要传递的参数
            * 在下面用String a来进行接收
            * 前两个参数必须为request和response，目前版本不能修改
            **/
            @Path("/main/{a}")
    	    public void rest(HttpServletRequest res,HttpServletResponse resp,@RequestParam("a")String a){
    		    resp.getWriter().append("a:"+a);	
            }
        }

###4,配置完毕

##源码都已经放在了git上面
##包含所需的3个jar
##因为技术原因，如有bug请向我反馈：email：feng_sh@outlook.com
##如果你对此项目感兴趣，也可以联系我。

#最后：推荐一下我的blog [fefine](http://www.fefein.xyz),[博客园](http://www.cnblogs.com/weikongziqu/)

