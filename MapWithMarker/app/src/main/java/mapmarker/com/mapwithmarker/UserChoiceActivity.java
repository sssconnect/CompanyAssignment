package mapmarker.com.mapwithmarker;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserChoiceActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSearch;
    private Button btnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearch = findViewById(R.id.btn_search);
        btnView = findViewById(R.id.btn_view);

        btnSearch.setOnClickListener(this);
        btnView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_search:
                Intent mapSearch  = new Intent(this,MapsActivity.class);
                mapSearch.putExtra(Config.IDENTITYKEY, Config.SEARCHVALUE);
                startActivity(mapSearch);
                break;
            case R.id.btn_view:
                Intent  mapView = new Intent(this,MapsActivity.class);
                mapView.putExtra(Config.IDENTITYKEY, Config.VIEWVALUE);
                startActivity(mapView);
                break;
        }
    }
}
