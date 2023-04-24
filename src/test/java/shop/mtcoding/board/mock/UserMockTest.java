package shop.mtcoding.board.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import shop.mtcoding.board.modules.user.User;
import shop.mtcoding.board.modules.user.UserController;
import shop.mtcoding.board.modules.user.UserSaveRequest;
import shop.mtcoding.board.modules.user.UserService;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
// 이유
@MockBean(JpaMetamodelMappingContext.class)
public class UserMockTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getPage_test() throws Exception {
        Pageable pageable = PageRequest.of(1, 10);
        Page<User> page = new PageImpl<>(
                List.of(
                        new User(1, "ssar", "1234"),
                        new User(2, "cos", "1234")));

        // given
        given(this.userService.getPage(pageable)).willReturn(page);

        // when
        ResultActions perform = this.mvc.perform(
                get("/users?page={page}&size={size}", 1, 10).accept(MediaType.APPLICATION_JSON_VALUE));

        // then
        perform.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].username").value("ssar"))
                .andExpect(jsonPath("$.content[0].password").value("1234"));
    }

    @Test
    void getUserFail() throws Exception {

        // given
        int id = 0;
        given(this.userService.getUser(id)).willReturn(Optional.empty());

        // when
        ResultActions perform = this.mvc.perform(
                get("/users/{id}", id).accept(MediaType.APPLICATION_JSON));

        perform.andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.detail").value("존재하지 않는 유저입니다."));
    }

    @Test
    void getUser() throws Exception {
        // given
        int id = 1;
        given(this.userService.getUser(id)).willReturn(Optional.of(
                new User(1, "ssar", "1234")
        ));

        // when
        ResultActions perform = this.mvc.perform(
                get("/users/{id}", id).accept(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("ssar"))
                .andExpect(jsonPath("$.password").value("1234"));
    }

    @Test
    void saveUserFail() throws Exception {

        // given
        UserSaveRequest request = new UserSaveRequest("", "");
        given(this.userService.save(request)).willReturn(new User(1, request.getUsername(), request.getPassword()));

        // when
        ResultActions perform = this.mvc.perform(
                post("/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE));

        // then
        perform.andExpect(status().isBadRequest())
                .andDo(print());
    }


}
