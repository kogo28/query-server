package project.goorm.queryserver.business.core.domain.news.infrastructure.query;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import project.goorm.queryserver.business.core.domain.common.deleted.Deleted;
import project.goorm.queryserver.business.core.domain.news.entity.News;

import java.util.List;
import java.util.Optional;

import static project.goorm.queryserver.business.core.domain.bookmark.entity.QBookmark.bookmark;
import static project.goorm.queryserver.business.core.domain.company.entity.QCompany.company;
import static project.goorm.queryserver.business.core.domain.news.entity.QNews.news;

@Repository
public class NewsQueryRepository {

    private final JPAQueryFactory queryFactory;

    public NewsQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Optional<List<News>> findNewsByCompanyId(Long companyId) {
        return Optional.ofNullable(queryFactory.selectFrom(news)
                .where(news.companyId.eq(companyId)
                .and(news.deleted.eq(Deleted.FALSE)))
                .fetch());
    }

    public Optional<List<News>> findNewsByCompanyName(String companyName) {
        return Optional.ofNullable(queryFactory.selectFrom(news)
                .join(company)
                .on(news.companyId.eq(company.companyId))
                .where(company.companyName.eq(companyName).and(news.deleted.eq(Deleted.FALSE)))
                .fetch());
    }

}
