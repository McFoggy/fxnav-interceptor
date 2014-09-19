fxnav-interceptor
=================

sample project to investigate interception of navigation inside a JavaFX webview.

# Description

The goal is to be able to intercept navigation to elements not interpreted by the JavaFX 8 webview/webengine and to allow an external control of them.
For example, it allows to intercept click on PDFs/zip/... files and to allow the container application to do whatever is expected with them: download, vizualize, ...

# Build & usage

    mvn clean install
    
This will build the project and will create an executable jar containing all the required dependencies.  

    java -jar target\navigation-interceptor-shaded.jar
    
> _Options_
> options can be passed to the JavaFX application
> - host: the hostname or IP to use (default is 127.0.0.1)
> - port: the http port to listen to (default 8080)
> 
> Example: 
>    java -jar target\navigation-interceptor-shaded.jar --host=192.168.0.1 --port=80

# What is demonstrated?

The goal was to demo in the current state of JavaFX 8, how difficult it is (but not impossible) to intercept correctly navigation/download for items not handled by the webview/webengine component of JavaFX.

The app shows 2 tabs:

- one called "By Location" 
- another called "By DOM Monitoring"

Both will by default show a simple html/javascript application running on an embeded Jetty server.

## By Location tab

Inside this tab, we try to intercept navigation/download by monitoring changes on the webengine.location property.
This is simple, but fails when you try to click several times on the same item.  

## By DOM Monitoring

Inside this tab, the web application loaded inside the webview is updated by the java side.
After a successful loading, a javascript library able to monitor DOM changes is injected inside the webapp.
Then Java bridges are introduced to this library so that changes detected on the webapp side will be propagated to the Java world.
This allows the Java world to register as 'ClickListener' on html DOM objects and thus intercept them before they can reach the web engine.
With this stuff every interception is possible.

__Key files to look at__
- javafx-mutation.js
- fr.brouillard.fx.browser.tabs.ByMutationObserverPresenter (especially method #enhanceAndMonitorPage)
