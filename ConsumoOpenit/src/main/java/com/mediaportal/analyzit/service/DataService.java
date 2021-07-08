package com.mediaportal.analyzit.service;

import com.mediaportal.analyzit.dto.MostWatched;
import com.mediaportal.analyzit.dto.Session;
import com.mediaportal.analyzit.dto.UsefulStats;
import com.mediaportal.analyzit.dto.User;
import com.mediaportal.analyzit.dto.Video;
import com.mediaportal.analyzit.dto.WatchedStats;
import com.mediaportal.analyzit.repository.DataRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Giovanny Azevedo
 */
public class DataService {

    public static void getAllData() {
        List<User> users = DataRepository.fetchUsersData();
        List<Video> videos = DataRepository.fetchSessionData();

        List<UsefulStats> usefulStats = arrangeUsefulStats(videos, users);

        showWatchedByUser(usefulStats, users);

        showMostWatchedVideos(usefulStats, 5);

    }

    public static List<UsefulStats> arrangeUsefulStats(List<Video> videos, List<User> users) {

        List<UsefulStats> usefulStats = new ArrayList();

        videos.forEach(video -> {
            video.getSession().forEach(session -> {
                //Obtem dados do user e da sessão
                try{
                    
                    String userName = users.stream().filter(user -> user.getSession().equals(session.getSessionId())).findAny().get().getUserName();
                    String date = treatDate(users, session);
                    String videoId = video.getVideoId();
                    Integer totalSegments = session.getTotalSegments();
                    Integer watchedSegments = session.getWatchedSegments();
                    String watchedPercentage = session.getWatchedPercentage();
                    Integer lastWatchedSegment = session.getLastWatchedSegment();

                    //Verifica se o userName e o videoId existem em algum usefulStats, pega esse usefulStats e armazena nas variáveis.
                    Optional<UsefulStats> usefulStatsAlreadyExists = usefulStats.stream()
                            .filter(stats -> stats.getUserName().equals(userName) && stats.getVideoId().equals(videoId))
                            .findAny();

                    //Se já existir um mesmo user e video, obter o usefulStats desse user existente e atualizar os dados nele, sem criar um novo. 
                    if (!usefulStats.isEmpty() && usefulStatsAlreadyExists.isPresent()) {
                        usefulStatsAlreadyExists.get().setDate(date);
                        usefulStatsAlreadyExists.get().setWatchedSegments(watchedSegments);
                        usefulStatsAlreadyExists.get().setLastWatchedSegment(lastWatchedSegment);
                        usefulStatsAlreadyExists.get().setWatchedPercentage(watchedPercentage);
                    } else {
                        //Cria um novo usefulStats
                        usefulStats.add(new UsefulStats(userName, date, videoId, totalSegments, watchedSegments, watchedPercentage, lastWatchedSegment));
                    }
                }catch(Exception e){
                    System.out.println("[ERROR] [ERROR] No user found for session '" + session.getSessionId() + "'!");
                }
            });
        });
        return usefulStats;
    }

    public static String treatDate(List<User> users, Session session) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuuMMdd HH:mm:ss:SSS");
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-uuuu");
        String date = "";
        
        try {
            date = users.stream().filter(user -> user.getSession().equals(session.getSessionId())).findAny().get().getDate();
        }catch(Exception e){
            date = null;
        }
        

        String stringToTest = date + " 00:00:00:000";

        if (date != null && !date.isEmpty()) {
            try {
                LocalDateTime dateTime = LocalDateTime.parse(stringToTest, formatter);
                date = dateTime.format(dateFormat);
            } catch (DateTimeParseException dtpe) {
                date = null;
            }
        }else{
            date = null;
        }

