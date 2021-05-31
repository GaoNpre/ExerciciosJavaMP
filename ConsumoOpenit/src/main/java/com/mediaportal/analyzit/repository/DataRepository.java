package com.mediaportal.analyzit.repository;

import com.mediaportal.analyzit.dto.Session;
import com.mediaportal.analyzit.dto.User;
import com.mediaportal.analyzit.dto.Video;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Giovanny Azevedo
 */
public class DataRepository {

    public static List<User> fetchUsersData(String path) {
        List<User> users = new ArrayList();
        try {
            BufferedReader data = new BufferedReader(new FileReader(path));
            String dataLines = null;
            while ((dataLines = data.readLine()) != null) {
                String[] line = dataLines.split("\\|");
                users.add(new User(line[0], line[1], line[2]));
            }
        } catch (IOException e) {
            System.err.printf("Error to open file: %s.\n", e.getMessage());
        }
        return users;
    }

    public static List<Video> fetchSessionData(String path) {
        List<Video> videos = new ArrayList();
        try {
            BufferedReader data = new BufferedReader(new FileReader(path));
            String dataLines = null;
            List<String[]> lines = new ArrayList<String[]>();
            while ((dataLines = data.readLine()) != null) {
                lines.add(dataLines.split("\\|"));
            }
            videos = buildVideos(lines);
        } catch (IOException e) {
            System.err.printf("Error to open file: %s.\n", e.getMessage());
        }
        return videos;
    }

    private static List<Video> buildVideos(List<String[]> lines) {
        List<String> videoIds = new ArrayList();
        List<Video> videos = new ArrayList();

        for (int i = 0; i < lines.size(); i++) {
            try{
                String videoId = lines.get(i)[0];
                String sessionId = lines.get(i)[1];
                Integer totalSegments = Integer.parseInt(lines.get(i)[2]);
                Integer watchedSegments = Integer.parseInt(lines.get(i)[3]);
                String watchedPercentage = lines.get(i)[4];
                Integer lastWatchedSegment = Integer.parseInt(lines.get(i)[5]);

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
            
        }
        return videos;
    }
}
