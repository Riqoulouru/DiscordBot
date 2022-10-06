package com.company.bot;


import com.company.bot.ConstantRunning.ReactionMembre;
import com.company.bot.commands.*;
import com.company.bot.commands.Music.JoinCommand;
import com.company.bot.commands.Music.PlayCommand;
import com.company.bot.commands.Music.SkipCommand;
import com.company.bot.commands.Music.StopCommand;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import javax.security.auth.login.LoginException;
import java.util.EnumSet;
import java.util.HashMap;

public class Main {

    public static JDABuilder builder;
    public static final String prefix = "&";
    private static final HashMap<Guild,String> listeningChannel = new HashMap<>();

    private static final String dataBaseUrl = "jdbc:sqlite:allDataBase.db";

    public static void main(String[] args) throws LoginException {

        String token = "";

        builder = JDABuilder.createDefault(
                        token,
                        GatewayIntent.GUILD_VOICE_STATES,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_MESSAGE_REACTIONS,
                        GatewayIntent.GUILD_EMOJIS)
                .disableCache(CacheFlag.MEMBER_OVERRIDES)
                .enableCache(CacheFlag.VOICE_STATE);

        builder.setBulkDeleteSplittingEnabled(false);

        builder.setCompression(Compression.NONE);

        builder.setActivity(Activity.playing("Discord"));


        registerListeners();

        builder.build();


    }

    public static void registerListeners() {
//        builder.addEventListeners(new BotCommands());
        builder.addEventListeners(new HotCombienMain());
        builder.addEventListeners(new GiveLink());
        builder.addEventListeners(new SetChannel());
        builder.addEventListeners(new AccepterRegles());
        builder.addEventListeners(new Help());
        builder.addEventListeners(new Clear());
        builder.addEventListeners(new NotifStream());
        builder.addEventListeners(new JoinCommand());
        builder.addEventListeners(new PlayCommand());
        builder.addEventListeners(new StopCommand());
        builder.addEventListeners(new SkipCommand());
        builder.addEventListeners(new Traduction());
    }

    public static HashMap<Guild, String> getListeningChannel() {
        return listeningChannel;
    }

    public static String getDataBaseUrl() {
        return dataBaseUrl;
    }
    public static void addServeurToListeningCHannel(Guild guild,String channel){
        if(!listeningChannel.containsKey(guild)){
            listeningChannel.put(guild,channel);
        }
    }

}
