package com.example.medicalcall;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class SendMessage extends AsyncTask<String, Void, Void> {

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This will normally run on a background thread. But to better
     * support testing frameworks, it is recommended that this also tolerates
     * direct execution on the foreground thread, as part of the {@link #execute} call.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param strings The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Void doInBackground(String... strings) {
        String message = strings[0];

        Socket s;
        PrintWriter pw;

        try{

            s = new Socket("192.168.1.46",7800);

            pw = new PrintWriter(s.getOutputStream());
            pw.write(message);
            pw.flush();

            s.close();
            pw.close();


        }catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
