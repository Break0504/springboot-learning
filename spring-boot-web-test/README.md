## spring boot web

### 集成 log4j2

* 排除logback依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

* 添加log4j2依赖
```xml
<!-- log4j2 依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

* 创建log4j2.properties 文件，配置日志格式 

[log4j2.properties](src/main/resources/log4j2.properties)

### mybatis 集成

* 添加 mysql 依赖
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

* 添加 mybatis 依赖
```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>${mybatis.spring.boot.version}</version>
</dependency>
```

* 添加数据库连接配置
```yaml
spring:
    # 数据库配置
    datasource:
        username: root
        password: 123456
        url: jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
        driver-class-name: com.mysql.jdbc.Driver
```

* 添加 mybatis 配置
```properties
# mybatis 映射设置
mybatis.config-location=classpath:config/mybatis-config.xml
mybatis.mapper-locations=classpath:mapper/*.xml
```

* mybatis-config.xml 开启自动驼峰命名转换
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<settings>
		<setting name="mapUnderscoreToCamelCase" value="true" />
	</settings>
</configuration>
```

* 在 resources 下新建 mapper文件夹，在 mapper 文件夹下写对应的 sql.xml 

### 集成 thymeleaf

* 添加 thymeleaf 模板依赖
```xml
<dependency><!--页面模板依赖-->
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```
* 添加引擎模板配置
```yaml
spring:
    # 引擎模板配置
    thymeleaf:
        cache: false # 关闭缓存
        prefix: classpath:/views/ # 指定 thymeleaf 模板路径
        suffix: .html
        encoding: UTF-8 # 指定字符集编码
```

* 添加静态资源过滤器
```java
@Configuration
public class CustomeWebConfiguration extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/views/");
        registry.addResourceHandler("/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
    }
}
```

* 创建 html 文件
```html
<!DOCTYPE html>
<!--将默认的头 <html lang="en"> 引入thymeleaf-->
<html lang="en" xmlns:th="http://www.thymeleaf.org" xmlns:layout="http://www.ultrag.net.nz/thymeleaf/layout">
<head>
    <meta charset="UTF-8">
    <title>主页</title>
    <script th:src="@{/js/jquery-1.7.2.min.js}"></script>
</head>
<body>
<table width="888" border="1">
        <thead>
        <tr>
            <th>id</th>
            <th>用户编号</th>
            <th>姓名</th>
            <th>手机号</th>
            <th>性别</th>
            <th>生日</th>
            <th>创建时间</th>
            <th>操作</th>
        </tr>
        <tr th:each="user:${alist}">
            <td  align="center" th:text="${user.id}"></td>
            <td  align="center" th:text="${user.userId}"></td>
            <td  align="center" th:text="${user.name}"></td>
            <td  align="center" th:text="${user.mobileNo}"></td>
            <td  align="center" th:text="${user.sex}"></td>
            <td  align="center" th:text="${user.birth}"></td>
            <td  align="center" th:text="${user.crtTs}"></td>
            <td  align="center"><a th:href="@{'/goUpdateUser/'+${user.userId}}">修改</a>
                <a th:href="@{'/deleteUser/'+${user.id}}">删除</a>
            </td>
        </tr>
        </thead>
    </table>
    <button><a href="/addUserHtml">添加</a></button>
</body>
</html>
```

### AOP 切面日志打印

* 添加 AOP 依赖
```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>${fastjson.version}</version>
</dependency>

<!-- aop依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```
* AOP 日志打印实例
```java
@Aspect
@Component
public class MvcAspect {
    private static Logger LOGGER = LoggerFactory.getLogger(MvcAspect.class);
    public static final String EDP = "execution(* com.breakzhang.web.test.controller.*Controller.*(..))";
    public static final String PC_NM = "point()";
    public MvcAspect(){}
    /**
     * 定义切入点
     */
    @Pointcut(EDP)
    public void point(){}

    /**
     * 在切入点开始处切入内容
     * @param joinPoint
     * @throws Throwable
     */
    @Before(PC_NM)
    public void before(JoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        if (joinPoint.getArgs().length < 1 || joinPoint.getArgs()[0] == null) {
            return;
        }

        LOGGER.debug("--request IP:{}, {}.{}", request.getRemoteAddr(), joinPoint.getTarget().getClass().getName(),
                ((MethodSignature) joinPoint.getSignature()).getMethod().getName());
        LOGGER.debug("--request param：{}", JSON.toJSONString(joinPoint.getArgs()));
    }

    @Around(PC_NM)
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object obj = null;
        try {
            obj = joinPoint.proceed();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            return obj;
        } finally {
            LOGGER.debug("耗时 : {}ms, --MvcAspect Response: {}", (System.currentTimeMillis() - startTime), JSON.toJSONString(joinPoint.getArgs()));
        }
    }
}
```

### 集成 redis 
* 添加依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

* 添加 redis 配置
```yaml
spring:
    # redis 配置
    redis:
        host: 127.0.0.1
        port: 6379
        password: 123456
        jedis:
            pool:
                max-idle: 8  # 连接池最大空闲连接
                min-idle: 0  # 连接池中的最小空闲连接
                max-active: 8 # 连接池最大连接数（使用负值表示没有限制）
                max-wait: 10000ms #连接池最大阻塞等待时间（使用负值表示没有限制）
        connect-timeout: 300 #连接超时时间（毫秒）
