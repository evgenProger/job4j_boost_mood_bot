package ru.job4j.content;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.job4j.interfaces.ContentProvider;
import ru.job4j.model.Content;

import java.io.File;

@Component
public class ContentProviderImage implements ContentProvider {

    @Override
    public Content byMood(Long chatId, Long moodId) {
        var content = new Content(chatId);
        var imageFile = new File("src/main/resources/media/clair.jpg");
        content.setPhoto(new InputFile(imageFile));
        return content;
    }
}
