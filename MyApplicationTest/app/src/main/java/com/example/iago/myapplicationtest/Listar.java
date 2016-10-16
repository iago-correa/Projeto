import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.example.iago.myapplicationtest.MySQLiteHelper;
import com.example.iago.myapplicationtest.Pokemon;
import com.example.iago.myapplicationtest.R;
import java.util.List;


public class Listar extends Activity {
    private ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all);

        MySQLiteHelper db = new MySQLiteHelper(getBaseContext());
        List<Pokemon> list = db.getAllPokemons();



        lista = (ListView)findViewById(R.id.listView);
       // lista.setAdapter(adaptador);
    }
}