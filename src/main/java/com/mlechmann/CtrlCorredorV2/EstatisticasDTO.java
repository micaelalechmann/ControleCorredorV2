package com.mlechmann.CtrlCorredorV2;

public class EstatisticasDTO {
    private int qntCorridas;
    private String media;
    private String mediana;
    private String desvioPadrao;

    public int getQntCorridas() {
        return qntCorridas;
    }

    public void setQntCorridas(int qntCorridas) {
        this.qntCorridas = qntCorridas;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMediana() {
        return mediana;
    }

    public void setMediana(String mediana) {
        this.mediana = mediana;
    }

    public String getDesvioPadrao() {
        return desvioPadrao;
    }

    public void setDesvioPadrao(String desvioPadrao) {
        this.desvioPadrao = desvioPadrao;
    }
}
