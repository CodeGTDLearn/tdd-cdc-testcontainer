package com.tdd.parallel.core.routes;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoutesStandard {
  public static final String REQ_MAP_STD = "/personStd";
  public static final String CRUD_STD = "/personCrudRepoStd";
  public static final String REPO_STD = "/personRepoStd";
  public static final String TPL_STD = "/personTemplStd";
  public static final String ID_STD = "/{id}";
}
