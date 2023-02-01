package org.AgneseDissan.cli.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Input{

    private static final BufferedReader in = new BufferedReader(
            new InputStreamReader( System.in)
    );
    private Input(){}

    public static String line() {
        String line = null;
        try {
            line = in.readLine();
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

    public static String getCmd(List<String> command){
        String cmd = getCmd();
        String ret;
        int num = 0;
        try{
            num = Integer.parseInt(cmd);

            if (num >= command.size()){
                return "INVALID_NUMBER";
            }

            ret = command.get(num);
        }catch (NumberFormatException e){
            ret = cmd;
        }

        return ret;
    }

    public static void close() throws IOException {
        in.close();
    }

    public static String getInfo(String page, String msg) {
        Output.pageMessage(page,"Insert " +  msg, false);
        return Input.line();
    }
}
