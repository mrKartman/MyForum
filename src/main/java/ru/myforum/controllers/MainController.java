package ru.myforum.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.myforum.entities.Article;
import ru.myforum.repository.ArticlesRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    ArticlesRepository articlesRepository;

//    @GetMapping("/")
//    public String main(Map<String, Object> model){
//        model.put("hello", "Hello, myforum");
//        return "main";
//    }
    @GetMapping("/")
    public String getArticles(Map<String, Object> model){
        Iterable<Article> articles = articlesRepository.findAll();
        model.put("articles", articles);
        return "main";
    }
    @GetMapping("/add")
    public String addArticlePage(){
        return "addarticle";
    }

    @PostMapping("/add")
    public String setArticle(@RequestParam String author, @RequestParam String them, @RequestParam String message, @RequestParam String tag){
        Article article = new Article(author, them, message, tag);
        articlesRepository.save(article);
        System.out.println(article.toString());
        return "addarticle";
    }

    @GetMapping("/delete/{id}")
    public String deleteArticle (@PathVariable Long id, Map <String, Object> model){
        Optional<Article> optionalArticle = articlesRepository.findById(id);
        if(optionalArticle.isPresent()){
            articlesRepository.deleteById(id);
        }else {
            model.put("DeleteArticle", "Данной статьи не существует");
        }
        return "redirect:/";
    }
}
