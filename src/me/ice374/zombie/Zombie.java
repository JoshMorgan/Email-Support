package me.ice374.zombie;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class Zombie extends JavaPlugin implements Listener {

    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("support")){
            String text = Arrays.toString(args).replace("[", "").replaceAll("[],,]", "");
            email(text, p);
        }
        return true;
    }

    public void email(String text, Player p){
        String[] to = {"jm109876543210@gmail.com"};
        String time = String.format("[%tm/%td/%ty - %tH:%tM:%tS] ", 
                new Date(), new Date(),new Date(),new Date(),new Date(),new Date());
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("tregmine.info@gmail.com","password-here");
            }
        });
        try {
            InternetAddress[] addressTo = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++)
            {
                addressTo[i] = new InternetAddress(to[i]);
            }
            Message message = new MimeMessage(session);
            message.setRecipients(RecipientType.TO, addressTo); 
            message.setSubject("[Help Request] from " + p.getName());
            message.setText(
                    "Help request from: " + p.getName() + " at " + time + "\n" +
                    "\n" +
                    "Message: " + text + "\n"
                    );
            Transport.send(message);
            p.sendMessage(ChatColor.GREEN + "Help request sent, we will get back to you ASAP :)");
        } catch (MessagingException e) {
            p.sendMessage(ChatColor.RED + "Error sending message, please try again.");
            throw new RuntimeException(e);
        }
    }
}