package ru.alov.market.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.alov.market.api.dto.UserProfileDto;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender sender;

    public void sendRegistrationMail(UserProfileDto userProfileDto) throws MessagingException {
        sendMail(userProfileDto.getEmail(), "Регистрация успешно пройдена", "Имя пользователя " + userProfileDto.getUsername());
    }

    private void sendMail(String email, String subject, String text) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(email);
        helper.setSubject(subject);
        helper.setText(text, true);
        sender.send(message);
    }


}
