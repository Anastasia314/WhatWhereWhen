package com.epam.whatwherewhen.controller;

import com.epam.whatwherewhen.command.PagePath;

/**
 * Date: 16.02.2019
 *
 * Contains page path and router type to describing behavior after command execution.
 *
 * @author Kavzovich Anastasia
 * @version 1.0
 */
public class Router {
    private String pagePath = PagePath.MAIN_PAGE;
    private RouteType routeType = RouteType.FORWARD;

    public enum RouteType {
        FORWARD, REDIRECT;
    }

    public Router() {
    }

    public Router(String pagePath) {
        this.pagePath = pagePath;
    }

    public Router(RouteType routeType) {
        this.routeType = routeType;
    }

    public Router(String pagePath, RouteType routeType) {
        this.pagePath = pagePath;
        this.routeType = routeType;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setPagePath(String pagePath) {
        if (pagePath == null) {
            this.pagePath = PagePath.MAIN_PAGE;
        }
        this.pagePath = pagePath;
    }

    public void setRouteType(RouteType routeType) {
        if (routeType == null) {
            this.routeType = RouteType.FORWARD;
        }
        this.routeType = routeType;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public void setPathWithParameter(String pagePath, String parameter, String parameterValue) {
        if (pagePath == null || parameter == null || parameterValue == null) {
            this.pagePath = PagePath.MAIN_PAGE;
        }
        this.pagePath = pagePath + "?" + parameter + "=" + parameterValue;
    }
}