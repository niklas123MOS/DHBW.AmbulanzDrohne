package EmergencyCenter;

import Configuration.Configuration;
import Configuration.Algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class PathPlanner {
    public int[][] executeFindPath(int rowStart, int colStart, int rowDest, int colDest, char[][] map) {
        System.out.println("Calculate Route...");


        if (isComponentSigned()) {

            Object instance;
            try {
                URL[] urls = {new File(Configuration.instance.getComponentPath()).toURI().toURL()};
                URLClassLoader urlClassLoader = new URLClassLoader(urls, PathPlanner.class.getClassLoader());
                Class clazz = Class.forName(Configuration.instance.pathAlgorithm.toString(), true, urlClassLoader);

                instance = clazz.getMethod("getInstance").invoke(null);
                Object port = clazz.getDeclaredField("port").get(instance);

                Method findPath = port.getClass().getMethod("findPath", int.class, int.class, int.class, int.class, char[][].class);
                int[][] path = (int[][]) findPath.invoke(port, rowStart, colStart, rowDest, colDest, map);
                return (path);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else return null;
    }


    private boolean isComponentSigned() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("C:\\Program Files\\Java\\jdk-16.0.1\\bin\\jarsigner", "-verify", Configuration.instance.getComponentPath());
            Process process = processBuilder.start();
            process.waitFor();

            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            boolean isComponentAccepted = false;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("verified")) {
                    isComponentAccepted = true;
                }
            }

            if (isComponentAccepted) {
                System.out.println("component accepted");
                return true;
            } else {
                System.out.println("component rejected");
                throw new UnsignedComponentException("Component is not signed. Only signed Components are allowed");

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return false;
    }
}
