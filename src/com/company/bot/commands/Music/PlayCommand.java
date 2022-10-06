package com.company.bot.commands.Music;

import com.company.bot.Main;
import com.company.bot.lavaPlayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

@SuppressWarnings("ConstantConditions")
public class PlayCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");




        if (args[0].equalsIgnoreCase(Main.prefix + "play")) {
            if(args.length >= 1) {

                final TextChannel channel = event.getChannel();
                final Member self = event.getGuild().getSelfMember();
                final GuildVoiceState selfVoiceState = self.getVoiceState();

                if (!selfVoiceState.inVoiceChannel()) {
                    channel.sendMessage("Je dois être dans un channel pour jouer de la ZIC MU").queue();
                    return;
                }

                final Member member = event.getMember();
                final GuildVoiceState memberVoiceState = member.getVoiceState();

                if (!memberVoiceState.inVoiceChannel()) {
                    channel.sendMessage("Vous devez être dans un channel pour que cette commande fonctionne").queue();
                    return;
                }

                if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
                    channel.sendMessage("Vous devez être dans le même channel que moi pour lancer de la musique").queue();
                    return;
                }


                String link = Arrays.toString(Arrays.copyOfRange(args, 1, args.length)).replace("[", "").replace("]", "");
                System.out.println(link);
                if(!isUrl(link)){
                    link = "ytsearch:" + link;
                }

                PlayerManager.getINSTANCE().loadAndPlay(channel, link);
            } else {
                event.getChannel().sendMessage("L'usage correcte de la commande est : " + Main.prefix + "play youtubeLink").queue();
            }
        }

    }


    private boolean isUrl(String url){
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e){
            return false;
        }
    }
}
