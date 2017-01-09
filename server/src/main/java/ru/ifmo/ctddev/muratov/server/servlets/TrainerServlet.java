package ru.ifmo.ctddev.muratov.server.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author amir.
 */
public class TrainerServlet extends HttpServlet {
    private static final String PHOTO_URL = "server/src/main/resources/photos/photo%d.jpeg";
    private static final String FORM =
            "<img src=\"%s\" alt=\"tonguePhoto\" style=\"width:304px;height:228px;\">\n" +
                    "<form id=\"train\" action=\"/train\" method=\"POST\">\n" +
                    "<input id=\"result\" name=\"result\" type=\"text\" value=\"0\"/>" +
                    "<input type=\"hidden\" name=\"photoId\" value=%s>\n" +
                    "        <input type=\"submit\" value=\"submit result\">\n" +
                    "    </form>\n<hr>\n";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //todo
        String html = readFile("server/src/main/resources/train.html");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(html);
        String photoDir = "server/src/main/resources/photos";
        try (Stream<Path> paths = Files.walk(Paths.get(photoDir))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath))
                    out.println(String.format(FORM, "/photos/" + filePath.getFileName(),
                            filePath.getFileName().toString().replaceAll("\\D+", "")));
            });
        }
        out.print("</body>\n</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double result = Double.valueOf(request.getParameter("result"));
        int photoId = Integer.valueOf(request.getParameter("photoId"));
        String photo = String.format(PHOTO_URL, photoId);
        System.out.println("result for photo number " + photoId + " is " + result);
        // todo delete photo and retrain model
        response.sendRedirect("/train");
    }

    private static String readFile(String path) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }

}
