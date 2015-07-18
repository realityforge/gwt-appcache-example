package org.realityforge.gwt.appcache.example.server;

//import javax.servlet.annotation.WebServlet;
import org.realityforge.gwt.appcache.server.AbstractManifestServlet;
import org.realityforge.gwt.appcache.server.propertyprovider.UserAgentPropertyProvider;

//@WebServlet(urlPatterns = { "/example.appcache" })
public class ManifestServlet
  extends AbstractManifestServlet
{
  public ManifestServlet()
  {
    addPropertyProvider( new UserAgentPropertyProvider() );
  }
}
