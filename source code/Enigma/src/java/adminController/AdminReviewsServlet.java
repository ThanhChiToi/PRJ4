/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package adminController;

import entity.Feedbacks;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.FeedbacksFacadeLocal;

/**
 *
 * @author longv
 */
@WebServlet(name = "AdminReviewsServlet", urlPatterns = {"/Admin/Reviews/*"})
public class AdminReviewsServlet extends HttpServlet {

    @EJB
    private FeedbacksFacadeLocal feedbacksFacade;
    RequestDispatcher dispatcher;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String action = request.getPathInfo();

        switch (action) {
            case "/List":
                getViewList(request, response);
                break;
            case "/Delete":
                delete(request, response);
                break;
            default:
                out.print("ok");
                break;
        }
    }
    
    private void getViewList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Feedbacks> list = feedbacksFacade.findAll();
        request.setAttribute("listReview", list);
        dispatcher = request.getRequestDispatcher("/admin/views/review/list.jsp");
        dispatcher.forward(request, response);
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));
        Feedbacks feed = new Feedbacks(id);
        feedbacksFacade.remove(feed);
        if(request.getParameter("role") == "admin"){
            response.sendRedirect("List"); 
        }
        else {
            response.sendRedirect(request.getContextPath() + "/Customer/Review"); 
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
