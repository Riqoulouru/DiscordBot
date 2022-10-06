package com.company.bot.ConstantRunning;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;

public class ReactionMembre extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        if (event.getGuild().getName().equals("\uD83D\uDCBBInsa Hauts-de-France - Ingénieur informatique - alternance")) {

            //clément
            if(Objects.requireNonNull(event.getMessage().getMember()).getId().equals("244533618758778882")){
                event.getMessage().addReaction("U+1F34C").queue();
            }
            //riqou
            if(Objects.requireNonNull(event.getMessage().getMember()).getId().equals("212204362058039296")){
                event.getMessage().addReaction("U+1F1E8 U+1F1F3").queue();
                event.getMessage().addReaction("chingchong:1018853260427280504").queue();
                System.out.println(event.getMessage().getContentRaw());

            }
            //gio
            if(Objects.requireNonNull(event.getMessage().getMember()).getId().equals("219501917384998912")){
                event.getMessage().addReaction("U+1F443").queue();
            }
            //théo
            if(Objects.requireNonNull(event.getMessage().getMember()).getId().equals("303628697531842563")){
                event.getMessage().addReaction("U+1F9D5").queue();
            }
            //Khalil
            if(Objects.requireNonNull(event.getMessage().getMember()).getId().equals("388601454350434304")){
                event.getMessage().addReaction("U+1F437").queue();
                event.getMessage().addReaction("U+1F996").queue();
            }
            //Martin t
            if(Objects.requireNonNull(event.getMessage().getMember()).getId().equals("397409785147817986")){
                event.getMessage().addReaction("Sheeeesh:974611230851493908").queue();
            }
            //Martin m
            if(Objects.requireNonNull(event.getMessage().getMember()).getId().equals("425324945162240020")){
                event.getMessage().addReaction("U+1F956").queue();
            }
        }
    }
}
