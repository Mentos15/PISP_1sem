package by.ermakovich.search_command.aop.logging;


import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Loging {


    private static final Logger log = Logger.getLogger(Loging.class);


    @Pointcut("execution(* by.ermakovich.search_command.controller.AdminController.AddEvent(..))")
    public void addEventMethod() {}

    @Before("addEventMethod()")
    public void beforeAddEvent() {
        log.info("Start addEvent");
    }

    @After("addEventMethod()")
    public void afterAddEvent() {
        log.info("End addEvent");
    }

    @Around("addEventMethod()")
    public Object aroundAddEvent(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object res = jp.proceed();
        long end = System.currentTimeMillis();
        log.info("Execution of adding event took " + (end - start) + "msec.");
        return res;
    }

    @AfterThrowing(pointcut = "addEventMethod()", throwing = "e")
    public void afterThrowingAddEvent(Exception e) {
        log.info("Error: " + e.getMessage());
    }
    //////////////////////////////////////////////////////////////


    @Pointcut("execution(* by.ermakovich.search_command.controller.AdminController.DeleteEvent(..))")
    public void deleteEventMethod() {}

    @Before("deleteEventMethod()")
    public void beforeDeleteEvent() {
        log.info("Start deleteEvent");
    }

    @After("deleteEventMethod()")
    public void afterDeleteEvent() {
        log.info("End deleteEvent");
    }

    @Around("deleteEventMethod()")
    public Object aroundDeleteEvent(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object res = jp.proceed();
        long end = System.currentTimeMillis();
        log.info("Execution of delete event took " + (end - start) + "msec.");
        return res;
    }

    @AfterThrowing(pointcut = "deleteEventMethod()", throwing = "e")
    public void afterThrowingDeleteEvent(Exception e) {
        log.info("Error: " + e.getMessage());
    }

    //////////////////////////////////////////////////////////////



    @Pointcut("execution(* by.ermakovich.search_command.controller.AdminController.DeleteApplication(..))")
    public void deleteApplicationtMethod() {}

    @Before("deleteApplicationtMethod()")
    public void beforeDeleteApplication() {
        log.info("Start deleteApplication");
    }

    @After("deleteApplicationtMethod()")
    public void afterDeleteApplication() {
        log.info("End deleteApplication");
    }

    @Around("deleteApplicationtMethod()")
    public Object aroundDeleteApplication(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object res = jp.proceed();
        long end = System.currentTimeMillis();
        log.info("Execution of delete Application took " + (end - start) + "msec.");
        return res;
    }

    @AfterThrowing(pointcut = "deleteApplicationtMethod()", throwing = "e")
    public void afterThrowingDeleteApplication(Exception e) {
        log.info("Error: " + e.getMessage());
    }


    //////////////////////////////////////////////////////////////



    @Pointcut("execution(* by.ermakovich.search_command.controller.AdminController.GetApplicationById(..))")
    public void GetApplicationById() {}

    @Before("GetApplicationById()")
    public void beforeGetApplicationById() {
        log.info("Start GetApplicationById");
    }

    @After("GetApplicationById()")
    public void afterGetApplicationById() {
        log.info("End GetApplicationById");
    }

    @Around("GetApplicationById()")
    public Object aroundGetApplicationById(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object res = jp.proceed();
        long end = System.currentTimeMillis();
        log.info("Execution of Get Application ById took " + (end - start) + "msec.");
        return res;
    }

    @AfterThrowing(pointcut = "GetApplicationById()", throwing = "e")
    public void afterThrowingGetApplicationById(Exception e) {
        log.info("Error: " + e.getMessage());
    }

    //////////////////////////////////////////////////////////////



    @Pointcut("execution(* by.ermakovich.search_command.controller.AdminController.GetAllApplication(..))")
    public void GetAllApplication() {}

    @Before("GetAllApplication()")
    public void beforeGetAllApplication() {
        log.info("Start GetAllApplication");
    }

    @After("GetAllApplication()")
    public void afterGetAllApplication() {
        log.info("End GetAllApplication");
    }

    @Around("GetAllApplication()")
    public Object aroundGetAllApplication(ProceedingJoinPoint jp) throws Throwable {
        long start = System.currentTimeMillis();
        Object res = jp.proceed();
        long end = System.currentTimeMillis();
        log.info("Execution of Get all application took " + (end - start) + "msec.");
        return res;
    }

    @AfterThrowing(pointcut = "GetAllApplication()", throwing = "e")
    public void afterThrowingGetAllApplication(Exception e) {
        log.info("Error: " + e.getMessage());
    }
}
