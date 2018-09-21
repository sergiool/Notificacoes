package site.ufsj.notificacoes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        TextView msg = findViewById(R.id.textView);
        String mensagem = getIntent().getStringExtra("mensagem");
        msg.setText(mensagem);
    }
}
