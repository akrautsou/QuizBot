package kurs.krautsou.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class BotInitilizer {


    @Autowired
    public BotInitilizer(MyQuizBot myQuizBot, TelegramBotsApi telegramBotsApi){
        try{
            telegramBotsApi.registerBot(myQuizBot);
        } catch (TelegramApiException ex){
            ex.printStackTrace();
        }

    }
}
