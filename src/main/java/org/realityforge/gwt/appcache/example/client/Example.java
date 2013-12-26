package org.realityforge.gwt.appcache.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import javax.annotation.Nonnull;
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
    final ApplicationCache cache = ApplicationCache.get();
    if ( null == cache )
    {
      Window.alert( "ApplicationCache not available!" );
    }
    else
    {
      final VerticalPanel textPanel = new VerticalPanel();

      cache.addCachedHandler( new Handler()
      {
        @Override
        public void onCachedEvent( @Nonnull final CachedEvent event )
        {
          appendText( textPanel, "Cached", "blue" );
        }
      } );
      cache.addCheckingHandler( new CheckingEvent.Handler()
      {
        @Override
        public void onCheckingEvent( @Nonnull final CheckingEvent event )
        {
          appendText( textPanel, "Checking", "yellow" );
        }
      } );
      cache.addDownloadingHandler( new DownloadingEvent.Handler()
      {
        @Override
        public void onDownloadingEvent( @Nonnull final DownloadingEvent event )
        {
          appendText( textPanel, "Downloading", "orange" );
        }
      } );
      cache.addErrorHandler( new ErrorEvent.Handler()
      {
        @Override
        public void onErrorEvent( @Nonnull final ErrorEvent event )
        {
          appendText( textPanel, "Error", "red" );
        }
      } );
      cache.addNoUpdateHandler( new NoUpdateEvent.Handler()
      {
        @Override
        public void onNoUpdateEvent( @Nonnull final NoUpdateEvent event )
        {
          appendText( textPanel, "NoUpdate", "green" );
        }
      } );
      cache.addObsoleteHandler( new ObsoleteEvent.Handler()
      {
        @Override
        public void onObsoleteEvent( @Nonnull final ObsoleteEvent event )
        {
          appendText( textPanel, "Obsolete", "yellow" );
        }
      } );
      cache.addProgressHandler( new ProgressEvent.Handler()
      {
        @Override
        public void onProgressEvent( @Nonnull final ProgressEvent event )
        {
          appendText( textPanel, "Progress[" + event.getLoaded() + " of " + event.getTotal() + "]", "orange" );
        }
      } );
      cache.addUpdateReadyHandler( new UpdateReadyEvent.Handler()
      {
        @Override
        public void onUpdateReadyEvent( @Nonnull final UpdateReadyEvent event )
        {
          appendText( textPanel, "UpdateReady", "green" );
        }
      } );
      {
        final Button button = new Button( "Request cache update" );
        button.addClickHandler( new ClickHandler()
        {
          @Override
          public void onClick( final ClickEvent event )
          {
            cache.update();
          }
        } );
        panel.add( button );
      }

      {
        final Button button = new Button( "Request removal of cache" );
        button.addClickHandler( new ClickHandler()
        {
          @Override
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
          @Override
          public void onClick( final ClickEvent event )
          {
            cache.swapCache();
          }
        } );
        panel.add( button );
      }
      {
        final Button button = new Button( "Show image from cache" );
        button.addClickHandler( new ClickHandler()
        {
          @Override
          public void onClick( final ClickEvent event )
          {
            panel.add( new Image( GWT.getModuleBaseURL() + "bonsai tree.jpg" ) );
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
