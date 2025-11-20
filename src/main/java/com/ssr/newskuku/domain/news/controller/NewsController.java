package com.ssr.newskuku.domain.news.controller;

import com.ssr.newskuku.domain.news.News;
import com.ssr.newskuku.domain.news.NewsService;
import com.ssr.newskuku.domain.news.dto.NewsResponse;
import com.ssr.newskuku.domain.news.mapper.NewsMapper;
import com.ssr.newskuku.domain.openai.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private final NewsMapper newsMapper;
    private final OpenAiService openAiService;

    /**
     * 임시 크롤링 API
     * @return
     */
    @GetMapping("/admin/clawl")
    public String crawl() {
        newsService.crawlLatestNews();
        return "크롤링 완료";
    }

    // 상세보기
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        NewsResponse.FindById news = newsService.getNewsId(id);
        model.addAttribute("news", news);
        return "news/detail";  // JSP 페이지
    }

    // ai 테스트 용도 추후 삭제 예정
    @PostMapping("{newsId}/summarize")
    public String summarizeNews(@PathVariable Long newsId) {
        News news = newsMapper.selectNewsById(newsId);

        // AI 요약 호출
        String summary = openAiService.getSummary(news.getContent());

        // DB에 저장 & is_write 업데이트
        newsMapper.insertNewsSummary(news.getNewsId(), summary, (long) news.getCategoryId());
        newsMapper.updateIsWrite(news.getNewsId(), 1);

        return "redirect:/news/detail/" + newsId;
    }
}
