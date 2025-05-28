package com.eunjisim.llm.issue.threadpoolsolutiondemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.eunjisim.llm.issue.threadpoolsolutiondemo.controller.LlmController;

@WebMvcTest(LlmController.class)
class ThreadpoolsolutiondemoApplicationTests {

	// @Test
	// void contextLoads() {
	// }

    @Autowired
	private MockMvc mockMvc;

	@Test
    void 기존_방식_비동기_테스트() throws Exception {
        // 1차 요청 → async started
        MvcResult mvcResult = mockMvc.perform(get("/api/llm/call"))
                .andExpect(request().asyncStarted())
                .andDo(print())
                .andReturn();

        // 비동기 처리 결과 확인
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string("기존 LLM 응답 완료"))
                .andDo(print());
    }

	@Test
    void 최적화_비동기_테스트() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/llm/call-optimized"))
                .andExpect(request().asyncStarted())
                .andDo(print())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().string("최적화된 LLM 응답 완료"))
                .andDo(print());
    }

}
