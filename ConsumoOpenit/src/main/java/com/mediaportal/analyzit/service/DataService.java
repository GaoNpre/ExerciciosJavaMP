package com.mediaportal.analyzit.service;

import com.mediaportal.analyzit.dto.UsefulStats;
import com.mediaportal.analyzit.dto.User;
import com.mediaportal.analyzit.dto.Video;
import com.mediaportal.analyzit.repository.DataRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Giovanny Azevedo
 */
public class DataService {
    public static void listWatchedByUser() {
        List<User> users = DataRepository.fetchUsersData();
        List<Video> videos = DataRepository.fetchSessionData();
        
        List<UsefulStats> usefulStats = arrangeUsefulStats(videos, users);
        
        usefulStats.forEach(stats -> {
            System.out.println("Usuário:    " + stats.getUserName());
            System.out.println("Data:       " + stats.getDate());
            System.out.println("VídeoID:    " + stats.getVideoId());
            System.out.println("Duração:    " + stats.getTotalSegments());
            System.out.println("Assistido:  " + stats.getWatchedSegments());
            System.out.println("Assistido%: " + stats.getWatchedPercentage());
            System.out.println("Parou em:   " + stats.getLastWatchedSegment());
            System.out.println("");
        });
        
    }
    
    public static List<UsefulStats> arrangeUsefulStats(List<Video> videos, List<User> users){
        
        List<UsefulStats> usefulStats = new ArrayList();
        
        videos.forEach(video -> {
            video.getSession().forEach(session -> {
                //Obtem dados do user e da sessão
                String userName = users.stream().filter(user -> user.getSession().equals(session.getSessionId())).findAny().get().getUserName();
                String date = users.stream().filter(user -> user.getSession().equals(session.getSessionId())).findAny().get().getDate();
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
                }else{
                    //Cria um novo usefulStats
                    usefulStats.add(new UsefulStats(userName, date, videoId, totalSegments, watchedSegments, watchedPercentage, lastWatchedSegment));
                }
            });
        });
        return usefulStats;
    }
}
