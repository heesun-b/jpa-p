package shop.mtcoding.board.mock;

import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.Media;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import shop.mtcoding.board.modules.user.User;
import shop.mtcoding.board.modules.user.UserController;
import shop.mtcoding.board.modules.user.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(UserController.class)
// 이유
@MockBean(JpaMetamodelMappingContext.class)
public class UserMockTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    UserService userService;

    @Test
    public void getPage_test() throws Exception {
        Pageable pageable = PageRequest.of(1, 10);
        Page<User> page = new PageImpl<>(
                List.of(
                        new User(1, "ssar", "1234"),
                        new User(2, "cos", "1234")));

        // given
        BDDMockito.given(this.userService.getPage(pageable)).willReturn(page);

        // when
        ResultActions perform = this.mvc.perform(
                get("/users?page={page}&size={size}", 1, 10).accept(MediaType.APPLICATION_JSON_VALUE));

        // then
        perform.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].username").value("ssar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].password").value("1234"));
    }

    @Test
    void getUserFail() throws Exception {

        // given
        int id = 0;
        BDDMockito.given(this.userService.getUser(id)).willReturn(Optional.empty());

        // when
        ResultActions perform = this.mvc.perform(
                get("/users/{id}", id).accept(MediaType.APPLICATION_JSON));

        perform.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
}
