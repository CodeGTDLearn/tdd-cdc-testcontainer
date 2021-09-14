package com.tdd.parallel.core.routes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoutesStandard {
  public static final String STD_REQ_MAP = "/personStd";
  public static final String STD_CRUD = "/personCrudRepoStd";
  public static final String STD_REPO = "/personRepoStd";
  public static final String STD_TEMPL = "/personTemplStd";
  public static final String STD_ID = "/{id}";
}
