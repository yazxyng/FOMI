package com.fomi;
import com.fomi.domain.auth.repository.AuthSessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import java.net.URI;
import java.net.http.*;
import java.util.regex.Pattern;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginTests {
 @LocalServerPort int port;
 @Autowired AuthSessionRepository sessions;
 HttpResponse<String> request(String path,String body,String token) throws Exception {
  var builder=HttpRequest.newBuilder(URI.create("http://localhost:"+port+path));
  if(token!=null) builder.header("Authorization","Bearer "+token);
  if(body!=null) builder.header("Content-Type","application/json").POST(HttpRequest.BodyPublishers.ofString(body));
  return HttpClient.newHttpClient().send(builder.build(),HttpResponse.BodyHandlers.ofString());
 }
 String field(String json,String name) {
  var matcher=Pattern.compile("\""+name+"\":\"([^\"]+)\"").matcher(json);
  assertThat(matcher.find()).isTrue(); return matcher.group(1);
 }
 @Test void loginRefreshAndLogout() throws Exception {
  assertThat(request("/api/users/me",null,null).statusCode()).isEqualTo(401);
  assertThat(request("/api/users/me",null,"invalid").statusCode()).isEqualTo(401);
  assertThat(request("/api/auth/signup","""
   {"username":"login_user","password":"test-password123","passwordConfirm":"test-password123"}
   """,null).statusCode()).isEqualTo(201);
  var login=request("/api/auth/login","""
   {"username":"login_user","password":"test-password123"}
   """,null);
  assertThat(login.statusCode()).isEqualTo(200);
  assertThat(login.headers().firstValue("Cache-Control").orElse("")).contains("no-store");
  String access=field(login.body(),"accessToken"),refresh=field(login.body(),"refreshToken");
  assertThat(request("/api/users/me",null,access).body()).contains("login_user").doesNotContain("password");
  var stored=sessions.findAll();
  assertThat(stored).isNotEmpty();
  assertThat(sessions.findByAccessHash(access)).isEmpty();
  var rotated=request("/api/auth/refresh","{\"refreshToken\":\""+refresh+"\"}",null);
  assertThat(rotated.statusCode()).isEqualTo(200);
  String newAccess=field(rotated.body(),"accessToken"),newRefresh=field(rotated.body(),"refreshToken");
  assertThat(newAccess).isNotEqualTo(access);
  assertThat(request("/api/users/me",null,access).statusCode()).isEqualTo(401);
  assertThat(request("/api/auth/refresh","{\"refreshToken\":\""+refresh+"\"}",null).statusCode()).isEqualTo(401);
  assertThat(request("/api/users/me",null,newAccess).statusCode()).isEqualTo(200);
  assertThat(request("/api/auth/logout","{\"refreshToken\":\""+newRefresh+"\"}",null).statusCode()).isEqualTo(204);
  assertThat(request("/api/users/me",null,newAccess).statusCode()).isEqualTo(401);
  assertThat(request("/api/auth/refresh","{\"refreshToken\":\""+newRefresh+"\"}",null).statusCode()).isEqualTo(401);
 }
 @Test void wrongPasswordAndUnknownUserReturnSameError() throws Exception {
  request("/api/auth/signup","{\"username\":\"wrong_user\",\"password\":\"test-password123\",\"passwordConfirm\":\"test-password123\"}",null);
  var wrong=request("/api/auth/login","{\"username\":\"wrong_user\",\"password\":\"wrong-password\"}",null);
  var absent=request("/api/auth/login","{\"username\":\"absent_user\",\"password\":\"wrong-password\"}",null);
  assertThat(wrong.statusCode()).isEqualTo(401);
  assertThat(absent.body()).isEqualTo(wrong.body());
 }
}
