package org.disagn.cli.io;

import java.io.*;
import java.util.Arrays;
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
        if (line)
            page += " " + s + "\n";
        else
            page += " " +  s + "\n>> ";
        Output.print(page);
    }

    public static void printTable(String page, String tableName, String[] columnNames, String[][] rowEntries) {
        int[] columnWidths = new int[columnNames.length];
        Arrays.fill(columnWidths, 0);

        for (int i = 0; i < columnNames.length; i++) {
            columnWidths[i] = Math.max(columnWidths[i], columnNames[i].length());
        }

        for (String[] row : rowEntries) {
            for (int i = 0; i < row.length; i++) {
                columnWidths[i] = Math.max(columnWidths[i], row[i].length());
            }
        }

        StringBuilder sb = new StringBuilder();
        //the top border
        for (int width : columnWidths) {
            sb.append("+");
            sb.append("-".repeat(Math.max(0, width + 2)));
        }
        sb.append("+\n");
        // Add the column names
        appender(columnWidths, sb, columnNames);

        // Add the row entries
        for (String[] row : rowEntries) {
            appender(columnWidths, sb, row);
        }

        Output.pageMessage(page, tableName + "\n" + sb, true);

    }

    private static void appender(int[] columnWidths, StringBuilder sb, String[] row) {
        for (int i = 0; i < row.length; i++) {
            sb.append("| ");
            sb.append(row[i]);
            sb.append(" ".repeat(Math.max(0, columnWidths[i] - row[i].length() + 1)));
        }
        sb.append("|\n");
        for (int width : columnWidths) {
            sb.append("+");
            sb.append("-".repeat(Math.max(0, width + 2)));
        }
        sb.append("+\n");
    }

    public static void exception(Exception e) {
        println("Exception: " + e.getMessage());
    }
}
