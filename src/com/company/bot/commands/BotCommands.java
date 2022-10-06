package com.company.bot.commands;

import com.company.bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class BotCommands extends ListenerAdapter {

    private final List<EmbedBuilder> embedBuilders;
    private final int lastEmbed;
    private int dpsLimlit;
    private int tankLimlit;
    private int healLimlit;
    private int nbOfTank;
    private int nbOfHeal;
    private int nbOfDps;
    private String date;

    public BotCommands() {
        embedBuilders = new ArrayList<>();
        embedBuilders.add(new EmbedBuilder());
        this.lastEmbed = 0;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split(" ");
        nbOfTank = 0;
        nbOfHeal = 0;
        nbOfDps = 0;
        date = "";
        if(event.getGuild().getName().equals("Riqou's Server")) {
            if(args[0].equalsIgnoreCase(Main.prefix + "raid")) {
                try {
                    tankLimlit = Integer.parseInt(args[1]);
                    healLimlit = Integer.parseInt(args[2]);
                    dpsLimlit = Integer.parseInt(args[3]);
                }catch (Exception e){
                    event.getChannel().sendMessage("pas de limitations mises en places").queue();
                    tankLimlit = 999;
                    healLimlit = 999;
                    dpsLimlit = 999;

                    try {
                        date = args[1];
                    } catch (Exception exception){

                    }
                }

                try {
                    date = args[4];
                } catch (Exception e){

                }


                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Raid " + date);
                embedBuilder.addField("Tank", "<:tank:810933727974522891>" + " tank:",false);
                embedBuilder.addField("Tank", "<:heal:810933749004763156>" + " heal:",false);
                embedBuilder.addField("Dps", "<:dps:810933738678648924>" + " dps:",false);
                embedBuilder.addField("BackupTank", "<:tank:810933727974522891> ",false);
                embedBuilder.addField("BackupHeal", "<:heal:810933749004763156> ",false);
                embedBuilder.addField("BackupDps", "<:dps:810933738678648924> ",false);
                embedBuilder.setDescription("Réagissez pour réserver votre place dans le raid");
                embedBuilder.setColor(Color.orange);
                embedBuilders.set(0,embedBuilder);
                event.getChannel().sendMessage(embedBuilders.get(lastEmbed).build()).queue(message -> {
                    message.addReaction("tank:810933727974522891").queue();
                    message.addReaction("heal:810933749004763156").queue();
                    message.addReaction("dps:810933738678648924").queue();
                });



            }
        }





    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if(!event.getUser().getName().equals("Riqou'sBot")){

            if(event.getReactionEmote().getName().equals("dps")){

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Raid");

                if(nbOfDps < dpsLimlit ){

                    embedBuilder.addField("Tank", embedBuilders.get(lastEmbed).getFields().get(0).getValue(),false);
                    embedBuilder.addField("Heal", embedBuilders.get(lastEmbed).getFields().get(1).getValue(),false);
                    embedBuilder.addField("Dps", embedBuilders.get(lastEmbed).getFields().get(2).getValue() + " " + event.getMember().getUser().getName(),false);
                    embedBuilder.addField("BackupTank", embedBuilders.get(lastEmbed).getFields().get(3).getValue(),false);
                    embedBuilder.addField("BackupHeal", embedBuilders.get(lastEmbed).getFields().get(4).getValue(),false);
                    embedBuilder.addField("BackupDps", embedBuilders.get(lastEmbed).getFields().get(5).getValue(),false);

                } else {
                    embedBuilder.addField("Tank", embedBuilders.get(lastEmbed).getFields().get(0).getValue(),false);
                    embedBuilder.addField("Heal", embedBuilders.get(lastEmbed).getFields().get(1).getValue(),false);
                    embedBuilder.addField("Dps", embedBuilders.get(lastEmbed).getFields().get(2).getValue(),false);
                    embedBuilder.addField("BackupTank", embedBuilders.get(lastEmbed).getFields().get(3).getValue(),false);
                    embedBuilder.addField("BackupHeal", embedBuilders.get(lastEmbed).getFields().get(4).getValue(),false);
                    embedBuilder.addField("BackupDps", embedBuilders.get(lastEmbed).getFields().get(5).getValue() + " " + event.getMember().getUser().getName(),false);
                }
                embedBuilder.setDescription("Réagissez pour réserver votre place dans le raid");
                embedBuilder.setColor(Color.orange);
                embedBuilders.set(lastEmbed,embedBuilder);
                event.getChannel().editMessageById(event.getMessageId(),embedBuilder.build()).queue();
                nbOfDps++;

            } else if(event.getReactionEmote().getName().equals("tank")){

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Raid");
                if(nbOfTank < tankLimlit){
                    embedBuilder.addField("Tank", embedBuilders.get(lastEmbed).getFields().get(0).getValue() + " " + event.getMember().getUser().getName(),false);
                    embedBuilder.addField("Heal", embedBuilders.get(lastEmbed).getFields().get(1).getValue(),false);
                    embedBuilder.addField("Dps", embedBuilders.get(lastEmbed).getFields().get(2).getValue(),false);
                    embedBuilder.addField("BackupTank", embedBuilders.get(lastEmbed).getFields().get(3).getValue(),false);
                    embedBuilder.addField("BackupHeal", embedBuilders.get(lastEmbed).getFields().get(4).getValue(),false);
                    embedBuilder.addField("BackupDps", embedBuilders.get(lastEmbed).getFields().get(5).getValue(),false);
                } else {
                    embedBuilder.addField("Tank", embedBuilders.get(lastEmbed).getFields().get(0).getValue(),false);
                    embedBuilder.addField("Heal", embedBuilders.get(lastEmbed).getFields().get(1).getValue(),false);
                    embedBuilder.addField("Dps", embedBuilders.get(lastEmbed).getFields().get(2).getValue(),false);
                    embedBuilder.addField("BackupTank", embedBuilders.get(lastEmbed).getFields().get(3).getValue() +" " + event.getMember().getUser().getName(),false);
                    embedBuilder.addField("BackupHeal", embedBuilders.get(lastEmbed).getFields().get(4).getValue(),false);
                    embedBuilder.addField("BackupDps", embedBuilders.get(lastEmbed).getFields().get(5).getValue(),false);
                }
                embedBuilder.setDescription("Réagissez pour réserver votre place dans le raid");
                embedBuilder.setColor(Color.orange);
                embedBuilders.set(lastEmbed,embedBuilder);
                event.getChannel().editMessageById(event.getMessageId(),embedBuilder.build()).queue();
                nbOfTank++;
            } else if(event.getReactionEmote().getName().equals("heal")){

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setTitle("Raid");
                if(nbOfHeal < healLimlit){
                    embedBuilder.addField("Tank", embedBuilders.get(lastEmbed).getFields().get(0).getValue(),false);
                    embedBuilder.addField("Heal", embedBuilders.get(lastEmbed).getFields().get(1).getValue() + " " + event.getMember().getUser().getName(),false);
                    embedBuilder.addField("Dps", embedBuilders.get(lastEmbed).getFields().get(2).getValue(),false);
                    embedBuilder.addField("BackupTank", embedBuilders.get(lastEmbed).getFields().get(3).getValue(),false);
                    embedBuilder.addField("BackupHeal", embedBuilders.get(lastEmbed).getFields().get(4).getValue(),false);
                    embedBuilder.addField("BackupDps", embedBuilders.get(lastEmbed).getFields().get(5).getValue(),false);
                } else {
                    embedBuilder.addField("Tank", embedBuilders.get(lastEmbed).getFields().get(0).getValue(),false);
                    embedBuilder.addField("Heal", embedBuilders.get(lastEmbed).getFields().get(1).getValue(),false);
                    embedBuilder.addField("Dps", embedBuilders.get(lastEmbed).getFields().get(2).getValue(),false);
                    embedBuilder.addField("BackupTank", embedBuilders.get(lastEmbed).getFields().get(3).getValue(),false);
                    embedBuilder.addField("BackupHeal", embedBuilders.get(lastEmbed).getFields().get(4).getValue() + " " + event.getMember().getUser().getName(),false);
                    embedBuilder.addField("BackupDps", embedBuilders.get(lastEmbed).getFields().get(5).getValue(),false);
                }

                embedBuilder.setDescription("Réagissez pour réserver votre place dans le raid");
                embedBuilder.setColor(Color.orange);
                embedBuilders.set(lastEmbed,embedBuilder);
                event.getChannel().editMessageById(event.getMessageId(),embedBuilder.build()).queue();
                nbOfHeal++;
            }
        }
    }

    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        RestAction<User> user = event.retrieveUser();
        User u = user.complete();
        String nom = u.getName();

        System.out.println(nom);
        if(event.getReactionEmote().getName().equals("dps")){

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Raid");

            if(nbOfDps > dpsLimlit){
                boolean trouve = false;
                String[] s = embedBuilders.get(lastEmbed).getFields().get(2).getValue().split(" ");

                for (String s1 : s){
                    if(s1.equals(nom)){
                        trouve = true;
                    }
                }

                if(trouve){

                    s = embedBuilders.get(lastEmbed).getFields().get(2).getValue().split(" ");
                    String[] s2 = embedBuilders.get(lastEmbed).getFields().get(5).getValue().split(" ");
                    String finalString = "";
                    for(String s1 : s){
                        if(!s1.equals(nom)){
                            finalString += s1 + " ";
                        }
                    }
                    finalString += s2[1];

                    String finalStringS2 = "";
                    for(String s1 : s2){
                        if(!s1.equals(s2[1])){
                            finalStringS2 += s1 + " ";
                        }
                    }

                    embedBuilder.addField("Tank", embedBuilders.get(lastEmbed).getFields().get(0).getValue(),false);
                    embedBuilder.addField("Heal", embedBuilders.get(lastEmbed).getFields().get(1).getValue(),false);
                    embedBuilder.addField("Dps", finalString,false);
                    embedBuilder.addField("BackupTank", embedBuilders.get(lastEmbed).getFields().get(3).getValue(),false);
                    embedBuilder.addField("BackupHeal", embedBuilders.get(lastEmbed).getFields().get(4).getValue(),false);
                    embedBuilder.addField("BackupDps", finalStringS2,false);

                } else {
                    System.out.println("TESSST");
                    s = embedBuilders.get(lastEmbed).getFields().get(5).getValue().split(" ");
                    String finalString = "";
                    for(String s1 : s){
                        if(!s1.equals(nom)){
                            finalString += s1 + " ";
                        }
                    }
                    embedBuilder.addField("Tank", embedBuilders.get(lastEmbed).getFields().get(0).getValue(), false);
                    embedBuilder.addField("Heal", embedBuilders.get(lastEmbed).getFields().get(1).getValue(), false);
                    embedBuilder.addField("Dps", embedBuilders.get(lastEmbed).getFields().get(2).getValue(), false);
                    embedBuilder.addField("BackupTank", embedBuilders.get(lastEmbed).getFields().get(3).getValue(),false);
                    embedBuilder.addField("BackupHeal", embedBuilders.get(lastEmbed).getFields().get(4).getValue(),false);
                    embedBuilder.addField("BackupDps", finalString,false);

                }


            } else {



                String[] s= embedBuilders.get(lastEmbed).getFields().get(2).getValue().split(" ");
                String finalString = "";
                for(String s1 : s){
                    if(!s1.equals(nom)){
                        finalString += s1 + " ";
                    }
                }

                embedBuilder.addField("Tank", embedBuilders.get(lastEmbed).getFields().get(0).getValue(),false);
                embedBuilder.addField("Heal", embedBuilders.get(lastEmbed).getFields().get(1).getValue(),false);
                embedBuilder.addField("Dps", finalString,false);
                embedBuilder.addField("BackupTank", embedBuilders.get(lastEmbed).getFields().get(3).getValue(),false);
                embedBuilder.addField("BackupHeal", embedBuilders.get(lastEmbed).getFields().get(4).getValue(),false);
                embedBuilder.addField("BackupDps", embedBuilders.get(lastEmbed).getFields().get(5).getValue(),false);

            }




            embedBuilder.setDescription("Réagissez pour réserver votre place dans le raid");
            embedBuilder.setColor(Color.orange);
            embedBuilders.set(lastEmbed,embedBuilder);
            event.getChannel().editMessageById(event.getMessageId(),embedBuilder.build()).queue();

            nbOfDps--;


        } else if(event.getReactionEmote().getName().equals("tank")){


            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Raid");


            if(nbOfTank > tankLimlit) {

                boolean trouve = false;
                String[] s = embedBuilders.get(lastEmbed).getFields().get(0).getValue().split(" ");

                for (String s1 : s){
                    if(s1.equals(nom)){
                        trouve = true;
                    }
                }

                if(trouve) {

                    s = embedBuilders.get(lastEmbed).getFields().get(0).getValue().split(" ");
                    String[] s2 = embedBuilders.get(lastEmbed).getFields().get(3).getValue().split(" ");
                    String finalString = "";
                    for(String s1 : s){
                        if(!s1.equals(nom)){
                            finalString += s1 + " ";
                        }
                    }
                    finalString += s2[1];

                    String finalStringS2 = "";
                    for(String s1 : s2){
                        if(!s1.equals(s2[1])){
                            finalStringS2 += s1 + " ";
                        }
                    }

                    embedBuilder.addField("Tank", finalString,false);
                    embedBuilder.addField("Heal", embedBuilders.get(lastEmbed).getFields().get(1).getValue(),false);
                    embedBuilder.addField("Dps", embedBuilders.get(lastEmbed).getFields().get(2).getValue(),false);
                    embedBuilder.addField("BackupTank", finalStringS2,false);
                    embedBuilder.addField("BackupHeal", embedBuilders.get(lastEmbed).getFields().get(4).getValue(),false);
                    embedBuilder.addField("BackupDps", embedBuilders.get(lastEmbed).getFields().get(5).getValue(),false);



                } else {
                    s = embedBuilders.get(lastEmbed).getFields().get(3).getValue().split(" ");
                    String finalString = "";
                    for(String s1 : s){
                        if(!s1.equals(nom)){
                            finalString += s1 + " ";
                        }
                    }
                    embedBuilder.addField("Tank", embedBuilders.get(lastEmbed).getFields().get(0).getValue(), false);
                    embedBuilder.addField("Heal", embedBuilders.get(lastEmbed).getFields().get(1).getValue(), false);
                    embedBuilder.addField("Dps", embedBuilders.get(lastEmbed).getFields().get(2).getValue(), false);
                    embedBuilder.addField("BackupTank", finalString,false);
                    embedBuilder.addField("BackupHeal", embedBuilders.get(lastEmbed).getFields().get(4).getValue(),false);
                    embedBuilder.addField("BackupDps", embedBuilders.get(lastEmbed).getFields().get(5).getValue(),false);
                }




            } else {

                String[] s = embedBuilders.get(lastEmbed).getFields().get(0).getValue().split(" ");
                String finalString = "";
                for(String s1 : s){
                    if(!s1.equals(nom)){
                        finalString += s1 + " ";
                    }
                }


                embedBuilder.addField("Tank", finalString,false);
                embedBuilder.addField("Heal", embedBuilders.get(lastEmbed).getFields().get(1).getValue(),false);
                embedBuilder.addField("Dps", embedBuilders.get(lastEmbed).getFields().get(2).getValue(),false);
                embedBuilder.addField("BackupTank", embedBuilders.get(lastEmbed).getFields().get(3).getValue(),false);
                embedBuilder.addField("BackupHeal", embedBuilders.get(lastEmbed).getFields().get(4).getValue(),false);
                embedBuilder.addField("BackupDps", embedBuilders.get(lastEmbed).getFields().get(5).getValue(),false);

            }
            embedBuilder.setDescription("Réagissez pour réserver votre place dans le raid");
            embedBuilder.setColor(Color.orange);
            embedBuilders.set(lastEmbed,embedBuilder);
            event.getChannel().editMessageById(event.getMessageId(),embedBuilder.build()).queue();

            nbOfTank--;

        } else if(event.getReactionEmote().getName().equals("heal")){



            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Raid");



            if(nbOfHeal > healLimlit){

                boolean trouve = false;
                String[] s = embedBuilders.get(lastEmbed).getFields().get(1).getValue().split(" ");

                for (String s1 : s){
                    if(s1.equals(nom)){
                        trouve = true;
                    }
                }

                if(trouve) {

                    s = embedBuilders.get(lastEmbed).getFields().get(1).getValue().split(" ");
                    String[] s2 = embedBuilders.get(lastEmbed).getFields().get(4).getValue().split(" ");
                    String finalString = "";
                    for(String s1 : s){
                        if(!s1.equals(nom)){
                            finalString += s1 + " ";
                        }
                    }
                    finalString += s2[1];

                    String finalStringS2 = "";
                    for(String s1 : s2){
                        if(!s1.equals(s2[1])){
                            finalStringS2 += s1 +" ";
                        }
                    }

                    embedBuilder.addField("Tank", embedBuilders.get(lastEmbed).getFields().get(0).getValue(),false);
                    embedBuilder.addField("Heal", finalString,false);
                    embedBuilder.addField("Dps", embedBuilders.get(lastEmbed).getFields().get(2).getValue(),false);
                    embedBuilder.addField("BackupTank", embedBuilders.get(lastEmbed).getFields().get(3).getValue(),false);
                    embedBuilder.addField("BackupHeal", finalStringS2,false);
                    embedBuilder.addField("BackupDps", embedBuilders.get(lastEmbed).getFields().get(5).getValue(),false);

                } else {
                    s = embedBuilders.get(lastEmbed).getFields().get(4).getValue().split(" ");
                    String finalString = "";
                    for (String s1 : s) {
                        if (!s1.equals(nom)) {
                            finalString += s1 + " ";
                        }
                    }
                    embedBuilder.addField("Tank", embedBuilders.get(lastEmbed).getFields().get(0).getValue(), false);
                    embedBuilder.addField("Heal", embedBuilders.get(lastEmbed).getFields().get(1).getValue(), false);
                    embedBuilder.addField("Dps", embedBuilders.get(lastEmbed).getFields().get(2).getValue(), false);
                    embedBuilder.addField("BackupTank", embedBuilders.get(lastEmbed).getFields().get(3).getValue(), false);
                    embedBuilder.addField("BackupHeal", finalString, false);
                    embedBuilder.addField("BackupDps", embedBuilders.get(lastEmbed).getFields().get(5).getValue(), false);

                }

            } else {

                String[] s= embedBuilders.get(lastEmbed).getFields().get(1).getValue().split(" ");
                String finalString = "";
                for(String s1 : s){
                    if(!s1.equals(nom)){
                        finalString += s1 + " ";
                    }
                }

                embedBuilder.addField("Tank", embedBuilders.get(lastEmbed).getFields().get(0).getValue(),false);
                embedBuilder.addField("Heal", finalString,false);
                embedBuilder.addField("Dps", embedBuilders.get(lastEmbed).getFields().get(2).getValue(),false);
                embedBuilder.addField("BackupTank", embedBuilders.get(lastEmbed).getFields().get(3).getValue(),false);
                embedBuilder.addField("BackupHeal", embedBuilders.get(lastEmbed).getFields().get(4).getValue(),false);
                embedBuilder.addField("BackupDps", embedBuilders.get(lastEmbed).getFields().get(5).getValue(),false);

            }

            embedBuilder.setDescription("Réagissez pour réserver votre place dans le raid");
            embedBuilder.setColor(Color.orange);
            embedBuilders.set(lastEmbed,embedBuilder);
            event.getChannel().editMessageById(event.getMessageId(),embedBuilder.build()).queue();

            nbOfHeal --;


        }
    }
}