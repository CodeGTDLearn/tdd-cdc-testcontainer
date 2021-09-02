package com.tdd.parallel.core;


public class Views {

  public static class ResponseViews {

    public static class UserFilter {
    }

    public static class AdminFilter extends UserFilter {
    }
  }

  public static class RequestViews {

    public static class UserFilter {
    }

    public static class AdminFilter extends UserFilter {
    }
  }
}
