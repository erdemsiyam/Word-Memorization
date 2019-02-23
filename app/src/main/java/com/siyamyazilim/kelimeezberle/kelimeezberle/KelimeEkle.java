package com.siyamyazilim.kelimeezberle.kelimeezberle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class KelimeEkle extends AppCompatActivity {

    EditText editText,editText2 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelime_ekle);

        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
    }

    public void kelimeEkle(View view) {
        String ing = editText.getText().toString();
        String turk = editText2.getText().toString();
        if(ing.trim().equals("") || turk.trim().equals("")) // girilen kelimelerden biri boşsa, geri döndür
        {
            Toast.makeText(getApplicationContext(),"Boş veri girmeyiniz",Toast.LENGTH_SHORT).show();
            return;
        }
        for (Kelime kel : Kelime.kelimeler)
        {
            if(kel.ingilizce.trim().equalsIgnoreCase(ing.trim()))
            {
                Toast.makeText(getApplicationContext(),"Böyle bir ingilizce kelime mevcut",Toast.LENGTH_LONG).show();
                return;
            }
        }
        new Kelime(ing,turk);
        Toast.makeText(getApplicationContext(),"Eklendi.",Toast.LENGTH_SHORT).show();
        editText.setText("");
        editText2.setText("");

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
            Toast.makeText(getApplicationContext(),"buradasın",Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.sinav)
        {
            Intent intent = new Intent(getApplicationContext(),SinavYap.class); // menüdeki veriye tıklayınca oluşacak intent belirlenir farklı main'e geçilir
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
