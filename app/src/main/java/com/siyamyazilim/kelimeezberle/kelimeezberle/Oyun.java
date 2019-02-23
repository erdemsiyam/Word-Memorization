package com.siyamyazilim.kelimeezberle.kelimeezberle;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Oyun {
    public static Kelime secilenKelime;
    public static Kelime sonSecilenKelime;
    public static int dogru=0;
    public static int yanlis=0;
    public static boolean ilkAcisOldu=false;

    public static void yeniOyun()
    {

        ArrayList<Kelime> geriKalanKelimeler = new ArrayList<Kelime>(Kelime.kelimeler); // tüm kelimeleri 'geriKalanKelimeler' adlı diziye kopyalarız
        if(sonSecilenKelime != null)
        {
            geriKalanKelimeler.remove(sonSecilenKelime); // son seçilen kelime varsa, tekrar aynı kelime sorulmasın diye kelimeler arasından çıkarılır.
        }
        int randomIndex = new Random().nextInt(geriKalanKelimeler.size()); // rastgele kelime seçimi için rastgele sayi
        secilenKelime =  geriKalanKelimeler.get(randomIndex); // rastgele gelen kelime alınır.

        geriKalanKelimeler.remove(secilenKelime); // seçilen kelime, şık cevapları seçimlerinde tekrar karşımıza gelmemesi için silinir.
        sonSecilenKelime = secilenKelime; // son seçilen olarak da kaydederiz.
        for (Button buton : SinavYap.buttons) // ŞIK OLUŞTURMA
        {
            randomIndex = new Random().nextInt(geriKalanKelimeler.size()); // sıradaki şık için rastgele kelime bulunur, geriKalanKelimelerden
            Kelime sikIcinSecilenKelime = geriKalanKelimeler.get(randomIndex); // şık için seçilen kelime ele alınır
            buton.setText(sikIcinSecilenKelime.cevap); // şık için rastgele seçilen kelimenin cevabı sıradaki şıklardan birine koyulur.
            geriKalanKelimeler.remove(sikIcinSecilenKelime); //şık için seçilen kelime , geriKalanKelimelerden silinir, bu sayede birdaha buna rastlamayız.
        }

        // DOĞRU CEVAP HERHANGİ BİR ŞIKKA YÜKLENİR
        randomIndex = new Random().nextInt(SinavYap.buttons.size()); // butonların sayısında rastgele sayı çekilir
        SinavYap.buttons.get(randomIndex).setText(secilenKelime.cevap); // gelen rastgele butonun text'ine gerçek cevap yüklenir.

        //text'e ingilzce kelimemizi yazdırırız
        SinavYap.textView.setText(secilenKelime.ingilizce);

        //buttonları etkinleştir ve default background color yapılır
        for (Button buton:SinavYap.buttons) {
            buton.setEnabled(true);
            buton.setBackgroundResource(android.R.drawable.btn_default);
        }

        //sıradaki soru butonunu gizle
        SinavYap.button5.setVisibility(View.INVISIBLE);
    }
    public static void butonTiklama(Context context , String tiklananCevap)
    {
        if(tiklananCevap.equalsIgnoreCase(secilenKelime.cevap)) // TIKLANAN DOĞRU İSE
        {
            dogru++;
            SinavYap.textView2.setText("Doğru : "+ dogru);
            Toast.makeText(context,"Doğru", Toast.LENGTH_SHORT).show();
            secilenKelime.dogruSayisi++; // seçili kelimenin doğru yapılma sayısı artırılır.
            for (Button buton: SinavYap.buttons)
            {
                if(buton.getText().toString().equalsIgnoreCase(tiklananCevap))
                    buton.setBackgroundColor(Color.GREEN); // doğru cevap yeşil
                else
                    buton.setBackgroundResource(android.R.drawable.btn_default); //yanlış cevap defaut.
            }
        }
        else // TIKLANAN YANLIS İSE
        {
            yanlis++;
            SinavYap.textView3.setText("Yanlış : "+yanlis);
            Toast.makeText(context,"Yanlış", Toast.LENGTH_SHORT).show();
            secilenKelime.yanlisSayisi++; // seçili kelimenin yanlış yapılma sayısı artırılır.
            for (Button buton: SinavYap.buttons)
            {
                if(buton.getText().toString().equalsIgnoreCase(tiklananCevap))
                    buton.setBackgroundColor(Color.RED); // yanlış cevap kırmızı
                else if(buton.getText().toString().equalsIgnoreCase(secilenKelime.cevap))
                    buton.setBackgroundColor(Color.GREEN); //yanlış seçildi ama, doğru cevap gösterilir
                else
                    buton.setBackgroundResource(android.R.drawable.btn_default); //yanlış cevap defaut.
            }
        }

        //butonları etkinsizleştir
        for (Button buton:SinavYap.buttons) {
            buton.setEnabled(false);
        }
        //sıradaki soru botununu göster
        SinavYap.button5.setVisibility(View.VISIBLE);
    }

    /********************************************/
    /* LİSTE ÇEKME */
    public static void kelimeleriGetir(AppCompatActivity main, ListView listView){
        ArrayAdapter<Kelime> arrayAdapter = new ArrayAdapter<Kelime>( main , android.R.layout.simple_list_item_1 , Kelime.kelimeler );
        listView.setAdapter(arrayAdapter);

    }
    public static void enCokYanlisList(AppCompatActivity main, ListView listView){
        ArrayList<Kelime> siralanacakList = Kelime.kelimeler;
        Collections.sort(siralanacakList,yanlisSayisinaGoreSiralama);
        ArrayAdapter arrayAdapter = new ArrayAdapter( main , android.R.layout.simple_list_item_1 , siralanacakList );
        listView.setAdapter(arrayAdapter);

    }
    public static void encokDogruList(AppCompatActivity main, ListView listView){
        ArrayList<Kelime> siralanacakList = Kelime.kelimeler;
        Collections.sort(siralanacakList,dogruSayisinaGoreSiralama);
        ArrayAdapter arrayAdapter = new ArrayAdapter( main , android.R.layout.simple_list_item_1 , siralanacakList );
        listView.setAdapter(arrayAdapter);
    }
    /********************************************/
    /* SİRALAMA METODLARI */
    public static Comparator<Kelime> yanlisSayisinaGoreSiralama = new Comparator<Kelime>() {
        public int compare(Kelime k1, Kelime k2) {return k2.yanlisSayisi-k1.yanlisSayisi;}};
    public static Comparator<Kelime> dogruSayisinaGoreSiralama = new Comparator<Kelime>() {
        public int compare(Kelime k1, Kelime k2) {return k2.dogruSayisi-k1.dogruSayisi;}};
    /********************************************/
    /* KAYITLI VERİ İŞLEMLERİ */
    public static void kayitliVerileriCek(Context ctx){
        SharedPreferences sharedPreferences= ctx.getSharedPreferences("com.siyamyazilim.kelimeezberle.kelimeezberle", Context.MODE_PRIVATE);
        Kelime.kelimeler = new Gson().fromJson(sharedPreferences.getString("kelimeler", null),new TypeToken<ArrayList<Kelime>>() {}.getType());
        if(Kelime.kelimeler == null)
            Kelime.kelimeler = new ArrayList<Kelime>();
    }
    public static void verileriKaydet(Context ctx){
        SharedPreferences sharedPreferences= ctx.getSharedPreferences("com.siyamyazilim.kelimeezberle.kelimeezberle", Context.MODE_PRIVATE);
        String json = new Gson().toJson(Kelime.kelimeler); // datas = liste , liste string olarak kaydedilir.
        sharedPreferences.edit().putString("kelimeler",json).apply();
    }
    /********************************************/

}
