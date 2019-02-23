package com.siyamyazilim.kelimeezberle.kelimeezberle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Kelimeler extends AppCompatActivity {
    ListView listView;
    static String sonSiralamaStili;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelimeler);


        Oyun.kayitliVerileriCek(this);

        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Kelime selectedItem = (Kelime) adapterView.getItemAtPosition(i);
                Toast.makeText(getApplicationContext(),String.valueOf(selectedItem),Toast.LENGTH_SHORT).show();            }
        });
        registerForContextMenu(listView); // LİSTVIEW nesnesi , context menü için kayıt edilir. Context menü = itemleri silmek için , uzun basış sonrası gelen menü
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



    public void tumKelimeleriGetir(View view) {
        if(!(Kelime.kelimeler.size()>0))
        {
            Toast.makeText(getApplicationContext(),"Hiç kelimen yok. Buradan ekleyebilirsin.",Toast.LENGTH_LONG).show();
            Intent intent= new Intent(getApplicationContext(),KelimeEkle.class);
            startActivity(intent);
            return;
        }
        Oyun.kelimeleriGetir(this,listView);
        sonSiralamaStili = "tum";
    }

    public void enCokYanlisYapilanlariGetir(View view) {
        if(!(Kelime.kelimeler.size()>0))
        {
            Toast.makeText(getApplicationContext(),"Hiç kelimen yok. Buradan ekleyebilirsin.",Toast.LENGTH_LONG).show();
            Intent intent= new Intent(getApplicationContext(),KelimeEkle.class);
            startActivity(intent);
            return;
        }
        Oyun.enCokYanlisList(this,listView);
        sonSiralamaStili = "yanlis";
    }

    public void enCokDogruYapilanlariGetir(View view) {
        if(!(Kelime.kelimeler.size()>0))
        {
            Toast.makeText(getApplicationContext(),"Hiç kelimen yok. Buradan ekleyebilirsin.",Toast.LENGTH_LONG).show();
            Intent intent= new Intent(getApplicationContext(),KelimeEkle.class);
            startActivity(intent);
            return;
        }
        Oyun.encokDogruList(this,listView);
        sonSiralamaStili = "dogru";
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) { // Menüden seçilen nesne için, sil seçeneği göstertme
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.listitemmenu, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position;

        switch (item.getItemId()) {
            case R.id.sil:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                        .getMenuInfo();
                position = (int) info.id;
                Kelime.kelimeler.remove(position);
                if(sonSiralamaStili.equalsIgnoreCase("tum"))
                {
                    Oyun.kelimeleriGetir(this,listView);
                }
                else if (sonSiralamaStili.equalsIgnoreCase("dogru"))
                {
                    Oyun.encokDogruList(this,listView);
                }
                else if (sonSiralamaStili.equalsIgnoreCase("yanlis"))
                {

                    Oyun.enCokYanlisList(this,listView);
                }
                break;
            /*case R.id.duzenle:
                break;*/
            default:
                break;
        }
        return super.onContextItemSelected(item);
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
            Toast.makeText(getApplicationContext(),"Buradasın",Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId() == R.id.kelimeEkle)
        {
            Intent intent = new Intent(getApplicationContext(),KelimeEkle.class); // menüdeki veriye tıklayınca oluşacak intent belirlenir farklı main'e geçilir
            startActivity(intent);
        }
        if(item.getItemId() == R.id.sinav)
        {
            Intent intent = new Intent(getApplicationContext(),SinavYap.class); // menüdeki veriye tıklayınca oluşacak intent belirlenir farklı main'e geçilir
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
