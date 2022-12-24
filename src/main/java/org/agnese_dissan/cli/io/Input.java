package org.agnese_dissan.cli.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Input{

    private static final BufferedReader INPUT = new BufferedReader(
            new InputStreamReader( System.in)
    );
    private Input(){}

    public static String line() {
        String line = null;
        try {
            line = INPUT.readLine();
        }catch (IOException e){
            e.printStackTrace();
        }
        return line;
    }

    public static String getCmd(){
        String ln = line();
        if(ln.contains("_")){

            String[] temp = ln.split("_");
            temp[0] = temp[0].toUpperCase();
            temp[1] = temp[1].toUpperCase();
            ln = temp[0] +"_"+temp[1];

        }else {

            ln = ln.toUpperCase();

        }
        return ln;
    }

    public static void close() throws IOException {
        INPUT.close();
    }
}
