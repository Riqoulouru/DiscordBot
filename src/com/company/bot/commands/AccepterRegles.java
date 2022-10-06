package com.company.bot.commands;

import com.company.bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class AccepterRegles extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        if (event.getGuild().getName().equals("Riqou's Server")) {
            event.getGuild().addRoleToMember(event.getMember(), Objects.requireNonNull(event.getJDA().getRoleById("833026347701502043"))).submit();
        }
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        Main.addServeurToListeningCHannel(event.getGuild(),event.getChannel().getName());

        if (event.getGuild().getOwner() == event.getMember()) {
            if(event.getGuild().getName().equals("Riqou's Server")) {

                String[] args = event.getMessage().getContentRaw().split(" ");

                if (args[0].equalsIgnoreCase(Main.prefix + "règles")) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();

                    embedBuilder.setColor(Color.orange);
                    embedBuilder.setTitle("Règles");
                    embedBuilder.addField("1. Les pseudonymes", "", false);

                    embedBuilder.addField("Votre pseudonyme et votre avatar sur Discord :", "", false);
                    embedBuilder.addField("Règle 1 : ", "Ne doit pas avoir de caractère pornographique.", true);
                    embedBuilder.addField("Règle 2 : ", "Ne doit pas pouvoir être confondu/ressemblant avec/à celui d'un membre du staff.", true);
                    embedBuilder.addField("Règle 3 : ", "Ne doit pas contenir de propos racistes, homophobes, sexistes ou faire référence à la drogue.", true);

                    embedBuilder.addField("2. Conduite à adopter :", "", false);
                    embedBuilder.addField("Règle 1 : ", "Pas d'insultes", true);
                    embedBuilder.addField("Règle 2 : ", "Soyez respectueux, courtois, poli envers les utilisateurs et le staff.", true);
                    embedBuilder.addField("Règle 3 : ", "Ne dévoilez sous aucun prétexte vos informations de compte, même à un membre du staff !", true);

                    embedBuilder.addField("3. Sanctions encourues :", "", false);
                    embedBuilder.addField("", "Le ban twitch + discord :)", true);

                    event.getGuild().getTextChannelById("832964872932556816").sendMessage(embedBuilder.build()).queue(message -> {
                        message.addReaction("✔").queue();
                    });
                }


            }
        }
    }




    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {

        if(event.getGuild().getName().equals("Riqou's Server")) {

            if (!event.getUser().getName().equals("Riqou'sBot")) {

                if (event.getReactionEmote().getName().equals("✔")) {

                    if (event.getChannel().getName().equals("bienvenue-et-règles")) {
                        event.getGuild().addRoleToMember(event.getMember(), Objects.requireNonNull(event.getJDA().getRoleById("832965190294306826"))).submit();

                        event.getGuild().removeRoleFromMember(event.getMember(), Objects.requireNonNull(event.getJDA().getRoleById("833026347701502043"))).submit();
                    }

                }
            }
        }
    }

    @Override
    public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {

        RestAction<Member> memberRestAction = event.retrieveMember();
        Member member = memberRestAction.complete();

        RestAction<User> user = event.retrieveUser();
        User u = user.complete();

        if (event.getGuild().getName().equals("Riqou's Server")) {
            if (!u.getName().equals("Riqou'sBot")) {

                if (event.getChannel().getName().equals("bienvenue-et-règles")) {

                    if (event.getReactionEmote().getName().equals("✔")) {

                        event.getGuild().addRoleToMember(member, Objects.requireNonNull(event.getJDA().getRoleById("833026347701502043"))).submit();

                        event.getGuild().removeRoleFromMember(member, Objects.requireNonNull(event.getJDA().getRoleById("832965190294306826"))).submit();
                    }
                }
            }
        }
    }
}
