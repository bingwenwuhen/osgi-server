package cn.com.event;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;

import java.util.HashMap;

/**
 * Created by xiaxuan on 16/7/16.
 */
public class Activator implements BundleActivator {


    private BundleContext context;

    boolean flag = true;

    private ServiceReference sr;

    EventAdmin eventAdmin = null;

    HashMap properties = null;

    Event event = null;


    public void start(BundleContext bundleContext) throws Exception {
        this.context = context;
        sr = context.getServiceReference(EventAdmin.class.getName());
        if (sr == null) {
            throw new Exception("Failed to obtain EventAdmin service reference!");
        }
        eventAdmin = (EventAdmin) context.getService(sr);
        if (eventAdmin == null) {
            throw new Exception("Failed to obtain EventAdmin service object!");
        }
        while (flag) {
            if (eventAdmin != null) {
                properties = new HashMap();
                properties.put(EventConstants.BUNDLE_SYMBOLICNAME, "est.first");
                //create event topic
                event = new Event("my_osgi_test_event", properties);
                eventAdmin.postEvent(event);        //asynchronous
                System.out.println("Send Event!");
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {}

            }
        }

        System.out.println("service  registered...");
    }

    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("service stoped...");
    }
}
