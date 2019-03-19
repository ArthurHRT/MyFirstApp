package br.pucminas.computacao.lddm.myfirstapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
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

    /*
    private void loginButton() {
        loginBut = (Button) findViewById(R.id.loginButton);
        loginBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginScreen = new Intent(getApplicationContext(),Login.class);
                startActivity(loginScreen);
            }
        });
    }
    */

    public void SalvarContato(View view){
        EditText nome = findViewById(R.id.editText2);
        EditText sobrenome = findViewById(R.id.editText3);
        EditText numero = findViewById(R.id.editText4);
        EditText email = findViewById(R.id.editText5);

        String nm = nome.getText().toString();
        String sb = sobrenome.getText().toString();
        String nmr = numero.getText().toString();
        String em = email.getText().toString();

        p = new Pessoa(nm, sb, nmr, em);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Deseja realmente salvar contato?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent loginScreen = new Intent(getApplicationContext(),Login.class);
                        startActivity(loginScreen);

                        sendEmailMessage(p);
                        sendWhatsappMessage(p);
                        addContact(p);

                    }
                })
                .setNegativeButton("Não", null);

        AlertDialog alert = builder.create();
        alert.show();

        //sendWhatsappMessage(p);
        //adicionandoContato(p);

    }

    protected void addContact(Pessoa p){

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

    private void sendEmailMessage(Pessoa p) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        String[] emails = p.getEmail().split(",");

        try {
            intent.putExtra(Intent.EXTRA_EMAIL, emails);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Confirmação de Cadastro");
            intent.putExtra(Intent.EXTRA_TEXT, "Seu cadastro foi realizado com sucesso!");

            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "Escolha um cliente de email"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}


