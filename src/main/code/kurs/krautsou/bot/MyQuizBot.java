package kurs.krautsou.bot;

import kurs.krautsou.bot.botUtils.QuestionAnswerGenerator;
import kurs.krautsou.bot.botUtils.UserSessionHandler;
import kurs.krautsou.entity.UserScoreEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import kurs.krautsou.bot.botUtils.UserScoreHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyQuizBot extends TelegramLongPollingBot {
    private static final String BOT_USER_NAME = "Krautsou_Bot";
    private static final String TOKEN = "578403994:AAFGn-Wir8klxykcR3SpR8VYWIowSMUWs6I";

    private final QuestionAnswerGenerator questionAnswerGenerator;

    private final UserSessionHandler userSessionHandler;

    private final UserScoreHandler userScoreHandler;

    @Autowired
    public MyQuizBot(QuestionAnswerGenerator questionAnswerGenerator, UserSessionHandler userSessionHandler, UserScoreHandler userScoreHandler) {
        this.questionAnswerGenerator = questionAnswerGenerator;
        this.userSessionHandler = userSessionHandler;
        this.userScoreHandler = userScoreHandler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = null;
        String userMessageText = null;
        String userName;
        Long userId = null;
        Long chatId = null;
        if (update.hasMessage()) {
            if (!update.getMessage().hasText() & !update.hasCallbackQuery()) {
                sendMainMenu(update.getMessage().getChatId());
                return;
            }
            message = update.getMessage();
            userId = message.getFrom().getId().longValue();
            userName = message.getFrom().getUserName();
            chatId = message.getChatId();
            userMessageText = message.getText().toLowerCase();
            if (!userSessionHandler.sessionIsActive(userId)) {
                sendMainMenu(chatId);
            }
        }
        if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getMessage();
            userMessageText = update.getCallbackQuery().getData();
            chatId = message.getChatId();
            userId = update.getCallbackQuery().getFrom().getId().longValue();
        }
        if (!userSessionHandler.sessionIsActive(userId) && userMessageText != null) {
            if (userMessageText.contains("/help")) {
                sendTextMessage(chatId, "Для начала новой выкторины пришлите мне /go. " +
                        "Для ответа на один вопрос викторины отведено 20 секунд, " +
                        "по истечению этого времени, ответ не засчитывается. " +
                        "За правильный ответ засчитывается 1 балл. " +
                        "Для просмотра своего счета пришлите /score.");
            }
            if (userMessageText.contains("/score")) {
                if (userScoreHandler.userAlreadyInChart(userId)) {
                    sendTextMessage(chatId, "Ваш счет: " + String.valueOf(userScoreHandler.getUserScoreById(userId)));
                } else {
                    sendTextMessage(chatId, "Запись во вашему счету отсутствует, " +
                            "вероятно вы еще не играли в викторину. " +
                            "Для начала пришлите /go.");
                }
            }
            if (userMessageText.contains("/top10")) {
                List<UserScoreEntity> topUsersScoreList = userScoreHandler.getTopFiveUserScore();
                String topUsersScoreString = topUsersScoreList.stream()
                        .map(UserScoreEntity::getUserName)
                        .collect(Collectors.joining("\n"));
                sendTextMessage(chatId, topUsersScoreString);
            }
            if (userMessageText.contains("/go")) {
                String questionAndAnswer = questionAnswerGenerator.getNewQuestionAndAnswerForUser();
                String[] questionAndAnswerArray = questionAndAnswer.split("\\|");
                String question = questionAndAnswerArray[0];
                userSessionHandler.createUserSession(userId, questionAndAnswer);
                if (!userScoreHandler.userAlreadyInChart(userId)) {
                    userName = message.getChat().getUserName();
                    userScoreHandler.addNewUserInChart(userId, userName);
                }
                sendTextMessage(chatId, question);
            }
        } else if (userSessionHandler.sessionIsActive(userId) && userMessageText != null) {
            String rightAnswer = userSessionHandler.getAnswerFromSession(userId).toLowerCase();
            LocalDateTime currentDate = LocalDateTime.now();

            if (userSessionHandler.validateDate(currentDate, userId)) {
                if (rightAnswer.contains(userMessageText)) {
                    sendTextMessage(chatId, "Поздравляю! Ответ правильный!");
                    sendMainMenu(chatId);
                    userScoreHandler.incrementUserScore(userId);
                    userSessionHandler.deleteUserSession(userId);
                } else {
                    sendTextMessage(chatId, "Неверный ответ!");
                    sendMainMenu(chatId);
                    userSessionHandler.deleteUserSession(userId);
                }
            } else {
                sendTextMessage(chatId, "Время на ответ вышло.");
                userSessionHandler.deleteUserSession(userId);
            }
        }

    }

    private void sendMainMenu(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Выбери команду");
        sendMessage.setReplyMarkup(getMainBotMarkup());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendTextMessage(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup getMainBotMarkup() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        List<InlineKeyboardButton> fourthRow = new ArrayList<>();
        firstRow.add(new InlineKeyboardButton().setText("Начать игру").setCallbackData("/go"));
        secondRow.add(new InlineKeyboardButton().setText("Мой счет").setCallbackData("/score"));
        thirdRow.add(new InlineKeyboardButton().setText("Топ 10").setCallbackData("/top10"));
        fourthRow.add(new InlineKeyboardButton().setText("Помощь").setCallbackData("/help"));
        rowsInLine.add(firstRow);
        rowsInLine.add(secondRow);
        rowsInLine.add(thirdRow);
        rowsInLine.add(fourthRow);
        markupInline.setKeyboard(rowsInLine);
        return markupInline;
    }

    @Override
    public String getBotUsername() {
        return BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }
}
