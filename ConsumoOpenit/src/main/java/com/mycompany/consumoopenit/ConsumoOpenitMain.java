package com.mycompany.consumoopenit;

import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Giovanny
 */
public class ConsumoOpenitMain {
    public static void main(String[] args) throws ParseException{
        //MOCK DE VIDEOS
        List<Video> videos = new ArrayList();
        //Formato da data
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
         
        //Video 1
        Date date_01 = sdf.parse("04/05/2021");
        Video video_01 = new Video("video_01", date_01, 120f, 65.2f);
        videos.add(video_01);
        
        //Video 2
        Date date_02 = sdf.parse("03/05/2021");
        Video video_02 = new Video("video_02", date_02, 123.4f, 10.2f);
        videos.add(video_02);
        
        //Video 3
        Date date_03 = sdf.parse("02/05/2021");
        Video video_03 = new Video("video_03", date_03, 322.5f, 322.5f);
        videos.add(video_03);
        
        //Video 4
        Date date_04 = sdf.parse("01/05/2021");
        Video video_04 = new Video("video_04", date_04, 143f, 143f);
        videos.add(video_04);
        
        //Video 5
        Date date_05 = sdf.parse("10/04/2021");
        Video video_05 = new Video("video_05", date_05, 74f, 61.2f);
        videos.add(video_05);
        
        //Video 6
        Date date_06 = sdf.parse("08/04/2021");
        Video video_06 = new Video("video_06", date_06, 410f, 220.7f);
        videos.add(video_06);
        
        
        //MOCK DE USUÁRIOS
        List<String> users_names = new ArrayList();
        users_names.add("Jorge");
        users_names.add("Romario");
        users_names.add("Ricardo");
        
        
        List<Usuario> users_data = new ArrayList();
        for(int i = 0; i < users_names.size(); i++){
            Usuario usr = new Usuario(users_names.get(i), videos);
            users_data.add(usr);
        }
        

        for(int i = 0; i < users_data.size(); i++){
            System.out.println("");
            System.out.println("Usuário: " + users_data.get(i).getNome());
            
            showAssistidosNaUltimaSemana(users_data.get(i).getVideos());
            
        }
        
    }

    public static void showAssistidosNaUltimaSemana(List<Video> videos) throws ParseException {
        List<Video> assistidosCompletos = new ArrayList();
        List<Video> assistidosIncompletos = new ArrayList();
        
        Instant nowDateInstant = Instant.now(); //Data atual
        Instant before = nowDateInstant.minus(Duration.ofDays(7));
        Date dateBefore = Date.from(before); //Data atual menos 7 dias
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");      
        String strDateContent = sdf.format(dateBefore);
        Date dateBeforeConverted = sdf.parse(strDateContent);

        for(int i = 0; i < videos.size(); i++){
            Integer compareDates = videos.get(i).getDate().compareTo(dateBeforeConverted);
            
            //Verifica se a duração é igual ao tempo assistido e se a data de visualização do vídeo foi dentro dos últimos 7 dias
            if(videos.get(i).getDuracao() == videos.get(i).getTempoAssistido() && compareDates > 0){
                assistidosCompletos.add(videos.get(i));
            }else if (videos.get(i).getDuracao() > videos.get(i).getTempoAssistido() && compareDates > 0){
                assistidosIncompletos.add(videos.get(i));
            }
        }
        System.out.println("    Assistiu totalmmente os vídeos:");
        for(int j = 0; j < assistidosCompletos.size(); j++){
            System.out.println("        Título: " + assistidosCompletos.get(j).getName());
        }
        System.out.println("    Acessou mas não viu totalmente os vídeos:");
        for(int i = 0; i < assistidosCompletos.size(); i++){
            System.out.println("        Título: " + assistidosIncompletos.get(i).getName());
        }
    }   
    
}
