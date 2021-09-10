package by.resliv.bot.service;

import by.resliv.bot.pojo.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

@Component
public class BotService extends TelegramLongPollingBot {

    private static final Logger logger = LogManager.getLogger(BotService.class);

    private static final String USER_NAME = "@AlexLazarenkoTestBot";

    private static final String BOT_TOKEN = "1965463710:AAFI0pLrJh0f7JuHYBiu-wXmG6Dyuae7WzU";

    private static final String NO_SUCH_CITY = "Sorry! No such city in our base!";

    @Autowired
    CityService service;

    @Override
    public String getBotUsername() {
        return USER_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Optional<City> thatCity = Optional.empty();
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()&&!message.isCommand()) {
                String text = message.getText();
                thatCity = service.findByCityName(text);
                try {
                    if (thatCity.isPresent()) {
                        execute(SendMessage.builder().chatId(message.getChatId().toString()).text(thatCity.get().getText()).build());
                    } else {
                        execute(SendMessage.builder().chatId(message.getChatId().toString()).text(NO_SUCH_CITY).build());
                    }
                } catch (TelegramApiException e) {
                    logger.error(e);
                }
            }
        }
    }

}