package ru.miyoto.gensys.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;


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
        //sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        try {
            execute(sendMessage.setText(text));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendAutoMsg(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(text);
        try {
            execute(sendMessage.setText(text));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void findPeople() {
        while (true) {
            if (BotServerFunctions.checkUpdates()) {
                String person = BotServerFunctions.searchPerson();
                if (person != null) {
                    sendPhotoMsg(BotServerFunctions.getChatId());
                    sendAutoMsg(BotServerFunctions.getChatId(), person);
                } else {
                    sendPhotoMsg(BotServerFunctions.getChatId());
                    sendAutoMsg(BotServerFunctions.getChatId(), "Warning! An outsider is approaching the checkpoint! If you want to add this person to the database, write \"/add\". Else write \"/no\"");
                    break;
                }
            }
            // else System.out.println(",,,,,");
        }
    }

    public void sendPhotoMsg(Long chatId) {
        File photo = new File("C://Users//810523//Documents//Metadata//photo//simplePhoto.png");
        SendPhoto sendPhoto = new SendPhoto().setPhoto(photo);
        sendPhoto.setChatId(chatId);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            //System.out.println("   " + BotServerFunctions.getWaitingEntering());
            switch (update.getMessage().getText()) {
                case "/start": {
                    BotServerFunctions.setChatId(update.getMessage().getChatId());
                    sendTextMsg(update.getMessage(), "Hello. I am GensysBot, part of the Gensys human recognition system.\nI am responsible for alerting the user when a person is approaching " +
                            "to the checkpoint, for searching the database and identifying the arriving person as a university student or as an outsider.");
                    BotServerFunctions.setWaitingEntering(6);
                    break;
                }
                case "/add": {
                    BotServerFunctions.getNewStudent().setMetadata(BotServerFunctions.getLastMetadata());
                    sendTextMsg(update.getMessage(), "Enter student's name:");
                    BotServerFunctions.setWaitingEntering(5);
                    break;
                }
                default: {
                    if (BotServerFunctions.getWaitingEntering() == 5) {
                        BotServerFunctions.getNewStudent().setName(update.getMessage().getText());
                        sendTextMsg(update.getMessage(), "Enter student's lastname:");
                        BotServerFunctions.setWaitingEntering(4);
                        break;
                    }
                    if (BotServerFunctions.getWaitingEntering() == 4) {
                        BotServerFunctions.getNewStudent().setLastName(update.getMessage().getText());
                        sendTextMsg(update.getMessage(), "Enter student's patronymic:");
                        BotServerFunctions.setWaitingEntering(3);
                        break;
                    }
                    if (BotServerFunctions.getWaitingEntering() == 3) {
                        BotServerFunctions.getNewStudent().setPatronymic(update.getMessage().getText());
                        sendTextMsg(update.getMessage(), "Enter student's course:");
                        BotServerFunctions.setWaitingEntering(2);
                        break;
                    }
                    if (BotServerFunctions.getWaitingEntering() == 2) {
                        BotServerFunctions.getNewStudent().setCourse(Integer.parseInt(update.getMessage().getText()));
                        sendTextMsg(update.getMessage(), "Enter student's group:");
                        BotServerFunctions.setWaitingEntering(1);
                        break;
                    }
                    if (BotServerFunctions.getWaitingEntering() == 1) {
                        BotServerFunctions.getNewStudent().setGroup(update.getMessage().getText());
                        BotServerFunctions.setWaitingEntering(6);
                        BotServerFunctions.addStudent(BotServerFunctions.getNewStudent());
                        sendTextMsg(update.getMessage(), "The student was successfully added to the database.");
                        break;
                    }

                    break;
                }
            }
        }

        if (BotServerFunctions.getWaitingEntering() == 6) {
            findPeople();
        }

       /* while (true) {
            if (BotServerFunctions.checkUpdates()) {
                String person = BotServerFunctions.searchPerson();
                if (person != null) {
                    sendAutoMsg(BotServerFunctions.getChatId(), person);
                } else {
                    sendAutoMsg(BotServerFunctions.getChatId(), "Warning! An outsider is approaching the checkpoint! If you want to add this person to the database, write \"/add\"");
                    break;
                }
            }
        }*/

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




