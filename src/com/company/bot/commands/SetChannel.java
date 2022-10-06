package com.company.bot.commands;

import com.company.bot.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class SetChannel extends ListenerAdapter {



    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        if(event.getGuild().getOwner() == event.getMember() ){
            String[] args = event.getMessage().getContentRaw().split(" ");

            if(!event.getAuthor().getName().equals("Riqou'sBot")) {
                if (args[0].equalsIgnoreCase(Main.prefix + "setChannel")) {
                    if(args[1] != null){

                        if(Main.getListeningChannel().containsKey(event.getGuild())){
                            Main.getListeningChannel().replace(event.getGuild(),args[1]);
                        } else {
                            Main.getListeningChannel().put(event.getGuild(),args[1]);
                        }

                        event.getChannel().sendMessage("C'est fait !").queue();
                    }

                }


            }

        }
    }

}


