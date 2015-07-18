package org.realityforge.gwt.appcache.example.server;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( urlPatterns = "/time" )
public class TimeServlet
  extends HttpServlet
{
  @Override
  protected void doGet( final HttpServletRequest request, final HttpServletResponse response )
    throws ServletException, IOException
  {
    response.setStatus( HttpServletResponse.SC_OK );
    response.getOutputStream().println(new Date().toString());
  }
}
