package com.company.bot.commands;

import com.company.bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class HotCombienMain extends ListenerAdapter {

    private User memberOne;
    private User memberTwo;
    private boolean GameIsLunched;
    private TextChannel textChannel;
    private int numberToUse;
    private int numberOfMemberOne;
    private int numberOfMemberTwo;



    public HotCombienMain() {
        numberOfMemberOne = -1;
        numberOfMemberTwo = -1;

    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {



        if(event.getChannel().getName().equals(Main.getListeningChannel().get(event.getGuild()))) {

            String[] args = event.getMessage().getContentRaw().split(" ");
            if (event.getGuild().getName().equals("Riqou's Server")) {
                if (args.length == 2) {

                    if (args[0].equals(Main.prefix + "HotCombien")) {

                        numberToUse = Integer.parseInt(args[1]);
                        memberOne = event.getMember().getUser();
                        GameIsLunched = true;

                        textChannel = event.getChannel();

                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setTitle("Hot combien " + numberToUse);
                        embedBuilder.addField("", "Hot combien lancé par " + memberOne.getName(), false);
                        embedBuilder.setDescription("Réagissez pour participer au hot combien");
                        embedBuilder.setColor(Color.red);
                        event.getChannel().sendMessage(embedBuilder.build()).queue(message -> {
                            message.addReaction("💪").queue();
                        });

                        Thread timeBeforeClosingGame = new HotCombienWaiting(this);
                        timeBeforeClosingGame.start();

                    }
                }
            }
        }

    }


    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        if(!event.getUser().getName().equals("Riqou'sBot")) {
            if(event.getReactionEmote().getName().equals("💪")) {
                if(memberTwo == null && event.getMember().getUser() != memberOne){
                    memberTwo = event.getMember().getUser();
                    textChannel.sendMessage(memberTwo.getName() + " a rejoin la parti ! \n" +
                            "répondez moi maintenant en privé !").queue();

                    memberOne.openPrivateChannel().complete().sendMessage("Envoyer moi un nombre entre 0 et " + numberToUse).queue();
                    memberTwo.openPrivateChannel().complete().sendMessage("Envoyer moi un nombre entre 0 et " + numberToUse).queue();
                    Thread tempsReponseJoueurs = new HotCombienWaitingResponse(this);
                    tempsReponseJoueurs.start();
                }
            }

        }
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        if(!event.getAuthor().getName().equals("Riqou'sBot")){
            if(event.getAuthor() == memberOne){
                try {
                    numberOfMemberOne = Integer.parseInt(event.getMessage().getContentRaw());
                    if(numberOfMemberOne < 0 || numberOfMemberOne > numberToUse){
                        memberOne.openPrivateChannel().complete().sendMessage("Le nombre n'est pas compris entre 0 et " + numberToUse +" réessayez").queue();
                        numberOfMemberOne = -1;

                    }else {
                        afficherResultat();
                    }

                } catch (NumberFormatException e) {
                    memberOne.openPrivateChannel().complete().sendMessage("Erreur, ceci n'est pas un nombre accèptable").queue();
                    numberOfMemberOne = -1;
                }

            } else if(event.getAuthor().getName().equals(memberTwo.getName())){
                System.out.println(event.getMessage().getContentRaw());
                try {
                    numberOfMemberTwo = Integer.parseInt(event.getMessage().getContentRaw());
                    if(numberOfMemberTwo < 0 || numberOfMemberTwo > numberToUse){
                        System.out.println(numberOfMemberTwo);
                        System.out.println(numberToUse);
                        memberTwo.openPrivateChannel().complete().sendMessage("Le nombre d'est pas compris entre 0 et " + numberToUse + " réessayez").queue();
                        numberOfMemberTwo = -1;

                    } else {
                        afficherResultat();
                    }

                } catch (Exception e) {
                    memberTwo.openPrivateChannel().complete().sendMessage("Erreur, ceci n'est pas un nombre accèptable").queue();
                    numberOfMemberOne = -1;
                }
            }
        }

    }

    public void afficherResultat(){
        if(numberOfMemberTwo != -1 && numberOfMemberOne != -1){

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("Résultats !");
            embedBuilder.addField("Résultat de " + memberOne.getName(), "Le n° est : " + numberOfMemberOne, false);
            embedBuilder.addField("Résultat de " + memberTwo.getName(), "Le n° est : " + numberOfMemberTwo, false);
            embedBuilder.setColor(Color.red);

            if(numberOfMemberOne != numberOfMemberTwo){
                embedBuilder.addField("","Ce n'est pas le même numéro ! Lancez le reverse !",false);
            }

            textChannel.sendMessage(embedBuilder.build()).queue();

        }
    }


    public void secondPlayerJoin(){
        if(memberTwo == null){
            textChannel.sendMessage("Aucun joueur n'a rejoint la partie, celle ci est annulé.").queue();
            this.memberOne = null;
            this.memberTwo = null;
            GameIsLunched = false;
            textChannel = null;
            numberOfMemberOne = -1;
            numberOfMemberTwo = -1;
        }
    }

    public void playerResponse(){
        if(numberOfMemberOne == -1 && numberOfMemberTwo == -1){
            textChannel.sendMessage("un des joueurs n'a pas répondu, reset de la partie.").queue();
            this.memberOne = null;
            this.memberTwo = null;
            GameIsLunched = false;
            textChannel = null;
            numberOfMemberOne = -1;
            numberOfMemberTwo = -1;
        }
    }

}
