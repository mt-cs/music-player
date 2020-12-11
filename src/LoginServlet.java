import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Scanner;

/**
 * Login servlet for the music library
 */

public class LoginServlet extends BaseServlet {
    /**
     * The loginServlet's doPost method opens up musicDb and check the provided password against what is stored.
     * If this is a match, return the user a page with a list of all the songs in their DB.
     * If it is not, send them back to login.html, with a message indicating that they were not successful.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException for file not found
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // get login info
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Open database
        if (openDB(username, password)){
            Cookie cookie = new Cookie("name", username);
            cookie.setMaxAge(60*60*24);
            response.addCookie(cookie);
            response.sendRedirect("/homepage");
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(getContent("login.html"));
            out.println("<div style=\"color:SlateGray;padding-left:20px;\"><p><i>Login failed: Incorrect username or password, try again.</i></p></div>");
        }
    }

    /**
     * Open music.db users table
     * @param username String
     * @param password String
     * @return true if password input matched the password in the database
     */
    public boolean openDB(String username, String password) {
        String query = "select * from users where username = '"+ username + "'";
        String pwd = getString(query, "password");
        return pwd.equals(password);
    }
}