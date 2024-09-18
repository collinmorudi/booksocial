package com.collin.booksocial.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.mail.javamail.MimeMessageHelper.MULTIPART_MODE_MIXED;

/**
 * The EmailService class is responsible for sending emails asynchronously
 * using a specified email template and context variables. It utilizes
 * Spring's JavaMailSender and SpringTemplateEngine for email creation
 * and template processing respectively.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    /**
     * Handles the creation and sending of emails.
     * Utilized by the EmailService class to send emails asynchronously.
     * This instance of JavaMailSender is responsible for sending the emails
     * created and configured by the EmailService.
     */
    private final JavaMailSender mailSender;
    /**
     * Template engine for processing email templates.
     * Utilizes SpringTemplateEngine for rendering HTML email content with context variables.
     */
    private final SpringTemplateEngine templateEngine;

    /**
     * Sends an email asynchronously using the specified email template and
     * context variables. This method constructs and sends a MIME email message
     * using the provided parameters.
     *
     * @param to the recipient's email address
     * @param username the recipient's username
     * @param emailTemplate the email template to be used; if null, "confirm-email" is used
     * @param activationCode the activation code to be included in the email
     * @param confirmationUrl the confirmation URL to be included in the email
     * @param subject the subject of the email
     * @throws MessagingException if an error occurs while creating or sending the email
     */
    @Async
    public void sendEmail(
            String to,
            String username,
            EmailTemplateName emailTemplate,
            String activationCode,
            String confirmationUrl,
            String subject
    ) throws MessagingException {
        String templateName;
        if (emailTemplate == null) {
            templateName = "confirm-email";
        } else {
            templateName = emailTemplate.name();
        }
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MULTIPART_MODE_MIXED,
                UTF_8.name()
        );
        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activation_code", activationCode);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("realcollin112@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);

        helper.setText(template, true);

        mailSender.send(mimeMessage);
    }
}
