package com.mediaportal.analyzit.service;

import com.mediaportal.analyzit.dto.ShowStats;
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
    public static void watchedByUser() {
        List<User> users = DataRepository.fetchUsersData("C:\\Users\\Media Portal\\Desktop\\dadosUsuario.txt");
   
        List<Video> videos = DataRepository.fetchSessionData("C:\\Users\\Media Portal\\Desktop\\dadosSessao.txt");
        
        List<ShowStats> showStats = treatWatchedVideos(videos, users);
        
        showStats.forEach(stats -> {
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
    
    public static List<ShowStats> treatWatchedVideos(List<Video> videos, List<User> users){
        
        List<ShowStats> showStats = new ArrayList();
        
        videos.forEach(video -> {
            video.getSession().forEach(session -> {
                //Obtem dados do user e da sessão
                String userName = users.stream().filter(user -> user.getSessao().equals(session.getSessionId())).findAny().get().getNome();
                String date = users.stream().filter(user -> user.getSessao().equals(session.getSessionId())).findAny().get().getDate();
                String videoId = video.getVideoId();
                Integer totalSegments = session.getTotalSegments();
                Integer watchedSegments = session.getWatchedSegments();
                String watchedPercentage = session.getWatchedPercentage();
                Integer lastWatchedSegment = session.getLastWatchedSegment();
                
                //Verifica se o userName e o videoId existem em algum showStats, pega esse showStats e armazena nas variáveis.
                Optional<ShowStats> userNameInContext = showStats.stream().filter(stats -> stats.getUserName().equals(userName)).findAny();
                Optional<ShowStats> videoIdInContext = showStats.stream().filter(stats -> stats.getVideoId().equals(videoId)).findAny();
                
                //Se já existir um mesmo user e video, obter o showStats desse user existente e atualizar os dados nele, sem criar um novo. 
                if (!showStats.isEmpty() && userNameInContext.isPresent() && videoIdInContext.isPresent()) {

                    //Salvar a data mais atual (PRECISA SER TRATADO AINDA!!!)
                    userNameInContext.get().setDate(date);
                    userNameInContext.get().setWatchedSegments(watchedSegments);
                    userNameInContext.get().setLastWatchedSegment(lastWatchedSegment);
                    userNameInContext.get().setWatchedPercentage(watchedPercentage);
                }else{
                    //Cria um novo showStats
                    showStats.add(new ShowStats(userName, date, videoId, totalSegments, watchedSegments, watchedPercentage, lastWatchedSegment));
                }
            });
        });
        return showStats;
    }
}
