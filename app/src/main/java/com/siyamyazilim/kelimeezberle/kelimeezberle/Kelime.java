package com.siyamyazilim.kelimeezberle.kelimeezberle;

import java.util.ArrayList;

public class Kelime {
    public static ArrayList<Kelime> kelimeler = new ArrayList<Kelime>();

    public Kelime(String ingilizce, String cevap)
    {
        this.ingilizce=ingilizce;
        this.cevap = cevap;
        this.dogruSayisi=0;
        this.yanlisSayisi=0;

        kelimeler.add(this);

    }
    public String ingilizce;
    public String cevap;
    public int dogruSayisi;
    public int yanlisSayisi;

    public void kelimeSil() throws Throwable {
        kelimeler.remove(this);
        this.finalize();
        System.gc(); // nesneyi öldürdüğünden emin değilim
    }

    @Override
    public String toString() {
        return "\t\t"+ingilizce+"\t\t=\t\t"+cevap+"\t\t\t\t("+dogruSayisi+"\t/"+yanlisSayisi+")";
    }
}
