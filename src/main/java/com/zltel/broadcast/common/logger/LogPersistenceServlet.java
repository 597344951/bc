package com.zltel.broadcast.common.logger;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;

import com.zltel.broadcast.common.dao.SimpleDao;

/**
 * 日志持久化Servlet
 * LogPersistenceServlet class
 *
 * @author Touss
 * @date 2018/5/4
 */
public class LogPersistenceServlet extends HttpServlet {
    private Log log = org.apache.commons.logging.LogFactory.getLog(LogPersistenceServlet.class);

    private SimpleDao simpleDao;

    public LogPersistenceServlet(SimpleDao simpleDao) {
        this.simpleDao = simpleDao;
    }

    @Override
    public void init() throws ServletException {
        Thread persistenceThread = new Thread(new Runnable() {
            @Override
            public void run() {
                LogBean log = null;
                while(true) {
                    log = LogQueue.pop();
                    if(log != null) {
                        simpleDao.add("log", log.toMap());
                    } else {
                        try {
                            Thread.sleep(5 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        persistenceThread.setName("thread-log-persistence");
        persistenceThread.start();
        log.info("LogPersistenceServlet init completed...");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/log.html");
    }
}