        return date;
    }

    public static void showWatchedByUser(List<UsefulStats> usefulStats, List<User> users) {

        List<String> usersFound = new ArrayList();
        List<String> allVideosOfUser = new ArrayList();
        List<WatchedStats> watchedIncomplete = new ArrayList();

        users.forEach(user -> {
            if (!usersFound.contains(user.getUserName())) {
                System.out.println("=========================================================================");
                System.out.println("[" + user.getUserName() + "]");

                //Busca todos os vídeos de cada usuário
                usefulStats.forEach(stats -> {
                    if (user.getUserName().equals(stats.getUserName())) {
                        System.out.println("    " + stats.getVideoId());
                        allVideosOfUser.add(stats.getVideoId());
                        //Verifica se o vídeo foi assistido completamente ou não
                        if (stats.getLastWatchedSegment() < stats.getTotalSegments()) {
                            watchedIncomplete.add(new WatchedStats(stats.getVideoId(), stats.getTotalSegments(), stats.getWatchedSegments(), stats.getWatchedPercentage(), stats.getLastWatchedSegment()));
                        }
                    }
                });

                System.out.println("Total: " + allVideosOfUser.size());

                //Exibe os vídeos assistidos incompletos
                if (!watchedIncomplete.isEmpty()) {
                    System.out.println("Continue Assistindo:");
                    watchedIncomplete.forEach(video -> System.out.println("    " + video.getVideoId() + " | A partir de: " + video.getLastWatchedSegment()));
                }

                //Verifica e exibe os alertas
                showWarning(usefulStats, user);

                System.out.println("");
                usersFound.add(user.getUserName());
                allVideosOfUser.clear();
                watchedIncomplete.clear();
            }
        });
    }

    public static void showWarning(List<UsefulStats> usefulStats, User user) {
        List<String> invalidDateVideos = new ArrayList();
        Integer totalWatched = 0;

        //Adventencia por muito tempo de uso     
        for (int usr = 0; usr < usefulStats.size(); usr++) {
            if (user.getUserName().equals(usefulStats.get(usr).getUserName())) {
                //System.out.println("    " + usefulStats.get(usr).getWatchedSegments());
                totalWatched += usefulStats.get(usr).getWatchedSegments();
                if (usefulStats.get(usr).getDate() == null) {
                    invalidDateVideos.add(usefulStats.get(usr).getVideoId());
                }
            }
        }
        if (totalWatched >= 30000 || !invalidDateVideos.isEmpty()) {
            System.out.println("[WARNING] [WARNING]");
        }
        
        if (totalWatched >= 30000) {
            System.out.println("[WARNING] Assistido: (" + totalWatched + ") Highly recomended to take a walk on the park!!!");
        }
        
        if (!invalidDateVideos.isEmpty()) {
            invalidDateVideos.forEach(idv -> System.out.println("[WARNING] The 'date' on the video '" + idv +"' is NOT A VALID date, but it's OK "));
        }

    }

    public static void showMostWatchedVideos(List<UsefulStats> usefulStats, Integer topMostWatched) {
        List<MostWatched> mostWatched = new ArrayList();
        List<String> videoIds = new ArrayList();
        List<String> videoIdsNoDuplicates = new ArrayList();

        usefulStats.forEach(stats -> videoIds.add(stats.getVideoId()));

        videoIds.forEach(videoId -> {
            if (!videoIdsNoDuplicates.contains(videoId)) {
                videoIdsNoDuplicates.add(videoId);
            }
        });

        videoIdsNoDuplicates.forEach(videoId -> {
            int occurrences = Collections.frequency(videoIds, videoId);
            mostWatched.add(new MostWatched(videoId, occurrences));
        });

        //mostWatched.subList(mostWatched.size() - topMostWatched, mostWatched.size()).clear();
        Collections.sort(mostWatched, new Comparator<MostWatched>() {
            public int compare(MostWatched m1, MostWatched m2) {
                return m2.getViews().compareTo(m1.getViews()); //E visto por ultimo
            }
        });

        //Valida se existe uma lista de 'mais assistidos'
        if (!mostWatched.isEmpty()) {
            System.out.println("");
            System.out.println("########################################################################");
            System.out.println("Os " + topMostWatched + " mais assistidos:");
            mostWatched.subList(0, topMostWatched).forEach(video -> {
                System.out.println("Video: " + video.getVideoId() + " Views: " + video.getViews());
            });
        }
    }
}
