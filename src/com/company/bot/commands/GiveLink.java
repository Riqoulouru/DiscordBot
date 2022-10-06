package com.company.bot.commands;

import com.company.bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class GiveLink extends ListenerAdapter {

    private int i = 0;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        Main.addServeurToListeningCHannel(event.getGuild(),event.getChannel().getName());

        if(event.getChannel().getName().equals(Main.getListeningChannel().get(event.getGuild()))){
            String[] args = event.getMessage().getContentRaw().split(" ");

            if(!event.getAuthor().getName().equals("Riqou'sBot")) {
                if (event.getGuild().getName().equals("Riqou's Server")) {
                    if (args[0].equalsIgnoreCase(Main.prefix + "twitch")) {

                        event.getChannel().sendMessage("https://www.twitch.tv/riqoulouru").queue();

                    }
                    if (args[0].equalsIgnoreCase(Main.prefix + "DiscordInvit")) {
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setTitle("Lien d'invitation discord");
                        embedBuilder.addField("Voici le lien d'invitation pour le discord ", "https://discord.gg/zQfFCEMEU9", true);
                        embedBuilder.setColor(Color.red);
                        event.getChannel().sendMessage(embedBuilder.build()).queue();

                    }
                }
            }
        }

    }

}