```

```java
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class) ;

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        LOGGER.info("RedisConfig == >> redisTemplate ");

        //创建RedisTemplate对象
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //序列化对象
        //简单的字符串序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //jdk序列化
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        //Json序列化
        /*GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer =
                new GenericJackson2JsonRedisSerializer();*/
        //设置String键的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //设置String值的序列化方式
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        return redisTemplate;
    }
}
```

* redis 缓存使用
```java
@Component
public class UserInfCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfCache.class);

    public static final int CACHE_MINUTES = 15; //缓存15分钟

    private static UserInfCache self;

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private RedisTemplate<String, UserInfDto> redisTemplate;

    /**
     * @PostConstruct 只会在项目启动的时候执行一次
     */
    @PostConstruct
    public void init() {
        self = this;
        refreshCache();
    }

    private void refreshCache() {
        LOGGER.debug("刷新所有用户缓存--");
        userInfoDao.listUserInf().forEach(user -> setCache(getKey(user.getUserId()), user));
    }

    private static void refreshUser(String userId) {
        LOGGER.debug("刷新用户：{}，缓存--", userId);
        UserInfDto userInf = self.userInfoDao.getOneByUserId(userId);
        self.setCache(getKey(userId), userInf);
    }

    private void setCache(String key, UserInfDto dto) {
        redisTemplate.opsForValue().set(key, dto, CACHE_MINUTES, TimeUnit.MINUTES);
    }

    public static UserInfDto getUserInf(String userId) {
        LOGGER.debug("查询用户获取缓存 userId：{}", userId);
        UserInfDto userInfDto = self.getCache(getKey(userId));
        if (userInfDto == null) {
            LOGGER.warn("user is null: {}", userId);
            refreshUser(userId);
        }
        return self.getCache(getKey(userId));
    }

    private UserInfDto getCache(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getKey(String userId) {
        return "breakzhang.user.cache-" + userId;
    }


}
```

### 多模块管理

#### 父 pom.xml 设置

```xml
<!--多模块名称-->
<modules>
    <module>spring-boot-web-test</module>
    <module>其他模块</module>
</modules>

<!-- spring boot 启动依赖 -->
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.4.1</version>
    <relativePath/> <!-- lookup parent from repository -->
</parent>


<!-- 集中管理依赖版本号，子模块可以直接使用${fastjson.version} 引用版本号 -->
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    <java.version>1.8</java.version>
    <easyexcel.version>2.2.6</easyexcel.version>
    <fastjson.version>1.2.73</fastjson.version>
    <mybatis.spring.boot.version>2.1.4</mybatis.spring.boot.version>
</properties>

 <!-- 定义多个模块的公共依赖，子模块自动引用该公共依赖 -->
<dependencies>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>


<!--指定使用maven多模块项目打包-->
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <source>${java.version}</source>
                <target>${java.version}</target>
            </configuration>
        </plugin>

        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <configuration>
                <skipTests>true</skipTests>    <!--默认关掉单元测试 -->
            </configuration>
        </plugin>
    </plugins>
</build>

```
#### 子模块 pom.xml 设置

```xml
<!-- 指定打包方式 -->
<packaging>war</packaging>

<!-- 引入父 pom.xml 依赖 -->
<parent>
    <groupId>com.breakzhang</groupId>
    <artifactId>springboot-learning</artifactId>
    <version>1.0.0</version>
    <relativePath>../pom.xml</relativePath> <!-- lookup parent from repository -->
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-logging</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    
    <!-- log4j2 依赖 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-log4j2</artifactId>
    </dependency>
    
    <!-- mybatis 依赖 -->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
        <version>${mybatis.spring.boot.version}</version>
    </dependency>

    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>${fastjson.version}</version>
    </dependency>

    <!-- aop依赖 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>

    <dependency><!--页面模板依赖-->
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
   <!-- <dependency>&lt;!&ndash;热部署依赖 redis会报ClassCastException 异常&ndash;&gt;
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
     </dependency>-->

     <!-- redis依赖 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <scope>test</scope>
     </dependency>
</dependencies>

<build>
    <!-- 指定打包名称 -->
    <finalName>spring-boot-web-test</finalName>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>

```