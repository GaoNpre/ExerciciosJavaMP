package com.mediaportal.analyzit.service;

import com.mediaportal.analyzit.dto.User;
import com.mediaportal.analyzit.dto.Video;
import com.mediaportal.analyzit.repository.DataRepository;
import java.util.List;

/**
 *
 * @author Giovanny Azevedo
 */
public class DataService {
    public static void watchedByUser() {
        List<User> users = DataRepository.fetchUsersData("C:\\Users\\Media Portal\\Desktop\\dadosUsuario.txt");
        for(int x = 0; x < users.size(); x++){
            System.out.println("Nome:   " + users.get(x).getNome());
            System.out.println("Data:   " + users.get(x).getDate());
            System.out.println("Sessao: " + users.get(x).getSessao());
            System.out.println("");
        }
        System.out.println("=========================================================================");
        System.out.println("");
        List<Video> videos = DataRepository.fetchSessionData("C:\\Users\\Media Portal\\Desktop\\dadosSessao.txt");
        for(int x = 0; x < videos.size(); x++){
            System.out.println("VideoId:    " + videos.get(x).getVideoId());
            System.out.println("Sessões:");
            for (int i = 0; i < videos.get(x).getSession().size(); i++) {
                System.out.println("    SessionId:          " + videos.get(x).getSession().get(i).getSessionId());
                System.out.println("    TotalSegments:      " + videos.get(x).getSession().get(i).getTotalSegments());
                System.out.println("    WatchedSegments:    " + videos.get(x).getSession().get(i).getWatchedSegments());
                System.out.println("    WatchedPErcentage:  " + videos.get(x).getSession().get(i).getWatchedPercentage());
                System.out.println("    WatchedSegments:    " + videos.get(x).getSession().get(i).getLastWatchedSegment());
                System.out.println("");
            }
            System.out.println("");
        }
        System.out.println("=========================================================================");
        for (int i = 0; i < users.size(); i++) {
            System.out.println("Usuário:    " + users.get(i).getNome());
            System.out.println("Sessão:    " + users.get(i).getSessao());
            for (int j = 0; j < videos.size(); j++) {
                //System.out.println("VideoID:    " + users.get(i).getNome());
                for (int k = 0; k < videos.get(j).getSession().size(); k++) {
                    if(users.get(i).getSessao().equals(videos.get(j).getSession().get(k).getSessionId())){
                        System.out.println("    VideoID:    " + videos.get(j).getVideoId());
                        System.out.println("    Assistido:    " + videos.get(j).getSession().get(k).getWatchedPercentage());
                        System.out.println("    Parou em:    " + videos.get(j).getSession().get(k).getLastWatchedSegment());
                        System.out.println("");
                    }
                }
            }
            System.out.println("");
        }
    }
    
    //    public static void showAssistidosNaUltimaSemana(List<Video> videos) throws ParseException {
//        List<Video> assistidosCompletos = new ArrayList();
//        List<Video> assistidosIncompletos = new ArrayList();
//        
//        Instant nowDateInstant = Instant.now(); //Data atual
//        Instant before = nowDateInstant.minus(Duration.ofDays(7));
//        Date dateBefore = Date.from(before); //Data atual menos 7 dias
//        
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");      
//        String strDateContent = sdf.format(dateBefore);
//        Date dateBeforeConverted = sdf.parse(strDateContent);
//
//        for(int i = 0; i < videos.size(); i++){
//            Integer compareDates = videos.get(i).getDate().compareTo(dateBeforeConverted);
//            
//            //Verifica se a duração é igual ao tempo assistido e se a data de visualização do vídeo foi dentro dos últimos 7 dias
//            if(videos.get(i).getDuracao() == videos.get(i).getTempoAssistido() && compareDates > 0){
//                assistidosCompletos.add(videos.get(i));
//            }else if (videos.get(i).getDuracao() > videos.get(i).getTempoAssistido() && compareDates > 0){
//                assistidosIncompletos.add(videos.get(i));
//            }
//        }
//        System.out.println("    Assistiu totalmmente os vídeos:");
//        for(int j = 0; j < assistidosCompletos.size(); j++){
//            System.out.println("        Título: " + assistidosCompletos.get(j).getName());
//        }
//        System.out.println("    Acessou mas não viu totalmente os vídeos:");
//        for(int i = 0; i < assistidosCompletos.size(); i++){
//            System.out.println("        Título: " + assistidosIncompletos.get(i).getName());
//        }
//    }
}
