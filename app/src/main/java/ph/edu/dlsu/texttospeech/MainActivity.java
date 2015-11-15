package ph.edu.dlsu.texttospeech;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {


    private TextToSpeech speaker;
    private ArrayList<String> Phrases;
    private boolean ttsLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeTTS();
        loadPhrases();
        setupListView();
    }

    private void initializeTTS() {
        speaker = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                ttsLoaded = true;
            }
        });
    }


    private void loadPhrases(){
        Phrases = new ArrayList<String>();
        Scanner cursor = new Scanner(getResources().openRawResource(R.raw.worddatabase));
        while (cursor.hasNextLine()) {
            String line = cursor.nextLine();
            Phrases.add(line);
        }
    }

    private void setupListView(){
        ListView words = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, R.layout.listitem, Phrases);
       words.setAdapter(adapter);


        words.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                handleClick(index);
            }
        });
    }

    private void handleClick(int index) {
        String text = Phrases.get(index);
        if (ttsLoaded) {
            speaker.setSpeechRate(0.6f);
            speaker.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}