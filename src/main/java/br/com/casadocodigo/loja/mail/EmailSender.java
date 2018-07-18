package br.com.casadocodigo.loja.mail;

import br.com.casadocodigo.loja.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {

    @Autowired
    private MailSender mailSender;

    public void sendNewPurchaseMail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("compras@casadocodigo.com.br");
        mailMessage.setTo(user.getLogin());
        mailMessage.setSubject("Nova Compra :)");
        mailMessage.setText("Parabéns pela sua compra na casa do código!");
        mailSender.send(mailMessage);
    }

}
