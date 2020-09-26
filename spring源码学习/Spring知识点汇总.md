# 资源路径
1、
地址前缀：classpath:	
示例：classpath:top/mcwebsite/resource/bean.xml	
从类路径中加载资源，classpath:和classpath:/是等价的，都是相对于类的路径。资源文件可以在标准的文件系统中，也可以在JAR和ZIP的类包中

2、
地址前缀：file:	
示例：file:/config/bean,xml	
使用UrlResource从文件系统目录中装载资源，可采用绝对或相对路径

3、
地址前缀：http://	
示例：http://www.mcwebsite.top/bean.xml	
使用UrlResource从Web服务器中装载资源

4、
地址前缀：ftp://	
示例：ftp://www.mcwebsite.top/bean.xml	
使用UrlResource从ftp服务器中装载资源

