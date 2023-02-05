package org.dissan.cli.io;

import org.dissan.exceptions.InvalidDateException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class Output{

    private static final BufferedWriter WRITER = new BufferedWriter(
            new OutputStreamWriter(System.out)
    );

    private Output() {}

    public static void print(String msg){
        try {
            WRITER.write(msg, 0, msg.length());
            WRITER.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void println(String msg){
        print(msg + "\n");
    }

    public static void printList(String page, List<String> commandList){
        Output.pageMessage(page, "COMMAND LIST", true);
        int i = 0;
        for (String command:
                commandList) {
            Output.println("[" + (i++) + "] " + command);
        }
    }

    public static void pageMessage(String page, String s, boolean line){
        page = "[" + page;
        if (line)
            page += ("]: " + s + "\n");
        else
            page += ("] " + s + "\n>> ");
        Output.print(page);
    }

    public static void printRow(String page, List<String> values, List<String> columnName){
        page = "[" + page;
        int max = 0;

        for (String column: values
             ) {
            if (max < column.length()){
                max = column.length();
            }
        }

        for (String column: columnName
        ) {
            if (max < column.length()){
                max = column.length();
            }
        }

        StringBuilder lines = new StringBuilder("___");

        lines.append("_".repeat(Math.max(0, (max) * (values.size()))));

        lines.append("___");
        String line = lines.toString();


        StringBuilder builder = new StringBuilder();


        builder.append(line).append("\n| ");
        for (String column: values
        ) {
            builder.append(column);
            builder.append(" | ");
        }
        builder.append(" |\n").append(line);


        String output = builder.toString();

        Output.print(output);

    }

    public static void exception(Exception e) {
        println("Error: " + e.getMessage());
    }
}
