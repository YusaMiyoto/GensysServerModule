package ru.miyoto.gensys.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Bot extends TelegramLongPollingBot {
    private String name = "GensysBot";
    private String token = "1872402124:AAGVfdgUggHCMX_T6at8JV38C67Athq7jU8";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        Bot telegramBot = new Bot();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public void sendTextMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            execute(sendMessage.setText(text));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /*public void sendMsg(Message message,){

    }*/

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            switch (update.getMessage().getText()) {
                case "/about":
                    sendTextMsg(update.getMessage(), "Я - GensysBot, часть системы распознавания людей Gensys.\nОтвечаю за оповещение пользователя о приближении человека " +
                            "к пропускному пункту, за поиск в базе данных и определение прибывшего человека как студента университета или как постороннего.");
                    break;
                default:
                    //if(checkPerson()){ sendMsg(); }
                    //else sendTextMsg(update.getMessage(),"Приближается незарегистрированный человек.");

            }
        }

    }

    @Override
    public String getBotUsername() {
        return name;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}




