package com.mediaportal.analyzit.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mediaportal.analyzit.dto.Session;
import com.mediaportal.analyzit.dto.User;
import com.mediaportal.analyzit.dto.Video;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovanny Azevedo
 */
public class DataRepository {

    public static List<User> fetchUsersData() {
        
        ObjectMapper objectMapper = new ObjectMapper();
        List<User> listUser = null;
        try {
            listUser = objectMapper.readValue(new File("C:\\Users\\GaoNpre\\Desktop\\dadosUsuario.json"), new TypeReference<List<User>>(){});
        }catch(IOException e){
            System.err.printf("Error to open file: %s.\n", e.getMessage());
        }
        return listUser;
    }

    public static List<Video> fetchSessionData() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Session> sessions = null;
        List<Video> videos = null;
        try {
            sessions = objectMapper.readValue(new File("C:\\Users\\GaoNpre\\Desktop\\dadosSessao.json"), new TypeReference<List<Session>>(){});
            videos = buildVideos(sessions);
        } catch (IOException e) {
            System.err.printf("Error to open file: %s.\n", e.getMessage());
        }
        return videos;
    }

    private static List<Video> buildVideos(List<Session> sessions) {
        List<String> videoIds = new ArrayList();
        List<Video> videos = new ArrayList();

        sessions.forEach(session -> {
            try{
                String videoId = session.getVideoId();
                String sessionId = session.getSessionId();
                Integer totalSegments = session.getTotalSegments();
                Integer watchedSegments = session.getWatchedSegments();
                String watchedPercentage = session.getWatchedPercentage();
                Integer lastWatchedSegment = session.getLastWatchedSegment();

                //Verifica se já existe algum vídeo criado com esse videoId
                if (!videoIds.contains(videoId)) {
                    videoIds.add(videoId);
                    Video video = new Video(videoId);
                    video.setSession(new Session(sessionId, totalSegments, watchedSegments, watchedPercentage, lastWatchedSegment));
                    videos.add(video);
                } else {
                    videos.get(videoIds.indexOf(videoId))
                            .setSession(new Session(sessionId, totalSegments, watchedSegments, watchedPercentage, lastWatchedSegment));
                }
            }catch(Exception e){
                System.err.printf("[Error] Could not parse an empty value on method: buildVideos().\n");
            }
        }); 
        
        return videos;
    }
}
