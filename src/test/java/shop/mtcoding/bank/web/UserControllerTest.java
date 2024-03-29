package shop.mtcoding.bank.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.bank.config.dummy.DummyObject;
import shop.mtcoding.bank.core.RestDoc;
import shop.mtcoding.bank.domain.user.UserRepository;
import shop.mtcoding.bank.dto.user.UserRequestDto.JoinRequestDto;

@DisplayName("유저 API")
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8081)
@ActiveProfiles("test") // 테스트 모드
@Sql("classpath:db/teardown.sql") // BeforeEach 직전 실행
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserControllerTest extends RestDoc {

     private DummyObject dummy = new DummyObject();

     @Autowired
     private MockMvc mvc;
     @Autowired
     private ObjectMapper om;
     @Autowired
     private EntityManager em;
     @Autowired
     private UserRepository userRepository;

     @BeforeEach
     public void setUp() {
          userRepository.save(dummy.newUser("ssar", "쌀"));
          em.clear();
     }

     @DisplayName("회원가입 성공")
     @Test
     public void join_success_test() throws Exception {
          // given
          JoinRequestDto joinRequestDto = new JoinRequestDto();
          joinRequestDto.setUsername("love");
          joinRequestDto.setPassword("1234");
          joinRequestDto.setEmail("love@nate.com");
          joinRequestDto.setFullname("러브");

          String requestBody = om.writeValueAsString(joinRequestDto);
          System.out.println("테스트 : " + requestBody);

          // when
          ResultActions resultActions = mvc.perform(post("/api/join")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON));
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);

          // then
          resultActions.andExpect(status().isCreated());
          resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
     }

     @DisplayName("회원가입 실패")
     @Test
     public void join_fail_test() throws Exception {
          // given
          JoinRequestDto joinRequestDto = new JoinRequestDto();
          joinRequestDto.setUsername("ssar");
          joinRequestDto.setPassword("1234");
          joinRequestDto.setEmail("ssar@nate.com");
          joinRequestDto.setFullname("쌀");

          String requestBody = om.writeValueAsString(joinRequestDto);
          System.out.println("테스트 : " + requestBody);

          // when
          ResultActions resultActions = mvc.perform(post("/api/join")
                    .content(requestBody)
                    .contentType(MediaType.APPLICATION_JSON));
          String responseBody = resultActions.andReturn().getResponse().getContentAsString();
          System.out.println("테스트 : " + responseBody);

          // then
          resultActions.andExpect(status().isBadRequest());
          resultActions.andDo(MockMvcResultHandlers.print()).andDo(document);
     }
}
