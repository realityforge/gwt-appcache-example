package org.realityforge.gwt.appcache.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.realityforge.gwt.appcache.client.ApplicationCache;
import org.realityforge.gwt.appcache.client.event.CachedEvent;
import org.realityforge.gwt.appcache.client.event.CachedEvent.Handler;
import org.realityforge.gwt.appcache.client.event.CheckingEvent;
import org.realityforge.gwt.appcache.client.event.DownloadingEvent;
import org.realityforge.gwt.appcache.client.event.ErrorEvent;
import org.realityforge.gwt.appcache.client.event.NoUpdateEvent;
import org.realityforge.gwt.appcache.client.event.ObsoleteEvent;
import org.realityforge.gwt.appcache.client.event.ProgressEvent;
import org.realityforge.gwt.appcache.client.event.UpdateReadyEvent;

public final class Example
  implements EntryPoint
{
  public void onModuleLoad()
  {
    final VerticalPanel panel = new VerticalPanel();
    final ApplicationCache cache = ApplicationCache.getApplicationCacheIfSupported();
    if ( null == cache )
    {
      Window.alert( "ApplicationCache not available!" );
    }
    else
    {
      final VerticalPanel textPanel = new VerticalPanel();

      cache.addCachedHandler( new Handler()
      {
        
        public void onCachedEvent(  final CachedEvent event )
        {
          appendText( textPanel, "Cached", "blue" );
        }
      } );
      cache.addCheckingHandler( new CheckingEvent.Handler()
      {
        
        public void onCheckingEvent(  final CheckingEvent event )
        {
          appendText( textPanel, "Checking", "yellow" );
        }
      } );
      cache.addDownloadingHandler( new DownloadingEvent.Handler()
      {
        
        public void onDownloadingEvent(  final DownloadingEvent event )
        {
          appendText( textPanel, "Downloading", "orange" );
        }
      } );
      cache.addErrorHandler( new ErrorEvent.Handler()
      {
        
        public void onErrorEvent(  final ErrorEvent event )
        {
          appendText( textPanel, "Error", "red" );
        }
      } );
      cache.addNoUpdateHandler( new NoUpdateEvent.Handler()
      {
        
        public void onNoUpdateEvent(  final NoUpdateEvent event )
        {
          appendText( textPanel, "NoUpdate", "green" );
        }
      } );
      cache.addObsoleteHandler( new ObsoleteEvent.Handler()
      {
        
        public void onObsoleteEvent(  final ObsoleteEvent event )
        {
          appendText( textPanel, "Obsolete", "yellow" );
        }
      } );
      cache.addProgressHandler( new ProgressEvent.Handler()
      {
        
        public void onProgressEvent(  final ProgressEvent event )
        {
          if ( event.isLengthComputable() )
          {
            appendText( textPanel, "Progress[" + event.getLoaded() + " of " + event.getTotal() + "]", "orange" );
          }
          else
          {
            appendText( textPanel, "Progress", "orange" );
          }
        }
      } );
      cache.addUpdateReadyHandler( new UpdateReadyEvent.Handler()
      {
        
        public void onUpdateReadyEvent(  final UpdateReadyEvent event )
        {
          appendText( textPanel, "UpdateReady", "green" );
        }
      } );
      {
        final Button button = new Button( "Request cache update" );
        button.addClickHandler( new ClickHandler()
        {
          
          public void onClick( final ClickEvent event )
          {
            cache.requestUpdate();
          }
        } );
        panel.add( button );
      }

      {
        final Button button = new Button( "Request removal of cache" );
        button.addClickHandler( new ClickHandler()
        {
          
          public void onClick( final ClickEvent event )
          {
            cache.removeCache();
          }
        } );
        panel.add( button );
      }

      {
        final Button button = new Button( "Request cache swap" );
        button.addClickHandler( new ClickHandler()
        {
          
          public void onClick( final ClickEvent event )
          {
            cache.swapCache();
          }
        } );
        panel.add( button );
      }
      {
        final Button button = new Button( "Abort cache download" );
        button.addClickHandler( new ClickHandler()
        {
          
          public void onClick( final ClickEvent event )
          {
            cache.abort();
          }
        } );
        panel.add( button );
      }

      {
        final Button button = new Button( "Show image from cache" );
        button.addClickHandler( new ClickHandler()
        {
          
          public void onClick( final ClickEvent event )
          {
            panel.add( new Image( GWT.getModuleBaseURL() + "bonsai tree.jpg" ) );
          }
        } );
        panel.add( button );
      }

      {
        final Button button = new Button( "Show time with fallback when offline" );
        button.addClickHandler( new ClickHandler()
        {
          
          public void onClick( final ClickEvent event )
          {
            final RequestBuilder requestBuilder =
              new RequestBuilder( RequestBuilder.GET, GWT.getModuleBaseURL() + "../time" );
            try
            {
              requestBuilder.sendRequest( "", new RequestCallback()
              {
                
                public void onResponseReceived( final Request request, final Response response )
                {
                  final TextArea textArea = new TextArea();
                  textArea.setText( "Response Received: " + response.getText() );
                  panel.add( textArea );
                }

                
                public void onError( final Request request, final Throwable exception )
                {
                  final TextArea textArea = new TextArea();
                  textArea.setText( "Error requesting resource: " + exception );
                  panel.add( textArea );
                }
              } );
            }
            catch ( final RequestException exception )
            {
              final TextArea textArea = new TextArea();
              textArea.setText( "Error requesting resource: " + exception );
              panel.add( textArea );
            }
          }
        } );
        panel.add( button );
      }

      panel.add( textPanel );
      RootPanel.get().add( panel );
    }
  }

  private void appendText( final VerticalPanel panel, final String eventName, final String color )
  {
    panel.add( new InlineHTML( "<span style=\"color:" + color + "\">" + eventName + "</span><br />" ) );
  }
}
