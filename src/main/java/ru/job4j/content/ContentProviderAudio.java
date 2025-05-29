package ru.job4j.content;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.job4j.interfaces.ContentProvider;
import ru.job4j.model.Content;

import java.io.InputStream;

@Component
public class ContentProviderAudio implements ContentProvider {
    @Override
    public Content byMood(Long chatId, Long moodId) {
        Content content = new Content(chatId);

        InputStream stream = getClass().getResourceAsStream("/media/gustav.mp3");

        if (stream != null) {
            try {
                content.setAudio(new InputFile(stream, "gustav.mp3"));
            } catch (Exception e) {
                content.setText("⚠️ Ошибка загрузки аудио: " + e.getMessage());
            }
        } else {
            content.setText("⚠️ Файл не найден: gustav.mp3");
        }

        return content;
    }
}

