package com.bardavid.buscacepapp;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class HttpService extends AsyncTask<Void, Void, CEP> {
    private final String cepInserido;
    public HttpService(String cep) {this.cepInserido = cep;}

    @Override
    protected CEP doInBackground (Void... voids) {
        StringBuilder resposta = new StringBuilder();

            try {

                URL url = new URL("https://viacep.com.br/ws/"+this.cepInserido+"/json/");

                HttpURLConnection connection = (HttpURLConnection)
                        url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.connect();

                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()){
                    resposta.append(scanner.next());
                }
                scanner.close();
                connection.disconnect();
            } catch (MalformedURLException e ){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return new Gson().fromJson(resposta.toString(), CEP.class);
    }
}
