package br.pucminas.computacao.lddm.myfirstapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    Button salvarcontato;
    Pessoa p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //salvarcontato = (Button)findViewById(R.id.button);



        //salvarcontato.setOnClickListener(new View.OnClickListener());
    }

    public void SalvarContato(View view){
        EditText nome = (EditText)findViewById(R.id.editText2);
        EditText sobrenome = (EditText)findViewById(R.id.editText3);
        EditText numero = (EditText)findViewById(R.id.editText4);
        EditText email = (EditText)findViewById(R.id.editText5);

        String nm = nome.getText().toString();
        String sb = sobrenome.getText().toString();
        String nmr = numero.getText().toString();
        String em = email.getText().toString();

        p = new Pessoa(nm, sb, nmr, em);

        adicionandoContato(p);
        sendWhatsappMessage(p);

    }

    protected void adicionandoContato(Pessoa p){

        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, p.getNome() + " " + p.getSobrenome());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, p.getCelular());
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, p.getEmail());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    private void sendWhatsappMessage(Pessoa p){
        PackageManager packageManager = this.getPackageManager();
        Intent itn = new Intent(Intent.ACTION_VIEW);
        String phone = "55" + p.getCelular();

        try {
            String url = "https://api.whatsapp.com/send?phone="+ phone +"&text=" + URLEncoder.encode("Cadastro realizado com sucesso!", "UTF-8");
            itn.setPackage("com.whatsapp");
            itn.setData(Uri.parse(url));

            if (itn.resolveActivity(packageManager) != null) {
                this.startActivity(itn);
            } else {
                Toast.makeText(this.getApplicationContext(),"Mensagem não enviada.",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    protected void whatsappMessage(Pessoa p) {

        PackageManager pm = getPackageManager();
           try {

               Intent waIntent = new Intent(Intent.ACTION_SEND);
               waIntent.setType("text/plain");
               String text = "Cadastro realizado com sucesso!";

               PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
               //Check if package exists or not. If not then code
               //in catch block will be called
               waIntent.setPackage("com.whatsapp");

               waIntent.putExtra(Intent.EXTRA_TEXT, text);
               startActivity(Intent.createChooser(waIntent, "Mensagem Enviada."));

           } catch (PackageManager.NameNotFoundException e) {
               Toast.makeText(this, "WhatsApp não Instalado!", Toast.LENGTH_SHORT)
                       .show();
           }

    }
    */

}


