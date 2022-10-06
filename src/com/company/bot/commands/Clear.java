package com.company.bot.commands;

import com.company.bot.Main;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class Clear extends ListenerAdapter {


    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        Main.addServeurToListeningCHannel(event.getGuild(), event.getChannel().getName());
        if (event.getGuild().getOwner() == event.getMember()) {

            String[] args = event.getMessage().getContentRaw().split(" ");

            if (!event.getAuthor().getName().equals("Riqou'sBot")) {

                if (args[0].equalsIgnoreCase(Main.prefix + "clear") ) {

                    boolean isMessage = true;

                    while(isMessage){
                        List<Message> messages = event.getChannel().getHistory().retrievePast(50).complete();

                        if(messages.isEmpty()){
                            isMessage = false;
                            return;
                        }


                        event.getChannel().deleteMessages(messages).complete();


                    }


                }

            }
        }
    }
}
