package springboot.profpilot.global.config.security;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseInitializer {
    @Value("${firebase.config.path}")
    private String firebaseConfigPath;


    @Bean
    public FirebaseApp firebaseApp() throws IOException {

//        String path = FirebaseInitializer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        System.out.println("Current file location: " + path); // Current file location: nested:/app.jar/!BOOT-INF/classes/!/

//        // ClassLoader를 사용하여 JAR 파일 내부의 리소스를 읽음
//        ClassPathResource classPathResource = new ClassPathResource("firebase.json");
//        InputStream serviceAccount = classPathResource.getInputStream();
//        System.out.println("classPathResource: " + classPathResource); // classPathResource: class path resource [firebase.json]

        FileInputStream serviceAccount =
                new FileInputStream("C:\\Users\\admin\\Documents\\GitHub\\bootcamp-game-project\\backend(springboot)\\springboot\\src\\main\\resources\\firebase.json");




//        FileInputStream serviceAccount =
//                new FileInputStream("C:\\Users\\admin\\project\\bootcamp-game-project\\backend(springboot)\\springboot\\src\\main\\resources\\firebase.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("soccergame-team5.appspot.com")
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        return app;
    }

    @Bean
    public FirebaseAuth getFirebaseAuth() throws IOException {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(firebaseApp());
        return firebaseAuth;
    }
}