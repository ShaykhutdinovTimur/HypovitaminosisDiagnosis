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
import java.io.*;
import java.util.List;

/**
 * @author amir.
 */
public class PostPhotoServlet extends HttpServlet {
    private int photoId = 0;
    private static final String PHOTO_URL = "server/src/main/resources/photos/photo%d.jpeg";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double result = 0;
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
                }
            }
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(String.format("Result is %.5f", result));
    }



}
