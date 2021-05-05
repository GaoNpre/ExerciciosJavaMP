package com.mycompany.consumoopenit;

import java.util.List;
import java.util.Calendar;
import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author Giovanny
 */
public class Usuario {
    private String nome;
    private List<Video> videos;
    
    
    public Usuario(String nome, List<Video> videos) {
        this.nome = nome;
        this.videos = videos;
    }

    public String getNome() {
        return nome;
    }

    public List<Video> getVideos() {
        return videos;
    }

    
}
