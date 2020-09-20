package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ControllerServlet extends HttpServlet {

    private boolean tryToParse(String s){
        try {
            Double.parseDouble(s);
            return true;
        }catch (NumberFormatException | NullPointerException ex ){
            return false;
        }
    }

    private boolean tryToParseArray(String[] params){
        try {
            for (String param : params) {
                Double.parseDouble(param);
            }
            return true;
        }catch (NumberFormatException | NullPointerException ex ) {
            return false;
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        try {

            if (tryToParse(req.getParameter("X"))&&
                tryToParse(req.getParameter("Y"))&&
                tryToParseArray(req.getParameterValues("R"))
            ){
                getServletContext().getRequestDispatcher("/areaCheckServlet").forward(req, resp);
            } else {
                getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            }

        }catch (Exception e){
            PrintWriter writer = resp.getWriter();
            writer.write("Server wanna cry: "+e.toString());
            writer.close();
        }
    }
}
