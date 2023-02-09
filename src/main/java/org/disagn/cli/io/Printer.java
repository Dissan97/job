package org.disagn.cli.io;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Arrays;



public class Printer {

    private final static Logger LOGGER = LoggerFactory.getLogger("PRINTER");
    private final static String LINE = "\n";
    private Printer() {}


    public static void print(String msg){
            LOGGER.info(msg);
    }


    public static void printList(String page, List<String> commandList){
        Printer.pageMessage(page, "COMMAND LIST", true);
        StringBuilder builder = new StringBuilder(LINE + LINE);
        int i = 0;
        for (String command:
                commandList) {
            builder.append("[").append(i++).append("] ").append(command).append("\n");
        }
        Printer.print(builder.toString());
    }

    public static void pageMessage(String page, String s, boolean line){
        if (line)
            page += " " + s + LINE;
        else
            page += " " +  s + LINE + "<<insert command>>";
        Printer.print(page);
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
        sb.append("+" + LINE);
        // Add the column names
        appender(columnWidths, sb, columnNames);

        // Add the row entries
        for (String[] row : rowEntries) {
            appender(columnWidths, sb, row);
        }

        Printer.pageMessage(page, tableName + LINE + sb, true);

    }

    private static void appender(int[] columnWidths, StringBuilder sb, String[] row) {
        for (int i = 0; i < row.length; i++) {
            sb.append("| ");
            sb.append(row[i]);
            sb.append(" ".repeat(Math.max(0, columnWidths[i] - row[i].length() + 1)));
        }
        sb.append("|" + LINE);
        for (int width : columnWidths) {
            sb.append("+");
            sb.append("-".repeat(Math.max(0, width + 2)));
        }
        sb.append("+" + LINE);
    }

    public static void exception(Exception e) {
        LOGGER.error("Exception: ", e);
    }
}
