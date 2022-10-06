package com.company.bot.commands;

import com.company.bot.Main;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import com.darkprograms.speech.translator.GoogleTranslate;

public class Traduction extends ListenerAdapter {
    private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
    private static final String CLIENT_SECRET = "PUBLIC_SECRET";
    private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        Main.addServeurToListeningCHannel(event.getGuild(), event.getChannel().getName());
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (!event.getAuthor().getName().equals("Riqou'sBot")) {
            if(event.getChannel().getName().equals("bot-commands")){
                if (args[0].equalsIgnoreCase(Main.prefix + "tradChinois")) {
                    String fromLang = "fr";
                    String toLang = "zh-CN";

                    String[] yourArray = Arrays.copyOfRange(args, 1, args.length);
                    StringBuilder phraseBuilder = new StringBuilder();
                    for (String s : yourArray) {
                        phraseBuilder.append(s);
                        phraseBuilder.append(" ");
                    }
                    String phrase = phraseBuilder.toString();

                    String traduction = "";
                    try {
                        traduction = GoogleTranslate.translate(fromLang, toLang, phrase);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    TextChannel channel = event.getChannel();
                    System.out.println(traduction.getClass());
                    channel.sendMessage(traduction).queue();

                }


                if (args[0].equalsIgnoreCase(Main.prefix + "chinoisToFrançais")) {
                    TextChannel channel = event.getChannel();
                    int nbFois = 0;
                    try {
                        nbFois = Integer.parseInt(args[1]);
                    } catch (Exception e){
                        channel.sendMessage("Veuillez entrer le nombre de fois à traduire en premier argument").queue();
                    }

                    String fromLang = "fr";
                    String toLang = "zh-CN";

                    String[] yourArray = Arrays.copyOfRange(args, 2, args.length);
                    StringBuilder phraseBuilder = new StringBuilder();
                    for (String s : yourArray) {
                        phraseBuilder.append(s);
                        phraseBuilder.append(" ");
                    }
                    String phrase = phraseBuilder.toString();

                    String traduction = "";

                    for (int i = 0; i < nbFois; i++) {
                        try {
                            phrase = GoogleTranslate.translate(fromLang, toLang, phrase);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        channel.sendMessage(i + " " + phrase).queue();
                        try {
                            phrase = GoogleTranslate.translate(toLang,fromLang, phrase);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        channel.sendMessage(phrase).queue();
                    }


                }

            }

        }

    }

}
