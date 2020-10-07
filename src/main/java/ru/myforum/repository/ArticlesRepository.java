package ru.myforum.repository;


import org.springframework.data.repository.CrudRepository;
import ru.myforum.entities.Article;

public interface ArticlesRepository extends CrudRepository<Article, Long> {
}
