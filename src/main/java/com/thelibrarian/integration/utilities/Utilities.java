package com.thelibrarian.integration.utilities;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
    private Utilities() {
    }

    public static String generateRandomTitles() {
        List<String> randomTitles = new ArrayList<>();
        randomTitles.add("HARRYPOTTER");
        randomTitles.add("SEÑORDELOSANILLOS");
        randomTitles.add("THEHUNGERGAMES");
        randomTitles.add("TWILIGHT");
        randomTitles.add("THEGREATGATSBY");
        randomTitles.add("THEHOBBIT");
        randomTitles.add("THEDIARYOFAYOUNGGIRL");
        randomTitles.add("OFMICEANDMEN");
        randomTitles.add("THELITTLEPRINCE");
        randomTitles.add("THEGIRLWITHTHEDRAGONTATTOO");
        randomTitles.add("1984");
        randomTitles.add("TOKILLAMOCKINGBIRD");
        randomTitles.add("ANIMALFARM");
        randomTitles.add("CATCHINGFIRE");
        randomTitles.add("DAVINCICODE");
        randomTitles.add("GEISHA");
        randomTitles.add("PRIDEANDPREJUDICE");
        randomTitles.add("CRIMEANDPUNISHMENT");
        randomTitles.add("ONEHUNDREDYEARSOFSOLITUDE");
        randomTitles.add("THEODYSSEY");
        randomTitles.add("THEOLDMANANDTHESEA");
        randomTitles.add("THEALCHEMIST");
        randomTitles.add("THEADVENTURESOFHUCKLEBERRYFINN");
        randomTitles.add("THEGIRLONTHETRAIN");
        randomTitles.add("FRANKENSTAIN");
        randomTitles.add("LESMISERABLES");
        randomTitles.add("GONEWITHTHEWIND");
        randomTitles.add("THINKANDGROWRICH");
        randomTitles.add("ATALEOFTWOCITIES");
        randomTitles.add("THETALEOFPETERRABBIT");
        randomTitles.add("WARANDPEACE");
        randomTitles.add("ANGELSANDDEMONS");
        randomTitles.add("THEGODFATHER");

        int min = 0;
        int max = randomTitles.size();

        int randomTitle = (int) (Math.random() * (max - min) + min);

        return randomTitles.get(randomTitle);

    }

}
