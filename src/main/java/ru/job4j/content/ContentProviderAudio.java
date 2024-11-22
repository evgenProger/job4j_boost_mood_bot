package ru.job4j.content;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.job4j.interfaces.ContentProvider;
import ru.job4j.model.Content;

@Component
public class ContentProviderAudio implements ContentProvider {
    @Override
    public Content byMood(Long chatId, Long moodId) {
        var content = new Content(chatId);
        content.setAudio(new InputFile("./audio/music.mp3"));
        return content;
    }
}
