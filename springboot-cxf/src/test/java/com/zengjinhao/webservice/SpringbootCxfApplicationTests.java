package com.zengjinhao.webservice;

import com.zengjinhao.webservice.model.User;
import com.zengjinhao.webservice.service.ICommonService;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootCxfApplicationTests {

    private Integer port;
    /**
     * 接口地址
     */
    private String wsdlAddress;

    @Before
    public void prepare() {
        wsdlAddress = "http://localhost:8092/services/CommonService?wsdl";
    }

    /**
     * 方式1.代理类工厂的方式,需要拿到对方的接口
     */
    @Test
    public void cl1() {
        try {
            // 接口地址
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(wsdlAddress);
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(ICommonService.class);
            // 创建一个代理接口实现
            ICommonService cs = (ICommonService) jaxWsProxyFactoryBean.create();
            // 数据准备
            String userName = "Leftso";
            // 调用代理接口的方法调用并返回结果
            String result = cs.sayHello(userName);
            System.out.println("返回结果:" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方式2. 动态调用方式
     */
    @Test
    public void cl2() {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdlAddress);
        // 需要密码的情况需要加上用户名和密码
        // client.getOutInterceptors().add(new ClientLoginInterceptor(USER_NAME, PASS_WORD));
        Object[] objects;
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("sayHello", "Leftso");
            System.out.println("返回类型：" + objects[0].getClass());
            System.out.println("返回数据:" + objects[0]);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方式3. 动态调用方式，返回对象User
     */
    @Test
    public void cl3() {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(wsdlAddress);
        Object[] objects;
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke("getUser", "张三");
            System.out.println("返回类型：" + objects[0].getClass());
            System.out.println("返回数据:" + objects[0]);
            User user = (User) objects[0];
            System.out.println("返回对象User.name=" + user.getName());
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方式4. 客户端代码生成方式
     * 这是推荐做法，就是根据wsdl的访问路径来生成客户端代码。这里有两种生成方式
     * <p>
     * Apache的wsdl2java工具
     * wsdl2java -autoNameResolution http://xxx?wsdl
     * JDK自带的工具（推荐）
     * JDK自己内置了一个wsimport工具，这个是推荐的生成方式。
     * <p>
     * wsimport -encoding utf-8 -p com.xncoding.webservice.client -keep http://xxx?wsdl -s d:/ws -B-XautoNameResolution
     * 其中：
     * <p>
     * -encoding ：指定编码格式（此处是utf-8的指定格式）
     * -keep：是否生成Java源文件
     * -s：指定.java文件的输出目录
     * -d：指定.class文件的输出目录
     * -p：定义生成类的包名，不定义的话有默认包名
     * -verbose：在控制台显示输出信息
     * -b：指定jaxws/jaxb绑定文件或额外的schemas
     * -extension：使用扩展来支持SOAP1.2
     */
//    @Test
//    public void cl4() {
//        CommonService_Service c = new CommonService_Service();
//        com.zengjinhao.webservice.client.User user = c.getCommonServiceImplPort().getUser("Tom");
//        assertThat(user.getName(), is("Tom"));
//    }
}