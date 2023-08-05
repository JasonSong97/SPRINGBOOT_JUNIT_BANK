package shop.mtcoding.bank.config.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.domain.user.User;
import shop.mtcoding.bank.domain.user.UserEnum;

public class JwtProcessTest {

     @Test
     public void create_test() throws Exception {
          // given
          User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
          LoginUser loginUser = new LoginUser(user);

          // when
          String jwtToken = JwtProcess.create(loginUser);
          System.out.println("테스트 : " + jwtToken);

          // then
          assertTrue(jwtToken.startsWith(JwtValueObject.TOKEN_PREFIX));
     }

     @Test
     public void verify_test() throws Exception {
          // given
          String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYW5rIiwicm9sZSI6IkNVU1RPTUVSIiwiaWQiOjEsImV4cCI6MTY5MTgwNjIwOH0.z0NyhELnJlWuUmNb4H9oO-uy3ZIHi69VSi3c8QZFH-Ni_grKIPCkPE1paTzYm8UKWeSkelmwJO6IH_TnBzNHDA";

          // when
          LoginUser loginUser = JwtProcess.verify(jwtToken);
          System.out.println("테스트 : " + loginUser.getUser().getId());
          System.out.println("테스트 : " + loginUser.getUser().getRole().name());

          // then
          assertThat(loginUser.getUser().getId()).isEqualTo(1L);
          assertThat(loginUser.getUser().getRole()).isEqualTo(UserEnum.CUSTOMER);
     }
}