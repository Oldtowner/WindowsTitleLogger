package ie.oldtowner;


import ie.oldtowner.controller.WindowTitleGetter;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CurrentApplicationLogger {
    private static String path;
    private static FileWriter fileWriter;
    private static WindowTitleGetter windowTitleGetter;
    private String fileCurrentDate;


    public static void main(String[] args) throws Exception {
        //Instantiate composite classes
        windowTitleGetter = new WindowTitleGetter();

        //Set up logger
        //TODO: ABSTRACT THE DAMNED LOGGER
        path = "C:\\JohnLogs\\";
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
        path = path +"\\"+dateFormat.format(date);
        File file = new File(path);

        file.mkdirs();
        //file.setPa;

        dateFormat = new SimpleDateFormat("dd");
        String fileCurrentDate =   dateFormat.format(date);
        file = new File(path +"\\"+fileCurrentDate+".txt");
        System.out.println("Writing to: "+file.getAbsolutePath());
        fileWriter = new FileWriter(file);

        DateFormat loggerTime = new SimpleDateFormat("HH:mm:ss");

        if(!file.exists()){
            System.out.println("Started at: "+loggerTime.format(date));
            fileWriter.write("Started at: "+loggerTime.format(date)+"\r\n");
        }


        java.lang.String loopCurrentDay = null; // To be updated in each iteration
        java.lang.String currentWindowTitle = windowTitleGetter.getCurrentWindowTitle();
        java.lang.String newWindowTitle = null;
        while(true){
        //for (int i = 0;i<100;i++)

            // Find out if day has passed - then update the file that we're writting to.
            loopCurrentDay=dateFormat.format(date);
            if(!fileCurrentDate.equals(loopCurrentDay)) {
                System.out.println("New day!");
                fileWriter.append("\r\n--------DAY END--------\r\n");
                fileWriter.flush();
                fileWriter.close();

                fileCurrentDate = loopCurrentDay.toString();
                file = new File(path +"\\"+fileCurrentDate+".txt");
                fileWriter = new FileWriter(file);
            }
            //Find out if window has changed.
            newWindowTitle = windowTitleGetter.getCurrentWindowTitle();
            //TODO: Find out if current window is blank - then maybe logged out. Catch if it's null too...
            //TODO: Time window progression
            if(!currentWindowTitle.equals(newWindowTitle)) {
                System.out.println(System.currentTimeMillis() + " Active window title: <" + newWindowTitle+">");
                fileWriter.append(loggerTime.format(date)+" "+newWindowTitle);
                fileWriter.append("\r\n");
                fileWriter.flush();
                currentWindowTitle = newWindowTitle;

                System.out.print("\0007");

            }
            TimeUnit.SECONDS.sleep(1);
        }
        /*PointerByReference pointer = new PointerByReference();
        GetWindowThreadProcessId(GetForegroundWindow(), pointer);
        Pointer process = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, pointer.getValue());
        GetModuleBaseNameW(process, null, buffer, MAX_TITLE_LENGTH);
        System.out.println("Active window process: " + Native.toString(buffer));*/
    }

    private void resetFileWriter(){


    }




}

