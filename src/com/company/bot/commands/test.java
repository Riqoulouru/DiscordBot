package com.company.bot.commands;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.darkprograms.speech.translator.GoogleTranslate;
public class test {

    private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
    private static final String CLIENT_SECRET = "PUBLIC_SECRET";
    private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";
    public static void main(String[] args) {
        String fromLang = "fr";
        String toLang = "zh-CN";
        String text = "Mangeons le caca";

        String translatedText = null;

        String expecetedText = "Hi . Good Morning";

        System.out.println(translatedText);



    }
}
