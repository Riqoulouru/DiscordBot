package com.company.bot.commands;

import com.company.bot.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.RestAction;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class NotifStream extends ListenerAdapter{

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        Main.addServeurToListeningCHannel(event.getGuild(), event.getChannel().getName());

        if (event.getGuild().getName().equals("Stream serveur")) {

            String[] args = event.getMessage().getContentRaw().split(" ");

            if (args[0].equalsIgnoreCase(Main.prefix + "notifs")) {
                if (event.getGuild().getOwner() == event.getMember()) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.addField("Notifs de stream", "Pour les notifs de stream, il vous suffit de réagir à l'émote : 👾 pour les recevoir !", false);
                    embedBuilder.addField("", "Si vous ne les voulez plus, il vous suffit d'enlever l'emote", false);
                    embedBuilder.setColor(Color.orange);

                    event.getChannel().sendMessage(embedBuilder.build()).queue(message -> {
                        message.addReaction("👾").queue();
                    });
                }
            }

            if(event.getChannel().getName().equals(Main.getListeningChannel().get(event.getGuild()))){

                if(args[0].equalsIgnoreCase(Main.prefix + "streaming")) {

                    Role role = event.getGuild().getRoleById("833123343724183574");

                    if (event.getMember().getUser().getId().equals("212204362058039296")) {
                        event.getGuild().getTextChannelById("832964945268178944").sendMessage("Je suis en stream ! Venez me voir ici\n" +
                                "https://twitch.tv/riqoulouru " + role.getAsMention()).queue();

                    }

                    if (event.getMember().getUser().getId().equals("174553067411341313")) {
                        event.getGuild().getTextChannelById("832964945268178944").sendMessage("Je suis en stream ! Venez me voir ici\n" +
                                "https://twitch.tv/alta762 " + role.getAsMention()).queue();

                    }

                    if (event.getMember().getUser().getId().equals("278191750626017280")) {
                        event.getGuild().getTextChannelById("832964945268178944").sendMessage("Je suis en stream ! Venez me voir ici\n" +
                                "https://www.twitch.tv/rav3blak " + role.getAsMention()).queue();

                    }

                }


            }
        }
    }


    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {

        if(event.getGuild().getName().equals("Stream serveur")) {

            if (!event.getUser().getName().equals("Riqou'sBot")) {

                if (event.getReactionEmote().getName().equals("👾")) {

                    if (event.getChannel().getName().equals("notifstream")) {


                        event.getGuild().addRoleToMember(event.getMember(), Objects.requireNonNull(event.getJDA().getRoleById("833123343724183574"))).submit();


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

        if(event.getGuild().getName().equals("Stream serveur")) {

            if (!u.getName().equals("Riqou'sBot")) {

                if (event.getReactionEmote().getName().equals("👾")) {
                    if (event.getChannel().getName().equals("notifstream")) {

                        event.getGuild().removeRoleFromMember(member, Objects.requireNonNull(event.getJDA().getRoleById("833123343724183574"))).submit();
                    }
                }
            }
        }
    }




}
