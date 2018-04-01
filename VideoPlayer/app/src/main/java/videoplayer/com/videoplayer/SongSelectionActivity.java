package videoplayer.com.videoplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SongSelectionActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup radioGroup;
    private RadioButton radioSongButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int selectedId=radioGroup.getCheckedRadioButtonId();
        radioSongButton=(RadioButton)findViewById(selectedId);
        switch (selectedId){
            case R.id.radioButton1:
                Intent intent1 = new Intent(this,PlayerActivity.class);
                intent1.putExtra(Config.LINK,radioSongButton.getText());
                intent1.putExtra(Config.ISRTMP,true);
                startActivity(intent1);
                break;
            case R.id.radioButton2:
                Intent intent2 = new Intent(this,PlayerActivity.class);
                intent2.putExtra(Config.LINK,radioSongButton.getText());
                intent2.putExtra(Config.ISRTMP,false);
                startActivity(intent2);
                break;
            case R.id.radioButton3:
                Intent intent3 = new Intent(this,PlayerActivity.class);
                intent3.putExtra(Config.LINK,radioSongButton.getText());
                intent3.putExtra(Config.ISRTMP,false);
                startActivity(intent3);
                break;
        }
    }
}
