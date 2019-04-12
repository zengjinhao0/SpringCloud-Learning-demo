package com.zengjinhao;

import com.zengjinhao.pos.model.BaseResponse;
import com.zengjinhao.pos.model.LoginParam;
import com.zengjinhao.pos.model.UnbindParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootResttemplateApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(SpringbootResttemplateApplicationTests.class);
    /**
     * @LocalServerPort 提供了 @Value("${local.server.port}") 的代替
     */
//    @LocalServerPort
//    private int port;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testRestTemplate() {
        LoginParam param = new LoginParam();
        param.setUsername("admin");
        param.setPassword("12345678");
        //String loginUrl = String.format("http://localhost:%d/login", port);
        String loginUrl = String.format("http://localhost:%d/login", 8092);
        BaseResponse r = restTemplate.postForObject(loginUrl, param, BaseResponse.class);
        assertThat(r.isSuccess(), is(true));

        String token = (String) r.getData();
        UnbindParam unbindParam = new UnbindParam();
        unbindParam.setImei("imei");
        unbindParam.setLocation("location");
        // 设置HTTP Header信息
        //String unbindUrl = String.format("http://localhost:%d/unbind", port);
        String unbindUrl = String.format("http://localhost:%d/unbind", 8092);
        URI uri;
        try {
            uri = new URI(unbindUrl);
        } catch (URISyntaxException e) {
            logger.error("URI构建失败", e);
            throw new RuntimeException("URI构建失败");
        }
        RequestEntity<UnbindParam> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .body(unbindParam);
        ResponseEntity<BaseResponse> responseEntity = restTemplate.exchange(requestEntity, BaseResponse.class);
        BaseResponse r2 = responseEntity.getBody();
        assertThat(r2.isSuccess(), is(true));
        assertThat(r2.getData(), is("unbind"));


//        //自动异常处理器
//        try {
//            responseEntity = restTemplate.exchange(requestEntity, String.class);
//        } catch (HttpServerErrorException e) {
//            // log error
//        }



//        /**
//         * 发送文件
//         */
//        MultiValueMap<String, Object> multiPartBody = new LinkedMultiValueMap<>();
//        multiPartBody.add("file", new ClassPathResource("/tmp/user.txt"));
//        RequestEntity<MultiValueMap<String, Object>> requestEntity = RequestEntity
//                .post(uri)
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .body(multiPartBody);
//
//
//        /**
//         * 下载文件
//         */
//        // 小文件
//        RequestEntity requestEntity = RequestEntity.get(uri).build();
//        ResponseEntity<byte[]> responseEntity = restTemplate.exchange(requestEntity, byte[].class);
//        byte[] downloadContent = responseEntity.getBody();
//        // 大文件
//        ResponseExtractor<ResponseEntity<File>> responseExtractor = new ResponseExtractor<ResponseEntity<File>>() {
//            @Override
//            public ResponseEntity<File> extractData(ClientHttpResponse response) throws IOException {
//                File rcvFile = File.createTempFile("rcvFile", "zip");
//                FileCopyUtils.copy(response.getBody(), new FileOutputStream(rcvFile));
//                return ResponseEntity.status(response.getStatusCode()).headers(response.getHeaders()).body(rcvFile);
//            }
//        };
//        File getFile = this.restTemplate.execute(targetUri, HttpMethod.GET, null, responseExtractor);
//
//
//
//        /**
//         * 发送get请求
//         */
//        // 1-getForObject()
//        User user1 = this.restTemplate.getForObject(uri, User.class);
//        // 2-getForEntity()
//        ResponseEntity<User> responseEntity1 = this.restTemplate.getForEntity(uri, User.class);
//        HttpStatus statusCode = responseEntity1.getStatusCode();
//        HttpHeaders header = responseEntity1.getHeaders();
//        User user2 = responseEntity1.getBody();
//        // 3-exchange()
//        RequestEntity requestEntity = RequestEntity.get(new URI(uri)).build();
//        ResponseEntity<User> responseEntity2 = this.restTemplate.exchange(requestEntity, User.class);
//        User user3 = responseEntity2.getBody();
//
//        /**
//         * 发送post请求
//         */
//        // 1-postForObject()
//        User user1 = this.restTemplate.postForObject(uri, user, User.class);
//        // 2-postForEntity()
//        ResponseEntity<User> responseEntity1 = this.restTemplate.postForEntity(uri, user, User.class);
//        // 3-exchange()
//        RequestEntity<User> requestEntity = RequestEntity.post(new URI(uri)).body(user);
//        ResponseEntity<User> responseEntity2 = this.restTemplate.exchange(requestEntity, User.class);
//
//
//        /**
//         * 设置headers
//         */
//        // 1-Content-Type
//        RequestEntity<User> requestEntity = RequestEntity
//                .post(new URI(uri))
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(user);
//        // 2-Accept
//        RequestEntity<User> requestEntity = RequestEntity
//                .post(new URI(uri))
//                .accept(MediaType.APPLICATION_JSON)
//                .body(user);
//        // 3-Other
//        RequestEntity<User> requestEntity = RequestEntity
//                .post(new URI(uri))
//                .header("Authorization", "Basic " + base64Credentials)
//                .body(user);


    }
}