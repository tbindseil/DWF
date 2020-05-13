package com.tjb.dwf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.cert.Certificate;

import org.w3c.dom.Text;

import com.google.common.util.concurrent.ListenableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.ExecutableQuery;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketImplFactory;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.SocketFactory;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import static org.apache.http.conn.ssl.SSLSocketFactory.TLS;

public class CreatePictureActivity extends AppCompatActivity {
    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_picture);

        try {
            enableSslCert();
        } catch (Exception e) {
            Log.e("enableSslCerts", "exception");
            e.printStackTrace();
        }

        //Socket.setSocketImplFactory((SocketImplFactory) () -> {
        //
        //});
        //DefaultSSLSocketFactory
        //SocketFactory    //.setSocketImplFactory(SocketFactory.getDefault());

        try {
            // why not inject this dep in?
            mClient = new MobileServiceClient("https://52.250.108.250:5001/Index", this);
        } catch (Exception e) {
            Log.e("CreatePictureActivity", "exception creating mobile client");
            Log.e("CreatePictureActivity", e.getMessage());
            e.printStackTrace();
        }
    }

    // TODO send title and hopefully username!
    public void onClickSubmit(View v) {
        /*MobileServiceTable<ToDoItem> toDoTable = mClient.getTable(ToDoItem.class);
        try {
            List<ToDoItem> results = toDoTable
                    .where()
                    .field("test")
                    .eq("TJTAG")
                    .execute()
                    .get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            Toast.makeText(this,"exception", Toast.LENGTH_SHORT).show();
            Log.e("Exception", e.getMessage());
            e.printStackTrace();
        }
        Log.e("problem", "problem");
        Toast.makeText(this,"no exception?", Toast.LENGTH_SHORT).show();*/

        Thread wikiThread = new WikiThread(this.getApplicationContext());
        wikiThread.start();
    }

    /**
     * Method that bypasses every SSL certificate verification and accepts every
     * connection with any SSL protected device that ONOS has an interaction with.
     * Needs addressing for secutirty purposes.
     *
     * @throws NoSuchAlgorithmException if algorithm specified is not available
     * @throws KeyManagementException if unable to use the key
     */
    //FIXME redo for security purposes.
    protected static void enableSslCert() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext ctx = SSLContext.getInstance(TLS);
        ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> {
            //FIXME better way to do this.
            return true;
        });
    }

    //FIXME this accepts every connection
    private static class DefaultTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }


    class WikiThread extends Thread {
        private Context context;

        WikiThread(Context context) {
            this.context = context;
        }

        public void run() {
            try {

                // Load CAs from an InputStream
                /*Log.e("TrustStoreConfiguration", "could be from a resource or ByteArrayInputStream or ...)");
                // From https://www.washington.edu/itconnect/security/ca/load-der.crt
                // InputStream caInput = new BufferedInputStream(new FileInputStream("load-der.crt"));
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                InputStream caInput = context.getAssets().open("localhost.crt");
                Certificate ca;
                try {
                    ca = cf.generateCertificate(caInput);
                    Log.e("TrustStoreConfiguration", "ca=" + ((X509Certificate) ca).getSubjectDN());
                } finally {
                    caInput.close();
                }

                Log.e("TrustStoreConfiguration", "a KeyStore containing our trusted CAs");
                String keyStoreType = KeyStore.getDefaultType();
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(null, null);
                keyStore.setCertificateEntry("ca", ca);

                Log.e("TrustStoreConfiguration", "a TrustManager that trusts the CAs in our KeyStore");
                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                tmf.init(keyStore);

                Log.e("TrustStoreConfiguration", "an SSLContext that uses our TrustManager");
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null, tmf.getTrustManagers(), null);

                Log.e("TrustStoreConfiguration", "the URLConnection to use a SocketFactory from our SSLContext");
                URL url = new URL("https://simplelinuxvm-eyctacw3skh6o.westus2.cloudapp.azure.com:5001/Index");
                Log.e("count", "1");
                HttpsURLConnection urlConnection =
                        (HttpsURLConnection)url.openConnection();
                Log.e("count", "2");
                urlConnection.setSSLSocketFactory(context.getSocketFactory());
                Log.e("count", "3");
                InputStream in = urlConnection.getInputStream();*/



                Log.e("TrustStoreConfiguration", "could be from a resource or ByteArrayInputStream or ...)");
                // From https://www.washington.edu/itconnect/security/ca/load-der.crt
                // InputStream caInput = new BufferedInputStream(new FileInputStream("load-der.crt"));
                //CertificateFactory cf = CertificateFactory.getInstance("X.509");
                //InputStream caInput = context.getAssets().open("localhost.crt");
                //Certificate ca;
                //try {
                    //ca = cf.generateCertificate(caInput);
                    //Log.e("TrustStoreConfiguration", "ca=" + ((X509Certificate) ca).getSubjectDN());
                //} finally {
                    //caInput.close();
                //}

                Log.e("TrustStoreConfiguration", "a KeyStore containing our trusted CAs");
                //String keyStoreType = KeyStore.getDefaultType();
                //KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                //keyStore.load(null, null);
                //keyStore.setCertificateEntry("ca", ca);

                Log.e("TrustStoreConfiguration", "a TrustManager that trusts the CAs in our KeyStore");
                //String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                //TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
                //tmf.init(keyStore);

                Log.e("TrustStoreConfiguration", "an SSLContext that uses our TrustManager");
                //SSLContext context = SSLContext.getInstance("TLS");
                //context.init(null, tmf.getTrustManagers(), null);

                Log.e("TrustStoreConfiguration", "the URLConnection to use a SocketFactory from our SSLContext");
                URL url = new URL("https://Draw-n-stuff.com/Index");
                //URL url = new URL("https://example.org");
                Log.e("count", "1");
                HttpsURLConnection urlConnection =
                        (HttpsURLConnection)url.openConnection();
                Log.e("count", "2");
                // why are these switched?
                //urlConnection.setSSLSocketFactory(context.getSocketFactory());
                Log.e("count", "3");
                InputStream in = urlConnection.getInputStream();

                Log.e("count", "4");
                int size = in.available();
                Log.e("count", "5 and size is " + size);
                byte bytes[] = new byte[size];
                Log.e("count", "6");
                int numBytesRead = in.read(bytes, 0, size);
                Log.e("Read", "num bytes read is " + numBytesRead);
                Log.e("Read", new String(bytes));

                /*Log.e("count", "1");
                URL url = new URL("https://example.org");
                Log.e("count", "2");
                URLConnection urlConnection = url.openConnection();
                Log.e("count", "3");
                InputStream in = urlConnection.getInputStream();
                Log.e("count", "4");
                int size = in.available();
                Log.e("count", "5");
                byte bytes[] = new byte[size];
                Log.e("count", "6");
                int numBytesRead = in.read(bytes, 0, size);
                Log.e("Read", "num bytes read is " + numBytesRead);
                Log.e("Read", new String(bytes));*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
