package kurs.krautsou.configuration;

import kurs.krautsou.bot.botUtils.QuestionAnswerGenerator;
import kurs.krautsou.bot.botUtils.UserSessionHandler;
import kurs.krautsou.dBUtil.questionAndAnswer.QuestionAndAnswerDaoImpl;
import kurs.krautsou.dBUtil.userSession.UserSessionDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.TelegramBotsApi;
import kurs.krautsou.bot.botUtils.UserScoreHandler;
import kurs.krautsou.dBUtil.questionAndAnswer.QuestionAndAnswerDao;
import kurs.krautsou.dBUtil.userSession.UserSessionDaoImpl;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public UserSessionDao userSessionDao() {
        return new UserSessionDaoImpl();
    }

    @Bean
    public QuestionAndAnswerDao questionAndAnswerDao() {
        return new QuestionAndAnswerDaoImpl();
    }

    @Bean
    public UserSessionHandler userSessionHandler() {
        return new UserSessionHandler();
    }

    @Bean
    public QuestionAnswerGenerator questionAnswerGenerator() {
        return new QuestionAnswerGenerator();
    }

    @Bean
    public UserScoreHandler userScoreHandler() {
        return new UserScoreHandler();
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() {
        return new TelegramBotsApi();
    }
}
