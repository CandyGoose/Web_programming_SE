package servlets.listeners;

import data.ResultContainer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * The DataLoader class is a servlet context listener responsible for initializing the ResultContainer and
 * setting it as an attribute in the servlet context when the web application starts.
 */
public class DataLoader implements ServletContextListener {

    /**
     * Initializes the ResultContainer and sets it as an attribute in the servlet context when the web application
     * starts.
     *
     * @param sce The ServletContextEvent object representing the context initialization event.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("results", new ResultContainer());
        context.log("ResultsContainer initialized");
    }

    /**
     * Invoked when the servlet context is about to be destroyed. This method is empty in this implementation.
     *
     * @param sce The ServletContextEvent object representing the context destruction event.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
