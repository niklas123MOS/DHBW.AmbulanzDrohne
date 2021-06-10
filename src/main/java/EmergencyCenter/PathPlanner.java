package EmergencyCenter;

import Configuration.Configuration;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class PathPlanner {
    public int[][] executeFindPath(int rowStart, int colStart, int rowDest, int colDest, char[][] map) {
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
    }
}
