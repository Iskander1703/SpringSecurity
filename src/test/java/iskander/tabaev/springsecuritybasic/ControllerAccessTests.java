package iskander.tabaev.springsecuritybasic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerAccessTests {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(authorities = "UPDATE")
    @Test
    void getWhenReadAuthorityThenAuthorized() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/myAccount"))
                .andExpect(status().isOk());
    }

}
