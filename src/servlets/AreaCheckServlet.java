package servlets;

import modules.Result;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static java.lang.Math.pow;

public class AreaCheckServlet extends HttpServlet {
    private final List<Double> R_PATTERN= Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0);
    public String prepareAnswer(Result result) {
        return  "<!DOCTYPE html>\n"+
                "<html>\n"+
                "<head>\n"+
                "        <meta charset=\"utf-8\">\n"+
                "       <title>test</title>\n"+
                "       <link rel=\"stylesheet\" type=\"text/css\" href=\"css/returned.css\">\n"+
                "   </head>\n"+
                "   <body>\n"+
                "       <div id=\"body_div\">\n"+
                "           <div id = \"head_div\" class=\"head\">\n"+
                "               <span>Антонов Максим Александрович, P3214</span>\n"+
                "               <span>Вариант 2524</span>\n"+
                "           </div>\n"+
                "           <div id=\"table_div\">\n"+
                "           <table class=\"result\">\n"+
                "               <col width=\"50\">\n"+
                "               <col width=\"50\">\n"+
                "               <col width=\"50\">\n"+
                "               <col width=\"75\">\n"+
                "               <thead>\n"+
                "                   <tr>\n"+
                "                      <td>X</td>\n"+
                "                      <td>Y</td>\n"+
                "                      <td>R</td>\n"+
                "                      <td>Result</td>\n"+
                "                   </tr>\n"+
                "               </thead>\n"+
                "               <tbody id=\"#table_body\">\n"+
                "                   <tr>\n"+
                "                   <td><div class='cell'>"+result.getX()+"</div></td>\n"+
                "                   <td><div class='cell'>"+result.getY()+"</div></td>\n"+
                "                   <td><div class='cell'>"+result.getR()+"</div></td>\n"+
                "                   <td><div class='cell'>"+result.isResult()+"</div></td>\n"+
                "                   </tr>\n"+
                "               </tbody>\n"+
                "           </table>\n"+
                "           </div>\n"+
                "           <form> <button id=\"submit_button\" type=\"submit\">Go back</button></form>\n"+
                "       </div>\n"+
                "   </body>\n"+
                "</html>";


    }

    public String prepareError(String type) {
        return  "<!DOCTYPE html>\n"+
                "<html>\n"+
                "<head>\n"+
                "        <meta charset=\"utf-8\">\n"+
                "       <title>test</title>\n"+
                "       <link rel=\"stylesheet\" type=\"text/css\" href=\"css/returned.css\">\n"+
                "   </head>\n"+
                "   <body>\n"+
                "       <div id=\"body_div\">\n"+
                "           <div id=\"head_div\" class=\"head\">\n"+
                "              <span id=\"error\"> INDEX OUT OF RANGE: " + type +"</span>"+
                "           </div>\n"+
                "           <form> <button id=\"submit_button\" type=\"submit\">Go back</button></form>\n"+
                "       </div>\n"+
                "   </body>\n"+
                "</html>";


    }

    private boolean checkRange(Double x,Double y,Double r){

        return (x >= -3 && x <= 5) &&
                (y >= -5 && y <= 3) &&
                (R_PATTERN.contains(r));

    }

    private boolean checkLength(String x_str,String y_str,Double x,Double y){

        if (y!=0.0) {
            int x_length;
            int y_length;

            if (x!=0.0){
                x_length = BigDecimal.valueOf(x).toPlainString().replaceAll("0+$", "").length();
            } else {
                x_length = BigDecimal.valueOf(x).toPlainString().length();
            }
            if (y!=0.0){
                y_length = BigDecimal.valueOf(y).toPlainString().replaceAll("0+$", "").length();
            } else {
                y_length = BigDecimal.valueOf(y).toPlainString().length();
            }


            if (y_str.contains(".")) {
                if (x_str.contains(".")){
                    return (x_str.length() ==x_length) &&
                           (y_str.length() == y_length );
                }else return (x_str.length() == (x_length - 2)) &&
                             (y_str.length() ==  y_length);
            }else {
                if (x_str.contains(".")){
                    return (x_str.length() == x_length ) &&
                           (y_str.length() == (y_length - 2));
                }else return (x_str.length() == (x_length - 2)) &&
                             (y_str.length() == (y_length - 2));
            }

        }else return true;

    }

    private boolean checkR(String[] r_string){
        for (String r_val:r_string){
            double r_double = Double.parseDouble(r_val);
            if(Double.toString(r_double).length() < r_val.length()){
                return false;
            }
        }
        return true;
    }



    private double[] getArrayFromParams(String[] params){

            return   Arrays.stream(params)
                    .mapToDouble(Double::parseDouble)
                    .toArray();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();

        Double x = Double.parseDouble(req.getParameter("X"));
        Double y = Double.parseDouble(req.getParameter("Y"));
        double[] r_vals = getArrayFromParams(req.getParameterValues("R"));
        Double r = Arrays
                    .stream(r_vals)
                    .max()
                    .getAsDouble();


        System.out.println(checkLength(req.getParameter("X"),req.getParameter("Y"),x,y));

        if (checkRange(x,y,r) &&
        checkLength(req.getParameter("X"),req.getParameter("Y"),x,y) &&
        checkR(req.getParameterValues("R"))) {
            @SuppressWarnings("unchecked")
            ArrayList<Result> results = (ArrayList<Result>) session.getAttribute("results"); //0_o warnings.......
            if (results == null) {
                results = new ArrayList<>();
            }

            Result res = new Result();
            res.setR(r);
            res.setX(x);
            res.setY(y);
            res.checkTheArea();

            results.add(res);
            session.setAttribute("results", results);
            String answer = prepareAnswer(res);
            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter response = resp.getWriter();
            response.write(answer);
            response.close();
        }else {
            String answer = prepareError("You have changed the request");
            resp.setContentType("text/html; charset=UTF-8");
            PrintWriter response = resp.getWriter();
            response.write(answer);
            response.close();
        }
    }
}