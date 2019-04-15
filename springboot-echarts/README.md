## Echarts + WebSocket实现图片生成

大致的过程是这样

对于每一种图表类型，开放一个数据请求接口，通过POST请求接受图表数据，然后push到客户端去，
客户端接受到请求后渲染echarts图表，并且会将BASE64格式图片数据再次提交给另外一个图片保存接口进行实际的保存。

对于需要生成图片形式的客户端，先打开一个浏览器连上服务器，另外写一个HTTPClient程序通过POST调用图片保存接口即可。



大致步骤是这样的：

1、编写一个RESTful API接口，用来接收生成图表需要的数据，然后向页面推送导出图片的消息请求。
2、编写一个WebSocket接口，接收Base64格式的图片编码，然后将其转换为图片保存到磁盘上
3、启动应用后打开websocket服务端，并监听页面发送的Echarts图片导出消息
4、打开页面，自动连接上websocket服务器，并监听导出图片的消息请求，收到消息后发送Echarts图片导出消息
5、后面就可以调用这个RESTful API接口来讲数据可视化为Echarts图片并保存了。
我曾经尝试过，Java程序通过phantomjs启动浏览器打开页面，最后导出来的图片有1.5M，而直接用浏览器打开后导出大小只有50K， 所以放弃了phantomjs方案。

## 使用案例

使用JMH做微基准测试的时候，通常需要可视化显示测试结果，这时候可将文本形式的测试结果上传，自动生成非常漂亮的性能测试报告图表。

其他想要可视化数据保存图片的场景，都可以这样实现。

## 改进方案

通过配合PhantomJS来实现无浏览器的自动图片生成。经过测试PhantomJS打开页面后可以连上socket服务器，但是10秒后自动关闭了。

尝试采用页面js轮询方式，1秒轮询一次，有数据的时候就导出图片。结果导出图片太大了，不知道怎么回事，另外轮询方案始终不是很好。

最后还是老老实实使用websocket方案

## JMH性能测试

在包`com.xncoding.benchmark`中，有几个基准测试，并且可将测试结果利用echarts图片导出到图片。

## 测试步骤

1. 启动应用后，用浏览器打开首页：<http://localhost:9075/>
2. 然后再执行图片生成测试方法`com.xncoding.echarts.common.util.ApplicationTests.testOption()`

## 许可证

Copyright (c) 2018 Xiong Neng

基于 MIT 协议发布: <http://www.opensource.org/licenses/MIT>


参考：https://www.xncoding.com/2017/07/16/spring/sb-socketio.html
      https://www.xncoding.com/2017/08/19/spring/sb-echarts.html
