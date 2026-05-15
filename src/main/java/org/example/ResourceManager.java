package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ResourceManager {

    private static Path path;

    public static void textureExtraction() throws IOException {
        path = Files.createTempDirectory("textures");
        path.toFile().deleteOnExit();

        String[] textures = {"textures/PlayerStay.png", "textures/PlayerWalk1.png", "textures/PlayerWalk2.png", "textures/PlayerWalk3.png"};

        for(String _path : textures){
            InputStream in = ResourceManager.class.getClassLoader().getResourceAsStream(_path);

            if(in == null){continue;}

            Path outFile = path.resolve(Path.of(_path).getFileName());
            Files.copy(in, outFile, StandardCopyOption.REPLACE_EXISTING);
            in.close();
        }
    }

    public static String getTexturePath(String texture){
        if (path != null){
            Path _path = path.resolve(texture);
            if(Files.exists(_path)){
                return _path.toAbsolutePath().toString();
            }
        }

        return "src/main/resources/textures/" + texture;
    }

}
