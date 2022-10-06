package com.company.bot.commands.Music;

import com.company.bot.Main;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ConstantConditions")
public class JoinCommand extends ListenerAdapter {


    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Main.prefix + "join")) {

            final TextChannel channel = event.getChannel();
            final Member self = event.getGuild().getSelfMember();
            final GuildVoiceState selfVoiceState = self.getVoiceState();


            if (selfVoiceState.inVoiceChannel()) {
                channel.sendMessage("je suis déjà dans un channel").queue();
                return;
            }

            final Member member = event.getMember();
            final GuildVoiceState memberVoiceState = member.getVoiceState();

            if (!memberVoiceState.inVoiceChannel()) {
                channel.sendMessage("Vous devez être dans un channel pour que cette commande fonctionne").queue();
                return;
            }

            final AudioManager audioManager = event.getGuild().getAudioManager();
            final VoiceChannel memberChannel = memberVoiceState.getChannel();

            audioManager.openAudioConnection(memberChannel);
            channel.sendMessage("Connexion à " + memberChannel.getName()).queue();
        }
    }
}
