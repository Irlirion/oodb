package com.company.lab2;

import com.company.lab2.domain.SocialNetwork;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Lab2SaveDB {
    public static void saveSocianNetwork(SocialNetwork socialNetwork) throws IOException {

        if (socialNetwork != null) {
            Gson gson = new Gson();
            String personsAsJson = gson.toJson(socialNetwork);

            System.out.println(personsAsJson);

            try (OutputStream os = new FileOutputStream(new File("social_network.json"))) {
                os.write(personsAsJson.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }

        }
    }
}
