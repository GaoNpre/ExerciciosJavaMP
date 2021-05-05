package com.mycompany.consumoopenit;

import java.util.Calendar;
import java.text.DateFormat;
import java.util.Date;

/**
 *
 * @author Media Portal
 */
public class Video {
        private String name;
        private Date date;
        private float duracao;
        private float tempoAssistido;

    public Video(String name, Date date, float duracao, float tempoAssistido) {
        this.name = name;
        this.date = date;
        this.duracao = duracao;
        this.tempoAssistido = tempoAssistido;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public float getDuracao() {
        return duracao;
    }

    public float getTempoAssistido() {
        return tempoAssistido;
    }
        
}
