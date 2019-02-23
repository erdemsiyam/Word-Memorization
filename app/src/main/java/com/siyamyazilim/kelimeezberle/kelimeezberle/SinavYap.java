package com.siyamyazilim.kelimeezberle.kelimeezberle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SinavYap extends AppCompatActivity {

    static TextView textView,textView2,textView3;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    static Button button5;
    static ArrayList<Button> buttons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinav_yap);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5); // SIRADAKİ SORUYA GEÇ BUTONU
        buttons = new ArrayList<Button>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);


        if(Kelime.kelimeler.size()<6)
        {
            Toast.makeText(getApplicationContext(),"Sınav olabilmek için en az 6 kelime eklemen gerekir.",Toast.LENGTH_LONG).show();
            Intent intent= new Intent(getApplicationContext(),KelimeEkle.class);
            startActivity(intent);  // kelime ekleye gönderilir.
        }
        else
        {
            Oyun.yeniOyun();
        }
    }
    @Override
    protected void onPause() {
        Oyun.verileriKaydet(this);
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        Oyun.verileriKaydet(this);
        super.onDestroy();
    }

    public void tiklama(View view) {

        switch (view.getId()) {
            case R.id.button:
                Oyun.butonTiklama(getApplicationContext(),button1.getText().toString());
                break;
            case R.id.button2:
                Oyun.butonTiklama(getApplicationContext(),button2.getText().toString());
                break;
            case R.id.button3:
                Oyun.butonTiklama(getApplicationContext(),button3.getText().toString());
                break;
            case R.id.button4:
                Oyun.butonTiklama(getApplicationContext(),button4.getText().toString());
                break;
        }
    }

    public void siradakiSoru(View view) {
        Oyun.yeniOyun();
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menulist,menu); // menüdeki veri tanıtılık
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.kelimeListesi)
        {
            Intent intent = new Intent(getApplicationContext(),Kelimeler.class); // menüdeki veriye tıklayınca oluşacak intent belirlenir farklı main'e geçilir
            startActivity(intent);
        }
        if(item.getItemId() == R.id.kelimeEkle)
        {
            Intent intent = new Intent(getApplicationContext(),KelimeEkle.class); // menüdeki veriye tıklayınca oluşacak intent belirlenir farklı main'e geçilir
            startActivity(intent);
        }
        if(item.getItemId() == R.id.sinav)
        {
            Toast.makeText(getApplicationContext(),"buradasın",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
