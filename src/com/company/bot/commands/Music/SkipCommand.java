package com.company.bot.commands.Music;

import com.company.bot.Main;
import com.company.bot.lavaPlayer.GuildMusicManager;
import com.company.bot.lavaPlayer.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class SkipCommand extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Main.prefix + "skip")) {

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

            GuildMusicManager musicManager = PlayerManager.getINSTANCE().getMusicManager(event.getGuild());

            AudioPlayer audioPlayer = musicManager.audioPlayer;

            if(audioPlayer.getPlayingTrack() == null){
                channel.sendMessage("Il n'y a pas de musique en cour de lecture").queue();
                return;
            }

            musicManager.scheduler.nextTrack();
            channel.sendMessage("Skip de la musique en cours").queue();


        }
    }
}
