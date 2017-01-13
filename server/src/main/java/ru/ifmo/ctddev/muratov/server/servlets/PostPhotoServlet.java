package ru.ifmo.ctddev.muratov.server.servlets;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import ru.ifmo.ctddev.muratov.server.StaticMembers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author amir.
 */
public class PostPhotoServlet extends HttpServlet {
    private int photoId = 0;
    private static final String PHOTO_URL = StaticMembers.SERVER_FILES_PATH + "/photos/photo%d.jpeg";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double result = 0;
        String photoUrl = "";
        try {
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
                    //String fieldName = item.getFieldName();
                    //String fieldValue = item.getString();
                    // todo get some more parameters to save history or smth else
                } else {
                    // Process form file field (input type="file").
                    //String fieldName = item.getFieldName();
                    //String fileName = FilenameUtils.getName(item.getName());
                    InputStream fileContent = item.getInputStream();
                    String photo = String.format(PHOTO_URL, photoId++);
                    IOUtils.copy(fileContent, new FileOutputStream(photo));
                    result = StaticMembers.ImageHandler.getResult(new FileInputStream(photo));
                    photoUrl = photo;
                }
            }

        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(String.format("Your result is %.5f", result));
        response.getWriter().println("Diagnosis: " + interpret(result));
        if (!photoUrl.equals(""))
            response.getWriter().println(photoUrl.substring(photoUrl.indexOf("p")));
        else
            System.err.println("nope");
    }

    private static String interpret(double fissureResult) {
        if (fissureResult < 0) {
            return "low intensity or high contrast image";
        }
        if (fissureResult < 1.5) {
            return "absolute norm";
        }
        if (fissureResult < 3) {
            return "within the normal range";
        }
        if (fissureResult < 4.5) {
            return "small lack of vitamin";
        }
        if (fissureResult < 6) {
            return "lack of vitamin";
        }
        if (fissureResult < 8) {
            return "significant deficient of vitamin";
        }
        return "low intensity or high contrast image";
    }

}
