package ru.ifmo.ctddev.muratov.server.servlets;

import ru.ifmo.ctddev.muratov.server.StaticMembers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author amir.
 */
public class TrainerServlet extends HttpServlet {
    private static final String PHOTO_URL = "server/src/main/resources/photos/%s";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> photos = getFiles("server/src/main/resources/photos");
        request.getSession().setAttribute("photos", photos);
        getServletContext().getRequestDispatcher("/train.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double result = Double.valueOf(request.getParameter("result"));
        String photo = String.format(PHOTO_URL, request.getParameter("photoName"));
        System.out.println("result for photo " + photo + " is " + result);
        StaticMembers.ImageHandler.train(new FileInputStream(photo), result);
        Files.delete(Paths.get(photo));
        response.sendRedirect("/train");
    }

    private List<String> getFiles(String dir) {
        try {
            return Files.walk(Paths.get(dir))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .map(File::getName)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            return null;
        }
    }
}
